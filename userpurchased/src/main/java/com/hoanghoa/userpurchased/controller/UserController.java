package com.hoanghoa.userpurchased.controller;

import com.hoanghoa.userpurchased.model.dto.request.UserLoginRequest;
import com.hoanghoa.userpurchased.model.dto.request.UserRegisterRequest;
import com.hoanghoa.userpurchased.model.dto.response.UserLoginResponse;
import com.hoanghoa.userpurchased.model.dto.response.UserRegisterResponse;
import com.hoanghoa.userpurchased.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private IUserService userService;

    @PostMapping("/register")
    public UserRegisterResponse register(@Valid @RequestBody UserRegisterRequest request) {
        return userService.register(request);
    }

    @PostMapping("/login")
    public UserLoginResponse loginByPhone(@Valid @RequestBody UserLoginRequest request) {
        return userService.userLoginInfo(request);
    }
}
