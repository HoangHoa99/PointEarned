package com.hoanghoa.userpurchased.service;

import com.hoanghoa.userpurchased.model.dto.request.DiscountSettingRequest;
import com.hoanghoa.userpurchased.model.dto.request.GetDiscountRequest;
import com.hoanghoa.userpurchased.model.dto.response.BaseResponse;
import com.hoanghoa.userpurchased.model.dto.response.GetDiscountResponse;

public interface IDiscountSettingService {

    BaseResponse setDiscountRule(DiscountSettingRequest request);
    GetDiscountResponse getDiscountRule(GetDiscountRequest request);
}
