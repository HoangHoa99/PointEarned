package com.hoanghoa.userpurchased.scheduled;

import com.hoanghoa.userpurchased.constant.UserRank;
import com.hoanghoa.userpurchased.model.dao.DiscountSetting;
import com.hoanghoa.userpurchased.model.dao.PointCount;
import com.hoanghoa.userpurchased.model.dao.User;
import com.hoanghoa.userpurchased.repository.DiscountSettingRepository;
import com.hoanghoa.userpurchased.repository.PointRepository;
import com.hoanghoa.userpurchased.repository.UserRepository;
import com.hoanghoa.userpurchased.service.IS3Service;
import com.hoanghoa.userpurchased.util.StringUtil;
import io.micrometer.core.instrument.util.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ScheduledTasks {

    private final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PointRepository pointRepository;
    @Autowired
    private DiscountSettingRepository discountSettingRepository;
    @Autowired
    private IS3Service s3Service;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    /**
     * Generate missing qr code
     * Run every twelve hours
     */
    @Scheduled(cron = "0 0 */12 ? * *")
    public void generateMissingQRUrl() {
        logger.info("{} Start generate missing qr url",
                StringUtil.generateTraceId(getClass().getName()));
        try {
            // get all missing qr url records
            List<User> users = userRepository.findAllMissingQR();
            List<User> saveList = new ArrayList<>();

            if(users.isEmpty()) return;

            logger.info("{} missing qr url size: {}",
                    StringUtil.generateTraceId(getClass().getName()),
                    users.size());

            // generate missing qr code
            for(User user : users) {
                String qrUrl = s3Service.generateQRCode(user.getPhoneNumber(), user.getUserType());

                if(StringUtils.isEmpty(qrUrl)) {
                    break;
                }
                logger.info("{} QR generated for: {}, value {}",
                        StringUtil.generateTraceId(getClass().getName()),
                        user.getUsername(),
                        qrUrl);

                user.setQrUrl(qrUrl);
                saveList.add(user);
                // for separate qr name
                Thread.sleep(2000);
            }

            // save qr url
            if(saveList.isEmpty()) return;

            logger.info("{} to-save list size {}",
                    StringUtil.generateTraceId(getClass().getName()),
                    saveList.size());
            userRepository.saveAllAndFlush(saveList);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Update user rank and remain number to use discount by rank
     * Run every day at midnight
     */
    @Scheduled(cron = "0 0 0 * * ?")
    void updateUserRank() {
        logger.info("{} Start to update user rank", StringUtil.generateTraceId(getClass().getName()));
        try {
            List<Integer> distinctStoreIds = pointRepository.distinctStoreId();
            List<DiscountSetting> discountSettingsById = discountSettingRepository.getAllByIds(distinctStoreIds);

            List<PointCount> pointCounts = pointRepository.findAll();

            for(PointCount pointCount : pointCounts) {
                List<DiscountSetting> discountSettingByStore = discountSettingsById.stream()
                        .filter(discountSetting -> discountSetting.getDiscountSettingId().getStoreId()
                                .equals(pointCount.getPointCountId().getStoreId()))
                        .sorted(Comparator.comparingInt(DiscountSetting::getLeastNumber).reversed())
                        .collect(Collectors.toList());

                Pair<UserRank, Integer> userRank = findUserRank(discountSettingByStore, pointCount.getPurchasedCount());
                logger.error("{} User id {} - store id {}. User rank: {}, discount remain {}",
                        StringUtil.generateTraceId(getClass().getName()),
                        pointCount.getPointCountId().getUserId(), pointCount.getPointCountId().getStoreId(),
                        userRank.getLeft(), userRank.getRight());

                pointCount.setUserRank(userRank.getLeft());
                pointCount.setDiscountRemain(userRank.getRight());
            }

            pointRepository.saveAllAndFlush(pointCounts);
        }
        catch (Exception e) {
            logger.error("{} error message: {}", StringUtil.generateTraceId(getClass().getName()), e.getMessage());
        }
    }

    /**
     * Find current user rank
     * @param discountSettingByStore List<DiscountSetting>
     * @param purchased Integer
     * @return Pair<UserRank, Integer> Current rank - discount remain
     */
    private Pair<UserRank, Integer> findUserRank(List<DiscountSetting> discountSettingByStore, Integer purchased) {

        for(DiscountSetting discountSetting : discountSettingByStore) {
            if(discountSetting.getLeastNumber() <= purchased) {
                return Pair.of(discountSetting.getDiscountSettingId().getUserRank(), discountSetting.getDiscountLimit());
            }
        }

        return Pair.of(null, 0);
    }
}
