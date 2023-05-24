package com.hoanghoa.userpurchased.controller;

import com.hoanghoa.userpurchased.model.dto.request.PointAccumulateRequest;
import com.hoanghoa.userpurchased.model.dto.response.BaseResponse;
import com.hoanghoa.userpurchased.service.IPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("point")
public class PointController {

    @Autowired
    private IPointService pointService;

    @PostMapping("/accumulate")
    public BaseResponse pointAccumulate(@Valid @RequestBody PointAccumulateRequest request) {
        return pointService.pointAccumulate(request);
    }
}
