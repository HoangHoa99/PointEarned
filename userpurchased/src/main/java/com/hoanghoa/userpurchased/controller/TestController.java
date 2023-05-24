package com.hoanghoa.userpurchased.controller;

import com.hoanghoa.userpurchased.model.dto.response.BaseResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {

    @GetMapping
    public Object test() {
        return new BaseResponse("Hello from there!");
    }
}
