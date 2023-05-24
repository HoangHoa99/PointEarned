package com.hoanghoa.userpurchased.service;

import com.hoanghoa.userpurchased.model.dto.request.DiscountSettingRequest;
import com.hoanghoa.userpurchased.model.dto.response.BaseResponse;

public interface IDiscountSettingService {

    public BaseResponse setDiscountRule(DiscountSettingRequest request);
}
