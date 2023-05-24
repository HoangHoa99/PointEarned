package com.hoanghoa.userpurchased.service.impl;

import com.hoanghoa.userpurchased.constant.MessageCode;
import com.hoanghoa.userpurchased.constant.SettingKey;
import com.hoanghoa.userpurchased.constant.UserRank;
import com.hoanghoa.userpurchased.constant.UserType;
import com.hoanghoa.userpurchased.model.dao.DiscountSettingId;
import com.hoanghoa.userpurchased.model.dao.DiscountSetting;
import com.hoanghoa.userpurchased.model.dto.request.DiscountSettingRequest;
import com.hoanghoa.userpurchased.model.dto.response.BaseResponse;
import com.hoanghoa.userpurchased.repository.DiscountSettingRepository;
import com.hoanghoa.userpurchased.repository.UserRepository;
import com.hoanghoa.userpurchased.service.IDiscountSettingService;
import com.hoanghoa.userpurchased.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DiscountSettingServiceImpl implements IDiscountSettingService {

    @Autowired
    private DiscountSettingRepository discountSettingRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MessageSource messageSource;

    /**
     * Save store discount setting
     * @param request DiscountSettingRequest
     * @return BaseResponse
     */
    @Override
    public BaseResponse setDiscountRule(DiscountSettingRequest request) {

        BaseResponse response;

        try {
            // validate
            boolean storeExisted = userRepository.existedUserByIdAndType(request.getStoreId(), UserType.STORE.name());
            if(!storeExisted) {
                String message = messageSource.getMessage(MessageCode.STORE_NOT_EXISTED, new Object[0],
                        Locale.ENGLISH);

                return new BaseResponse(message, true);
            }

            // parse setting
            List<DiscountSetting> settings = this.buildDiscountSetting(request);

            if(settings.isEmpty()) {
                String message = messageSource.getMessage(MessageCode.STORE_SETTING_EMPTY, new Object[0],
                        Locale.ENGLISH);

                return new BaseResponse(message, true);
            }

            discountSettingRepository.saveAllAndFlush(settings);

            response = new BaseResponse(messageSource.getMessage(MessageCode.SUCCESS, new Object[0], Locale.ENGLISH));
        }
        catch (Exception e) {
            e.printStackTrace();

            response = new BaseResponse(
                    messageSource.getMessage(MessageCode.STORE_SETTING_ERROR, new Object[0], Locale.ENGLISH),
                    true
            );
        }

        return response;
    }

    /**
     * Build discount setting object
     * @param request DiscountSettingRequest
     * @return DiscountSetting Object
     */
    private List<DiscountSetting> buildDiscountSetting(DiscountSettingRequest request) {
        List<DiscountSetting> settingsToSave = new ArrayList<>();

        for(Map.Entry<UserRank, Map<SettingKey, String>> settingByRank : request.getDiscountSettings().entrySet()) {
            // build discount setting id
            DiscountSettingId discountSettingId = new DiscountSettingId();
            discountSettingId.setStoreId(request.getStoreId());
            discountSettingId.setUserRank(settingByRank.getKey());

            // build rank setting
            Map<SettingKey, String> settingMap = settingByRank.getValue();
            Integer leastNumber = StringUtil.convertToInteger(settingMap.get(SettingKey.LEAST_NUMBER));
            Integer discountLimit = StringUtil.convertToInteger(settingMap.get(SettingKey.DISCOUNT_LIMIT));
            Float discountValue = StringUtil.convertToFloat(settingMap.get(SettingKey.DISCOUNT_VALUE));

            // build discount setting
            DiscountSetting discountSetting = new DiscountSetting();
            discountSetting.setDiscountSettingId(discountSettingId);
            discountSetting.setLeastNumber(leastNumber);
            discountSetting.setDiscountLimit(discountLimit);
            discountSetting.setDiscountValue(discountValue);

            // add to TO_SAVE list
            settingsToSave.add(discountSetting);
        }

        return settingsToSave;
    }
}
