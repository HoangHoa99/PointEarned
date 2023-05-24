package com.hoanghoa.userpurchased.controller;

import com.hoanghoa.userpurchased.model.dto.request.DiscountSettingRequest;
import com.hoanghoa.userpurchased.model.dto.response.BaseResponse;
import com.hoanghoa.userpurchased.service.IDiscountSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("discount-setting")
public class DiscountSettingController {

    @Autowired
    private IDiscountSettingService discountSettingService;

    @PostMapping("set")
    public BaseResponse setDiscountRule(@Valid @RequestBody DiscountSettingRequest request) {
        return discountSettingService.setDiscountRule(request);
    }
}
