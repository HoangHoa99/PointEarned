package com.hoanghoa.userpurchased.service;

import com.hoanghoa.userpurchased.constant.MessageCode;
import com.hoanghoa.userpurchased.constant.UserType;
import com.hoanghoa.userpurchased.model.dao.User;
import com.hoanghoa.userpurchased.model.dto.request.UserLoginRequest;
import com.hoanghoa.userpurchased.model.dto.request.UserRegisterRequest;
import com.hoanghoa.userpurchased.model.dto.response.UserLoginResponse;
import com.hoanghoa.userpurchased.model.dto.response.UserRegisterResponse;
import com.hoanghoa.userpurchased.repository.UserRepository;
import com.hoanghoa.userpurchased.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;
    @Mock
    MessageSource messageSource;
    @Mock
    IS3Service s3Service;
    @InjectMocks
    UserServiceImpl userService;

    @Test
    @DisplayName(value = "Return error while registering user that existed")
    void whenRegisterWithExistedPhone_thenReturnUserExisted() {

        // init request
        String phoneNumber = "0987123654";
        String UR001 = "User has already existed";
        UserRegisterRequest request = new UserRegisterRequest(phoneNumber, "test", UserType.USER);

        // get actual response
        when(userRepository.existedUserByPhone(request.getPhoneNumber())).thenReturn(true);
        when(messageSource.getMessage(MessageCode.USER_EXISTED, new Object[0], Locale.ENGLISH))
                .thenReturn(UR001);
        UserRegisterResponse actualResponse = userService.register(request);

        // init expect response
        String message = messageSource.getMessage(MessageCode.USER_EXISTED, new Object[0], Locale.ENGLISH);
        UserRegisterResponse expectResponse = new UserRegisterResponse(message, true);

        // compare value
        Assertions.assertEquals(expectResponse.getError(), actualResponse.getError());
        Assertions.assertEquals(expectResponse.getMessage(), actualResponse.getMessage());
    }

    @Test
    @DisplayName(value = "Return success while registering new user")
    void whenRegisterWithNewPhone_thenReturnSuccess() {

        // init variable
        String phoneNumber = "0987123654";
        UserRegisterRequest request = new UserRegisterRequest(phoneNumber, "test", UserType.USER);
        String qrUrl = "Test qr url";

        // get actual response
        when(userRepository.existedUserByPhone(phoneNumber)).thenReturn(false);
        when(s3Service.generateQRCode(request.getPhoneNumber(), request.getUserType())).thenReturn(qrUrl);
        UserRegisterResponse actualResponse = userService.register(request);

        // init expect response
        String message = messageSource.getMessage(MessageCode.USER_REGISTER_SUCCESSFUL, new Object[0], Locale.ENGLISH);
        UserRegisterResponse expectResponse = new UserRegisterResponse(message, qrUrl);

        // compare value
        Assertions.assertEquals(expectResponse.getError(), actualResponse.getError());
        Assertions.assertEquals(expectResponse.getQrUrl(), actualResponse.getQrUrl());
    }

    @Test
    @DisplayName(value = "Return error while non-existed user login")
    void whenLoginNonExistedUser_thenReturnError() {

        // init variable
        String phoneNumber = "0987123654";
        Optional<User> emptyUser = Optional.empty();
        UserLoginRequest request = new UserLoginRequest(phoneNumber);

        // get actual response
        when(userRepository.findByPhone(phoneNumber)).thenReturn(emptyUser);
        UserLoginResponse actualResponse = userService.userLoginInfo(request);

        // init expect response
        String message = messageSource.getMessage(MessageCode.USER_NOT_EXISTED, new Object[0], Locale.ENGLISH);
        UserLoginResponse expectResponse = new UserLoginResponse(message, true);

        // compare value
        Assertions.assertEquals(expectResponse.getError(), actualResponse.getError());
    }

    @Test
    @DisplayName(value = "Return success while existed user login")
    void whenLoginExistedUser_thenReturnNotEmptyPurchasedList() {

        // init variable
        String phoneNumber = "0987123654";
        User fakeUser = new User();
        fakeUser.setId(0);
        fakeUser.setPhoneNumber(phoneNumber);
        fakeUser.setUserType(UserType.USER);
        Optional<User> optionalUser = Optional.of(fakeUser);
        UserLoginRequest request = new UserLoginRequest(phoneNumber);
        List<Object[]> fakeResponseList = new ArrayList<>();
        fakeResponseList.add(new Object[]{"0", "1", "2", "3"});

        // get actual response
        when(userRepository.findByPhone(phoneNumber)).thenReturn(optionalUser);
        when(userRepository.userPurchasedList(fakeUser.getId())).thenReturn(fakeResponseList);
        UserLoginResponse actualResponse = userService.userLoginInfo(request);

        // compare value
        Assertions.assertFalse(actualResponse.getUserPurchasedList().isEmpty());
    }
}
