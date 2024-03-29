package com.hoanghoa.userpurchased.service.impl;

import com.hoanghoa.userpurchased.constant.MessageCode;
import com.hoanghoa.userpurchased.constant.UserType;
import com.hoanghoa.userpurchased.model.dao.entity.UserPurchased;
import com.hoanghoa.userpurchased.model.dao.User;
import com.hoanghoa.userpurchased.model.dto.request.UserLoginRequest;
import com.hoanghoa.userpurchased.model.dto.request.UserPurchasedRequest;
import com.hoanghoa.userpurchased.model.dto.request.UserRegisterRequest;
import com.hoanghoa.userpurchased.model.dto.response.UserLoginResponse;
import com.hoanghoa.userpurchased.model.dto.response.UserPurchasedResponse;
import com.hoanghoa.userpurchased.model.dto.response.UserRegisterResponse;
import com.hoanghoa.userpurchased.repository.UserRepository;
import com.hoanghoa.userpurchased.service.IS3Service;
import com.hoanghoa.userpurchased.service.IUserService;
import com.hoanghoa.userpurchased.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private IS3Service s3Service;

    @Override
    public UserRegisterResponse register(UserRegisterRequest request) {
        // response
        UserRegisterResponse response;
        try {
            // validate
            boolean userExisted = userRepository.existedUserByPhone(request.getPhoneNumber());
            if(userExisted) {
                String message = messageSource.getMessage(MessageCode.USER_EXISTED, new Object[0], Locale.ENGLISH);

                return new UserRegisterResponse(message, true);
            }

            // generate & save to s3 bucket
            String qrUrl = s3Service.generateQRCode(request.getPhoneNumber(), request.getUserType());

            // mapping and save
            this.saveUser(request, qrUrl);

            // return qr url
            String message = messageSource.getMessage(MessageCode.USER_REGISTER_SUCCESSFUL, new Object[0],
                    Locale.ENGLISH);
            response = new UserRegisterResponse(message, qrUrl);
        }
        catch (Exception e) {
            e.printStackTrace();

            String message = messageSource.getMessage(MessageCode.USER_REGISTER_FAILURE, new Object[0],
                    Locale.ENGLISH);
            response = new UserRegisterResponse(message, true);
        }

        return response;
    }

    /**
     * Save user information
     * @param request Register request
     * @param qrUrl QR image URL of user
     */
    private void saveUser(UserRegisterRequest request, String qrUrl) {
        User user = new User();
        user.setPhoneNumber(request.getPhoneNumber());
        user.setUsername(request.getUsername());
        user.setUserType(request.getUserType());
        user.setQrUrl(qrUrl);

        userRepository.save(user);
    }

    /**
     * User login handler
     * @param request UserLoginRequest
     * @return UserLoginResponse
     */
    @Override
    public UserLoginResponse userLoginInfo(UserLoginRequest request) {

        Optional<User> clientOptional = userRepository.findByPhone(request.getPhoneNumber());
        if(clientOptional.isEmpty()) {
            String message = messageSource.getMessage(MessageCode.USER_NOT_EXISTED, new Object[0], Locale.ENGLISH);

            return new UserLoginResponse(message, true);
        }

        User user = clientOptional.get();

        UserLoginResponse response = new UserLoginResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setUserType(user.getUserType());
        response.setQrUrl(user.getQrUrl());

        return response;
    }

    @Override
    public UserPurchasedResponse userPurchasedHistory(UserPurchasedRequest request) {
        UserPurchasedResponse response;
        try {
            Optional<User> clientOptional = userRepository.findById(request.getId());
            if(clientOptional.isEmpty()) {
                String message = messageSource.getMessage(MessageCode.USER_NOT_EXISTED, new Object[0], Locale.ENGLISH);

                return new UserPurchasedResponse(message, true);
            }

            User foundUser = clientOptional.get();

            List<UserPurchased> userPurchasedList = this.userPurchasedList(foundUser.getUserType(), foundUser.getId());

            String message = messageSource.getMessage(MessageCode.SUCCESS, new Object[0], Locale.ENGLISH);

            response = new UserPurchasedResponse();
            response.setUserPurchasedList(userPurchasedList);
            response.setMessage(message);
        }
        catch (Exception e) {
            LOGGER.error("{} error occur while fetching user purchased. Message: {}",
                    StringUtil.generateTraceId(String.valueOf(request.getId()), "userPurchasedHistory"),
                    e.getMessage());

            String message = messageSource.getMessage(MessageCode.FAILURE, new Object[0], Locale.ENGLISH);

            response = new UserPurchasedResponse(message, true);
        }

        return response;
    }

    private List<UserPurchased> userPurchasedList(UserType userType, Integer userId) {

        List<Object[]> userPurchasedObj;
        if(UserType.USER.equals(userType)) {
            userPurchasedObj = userRepository.userPurchasedList(userId);
        }
        else {
            userPurchasedObj = userRepository.userHistoryList(userId);
        }

        List<UserPurchased> userPurchasedList = new ArrayList<>();
        for(Object[] obj : userPurchasedObj) {
            UserPurchased userPurchased = UserPurchased.mapToUserPurchased(obj);

            userPurchasedList.add(userPurchased);
        }

        return userPurchasedList;
    }
}
