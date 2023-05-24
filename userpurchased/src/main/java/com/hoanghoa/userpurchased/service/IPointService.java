package com.hoanghoa.userpurchased.service;

import com.hoanghoa.userpurchased.model.dto.request.PointAccumulateRequest;
import com.hoanghoa.userpurchased.model.dto.response.BaseResponse;

public interface IPointService {

    public BaseResponse pointAccumulate(PointAccumulateRequest request);
}
