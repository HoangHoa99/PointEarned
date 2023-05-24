package com.hoanghoa.userpurchased.service.impl;

import com.hoanghoa.userpurchased.constant.MessageCode;
import com.hoanghoa.userpurchased.constant.UserType;
import com.hoanghoa.userpurchased.model.dao.PointCountId;
import com.hoanghoa.userpurchased.model.dao.PointCount;
import com.hoanghoa.userpurchased.model.dao.User;
import com.hoanghoa.userpurchased.model.dto.request.PointAccumulateRequest;
import com.hoanghoa.userpurchased.model.dto.response.BaseResponse;
import com.hoanghoa.userpurchased.repository.PointRepository;
import com.hoanghoa.userpurchased.repository.UserRepository;
import com.hoanghoa.userpurchased.service.IPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;

@Service
public class PointServiceImpl implements IPointService {

    @Autowired
    private PointRepository pointRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MessageSource messageSource;

    /**
     * Accumulate point
     * @param request PointAccumulateRequest
     * @return BaseResponse
     */
    @Override
    public BaseResponse pointAccumulate(PointAccumulateRequest request) {

        BaseResponse response;

        try {
            // validate user
            boolean userExistedByPhone = userRepository.existedUserByPhone(request.getPhoneNumber());
            boolean userExistedById = userRepository.existsById(request.getId());
            if(!userExistedByPhone || !userExistedById) {
                String message = messageSource.getMessage(MessageCode.USER_NOT_EXISTED, new Object[0], Locale.ENGLISH);

                return new BaseResponse(message, true);
            }

            // Save user purchased
            PointCount pointCount = this.initPointCount(request);
            pointRepository.save(pointCount);

            response = new BaseResponse(messageSource.getMessage(MessageCode.USER_PURCHASED_SUCCESS,
                    new Object[0], Locale.ENGLISH));
        }
        catch (Exception e) {
            e.printStackTrace();

            response = new BaseResponse(
                    messageSource.getMessage(MessageCode.USER_PURCHASED_ERROR, new Object[0], Locale.ENGLISH),
                    true
            );
        }

        return response;
    }

    /**
     * Initialize point count table
     * @param request PointAccumulateRequest
     * @return PointCount object
     */
    private PointCount initPointCount(PointAccumulateRequest request) {
        // Build point count id
        User client = userRepository.findByPhoneAndType(request.getPhoneNumber(), request.getUserType().name());

        PointCountId pointCountId = new PointCountId();
        if(UserType.USER.equals(request.getUserType())) {
            pointCountId.setStoreId(request.getId());
            pointCountId.setUserId(client.getId());
        }
        else {
            pointCountId.setStoreId(client.getId());
            pointCountId.setUserId(request.getId());
        }

        // Find point count by pair id
        Optional<PointCount> pointCountOpt = pointRepository.pointCountByUser(pointCountId.getUserId(),
                pointCountId.getStoreId());

        PointCount pointCount;
        // Create new
        if(pointCountOpt.isEmpty()) {
            pointCount = new PointCount();
            pointCount.setPointCountId(pointCountId);
            pointCount.setPurchasedCount(request.getPurchasedCount());

            return pointCount;
        }

        // Add to existed one
        pointCount = pointCountOpt.get();
        pointCount.setPurchasedCount(request.getPurchasedCount());

        return pointCount;
    }
}
