package com.hoanghoa.userpurchased.service;

import com.hoanghoa.userpurchased.constant.MessageCode;
import com.hoanghoa.userpurchased.constant.SettingKey;
import com.hoanghoa.userpurchased.constant.UserRank;
import com.hoanghoa.userpurchased.constant.UserType;
import com.hoanghoa.userpurchased.model.dto.request.DiscountSettingRequest;
import com.hoanghoa.userpurchased.model.dto.response.BaseResponse;
import com.hoanghoa.userpurchased.repository.DiscountSettingRepository;
import com.hoanghoa.userpurchased.repository.UserRepository;
import com.hoanghoa.userpurchased.service.impl.DiscountSettingServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import java.util.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DiscountSettingServiceTest {

    @Mock
    UserRepository userRepository;
    @Mock
    DiscountSettingRepository discountSettingRepository;
    @Mock
    MessageSource messageSource;
    @InjectMocks
    DiscountSettingServiceImpl discountSettingService;

    @Test
    @DisplayName(value = "When set discount-setting for non-existed store, return error")
    void whenStoreNonExisted_thenReturnError(){
        // init variable
        String ST001 = "Store has not existed";
        Map<UserRank, Map<SettingKey, String>> discountSetting = new HashMap<>();
        Map<SettingKey, String> settingKeyMap = new HashMap<>();
        settingKeyMap.put(SettingKey.LEAST_NUMBER, "10");
        settingKeyMap.put(SettingKey.DISCOUNT_VALUE, "0.05");
        settingKeyMap.put(SettingKey.DISCOUNT_LIMIT, "3");
        discountSetting.put(UserRank.BRONZE, settingKeyMap);

        DiscountSettingRequest request = new DiscountSettingRequest();
        request.setStoreId(0);
        request.setDiscountSettings(discountSetting);

        // actual result
        when(userRepository.existedUserByIdAndType(request.getStoreId(), UserType.STORE.name()))
                .thenReturn(false);
        when(messageSource.getMessage(MessageCode.STORE_NOT_EXISTED, new Object[0], Locale.ENGLISH))
                .thenReturn(ST001);
        BaseResponse actualResponse = discountSettingService.setDiscountRule(request);

        Assertions.assertTrue(actualResponse.getError());
    }

    @Test
    @DisplayName(value = "Return success when set the discount-setting for existed store")
    void whenStoreExisted_thenReturnSuccess(){
        // init variable
        Map<UserRank, Map<SettingKey, String>> discountSettingMap = new HashMap<>();
        Map<SettingKey, String> settingKeyMap = new HashMap<>();
        settingKeyMap.put(SettingKey.LEAST_NUMBER, "10");
        settingKeyMap.put(SettingKey.DISCOUNT_VALUE, "0.05");
        settingKeyMap.put(SettingKey.DISCOUNT_LIMIT, "3");
        discountSettingMap.put(UserRank.BRONZE, settingKeyMap);

        DiscountSettingRequest request = new DiscountSettingRequest();
        request.setStoreId(0);
        request.setDiscountSettings(discountSettingMap);

        // actual result

        when(userRepository.existedUserByIdAndType(0, UserType.STORE.name())).thenReturn(Boolean.TRUE);
        BaseResponse actualResponse = discountSettingService.setDiscountRule(request);

        Assertions.assertFalse(actualResponse.getError());
    }
}
