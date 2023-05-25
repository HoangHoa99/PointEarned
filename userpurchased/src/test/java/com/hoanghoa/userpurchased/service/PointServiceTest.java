package com.hoanghoa.userpurchased.service;

import com.hoanghoa.userpurchased.constant.MessageCode;
import com.hoanghoa.userpurchased.constant.UserType;
import com.hoanghoa.userpurchased.model.dao.PointCount;
import com.hoanghoa.userpurchased.model.dao.PointCountId;
import com.hoanghoa.userpurchased.model.dao.User;
import com.hoanghoa.userpurchased.model.dto.request.PointAccumulateRequest;
import com.hoanghoa.userpurchased.model.dto.response.BaseResponse;
import com.hoanghoa.userpurchased.repository.PointRepository;
import com.hoanghoa.userpurchased.repository.UserRepository;
import com.hoanghoa.userpurchased.service.impl.PointServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PointServiceTest {

    @Mock
    UserRepository userRepository;
    @Mock
    PointRepository pointRepository;
    @InjectMocks
    PointServiceImpl pointService;
    @Mock
    MessageSource messageSource;

    @Test
    @DisplayName(value = "Return error while both users do not exist")
    void whenBothNonExisted_thenReturnError() {
        // init variable
        String phoneNumber = "0912982112";
        PointAccumulateRequest request = new PointAccumulateRequest();
        request.setId(0);
        request.setPhoneNumber(phoneNumber);
        request.setUserType(UserType.USER);
        request.setPurchasedCount(2);

        // actual result
        when(userRepository.existedUserByPhone(request.getPhoneNumber())).thenReturn(false);
        when(userRepository.existsById(request.getId())).thenReturn(false);
        when(messageSource.getMessage(any(), any(), any())).thenReturn(any());
        BaseResponse actualResponse = pointService.pointAccumulate(request);

        // compare result
        Assertions.assertTrue(actualResponse.getError());
    }

    @Test
    @DisplayName(value = "Return error while user does not exist")
    void whenUserNonExisted_thenReturnError() {
        // init variable
        String phoneNumber = "0912982112";
        PointAccumulateRequest request = new PointAccumulateRequest();
        request.setId(0);
        request.setPhoneNumber(phoneNumber);
        request.setUserType(UserType.USER);
        request.setPurchasedCount(2);

        // actual result
        when(userRepository.existedUserByPhone(request.getPhoneNumber())).thenReturn(false);
        when(userRepository.existsById(request.getId())).thenReturn(true);
        when(messageSource.getMessage(any(), any(), any())).thenReturn(any());
        BaseResponse actualResponse = pointService.pointAccumulate(request);

        // compare result
        Assertions.assertTrue(actualResponse.getError());
    }

    @Test
    @DisplayName(value = "Return error while store does not exist")
    void whenStoreNonExisted_thenReturnError() {
        // init variable
        String phoneNumber = "0912982112";
        PointAccumulateRequest request = new PointAccumulateRequest();
        request.setId(0);
        request.setPhoneNumber(phoneNumber);
        request.setUserType(UserType.USER);
        request.setPurchasedCount(2);

        // actual result
        when(userRepository.existedUserByPhone(request.getPhoneNumber())).thenReturn(true);
        when(userRepository.existsById(request.getId())).thenReturn(false);
        when(messageSource.getMessage(any(), any(), any())).thenReturn(any());
        BaseResponse actualResponse = pointService.pointAccumulate(request);

        // compare result
        Assertions.assertTrue(actualResponse.getError());
    }

    @Test
    @DisplayName(value = "Return success while accumulate user purchased")
    void whenAccumulatePoint_thenReturnSuccess() {
        // init variable
        String phoneNumber = "0912982112";
        PointAccumulateRequest request = new PointAccumulateRequest();
        request.setId(0);
        request.setPhoneNumber(phoneNumber);
        request.setUserType(UserType.USER);
        request.setPurchasedCount(2);
        User fakeUser = new User();
        fakeUser.setId(1);
        Optional<PointCount> optionalPointCount = Optional.of(new PointCount());

        // actual result
        when(userRepository.existedUserByPhone(request.getPhoneNumber())).thenReturn(true);
        when(userRepository.existsById(request.getId())).thenReturn(true);
        when(userRepository.findByPhoneAndType(request.getPhoneNumber(), request.getUserType().name()))
                .thenReturn(fakeUser);
        when(pointRepository.pointCountByUser(fakeUser.getId(), 0)).thenReturn(optionalPointCount);
        when(messageSource.getMessage(MessageCode.USER_PURCHASED_SUCCESS, new Object[0], Locale.ENGLISH))
                .thenReturn(any());
        BaseResponse actualResponse = pointService.pointAccumulate(request);

        // compare result
        Assertions.assertFalse(actualResponse.getError());
    }
}
