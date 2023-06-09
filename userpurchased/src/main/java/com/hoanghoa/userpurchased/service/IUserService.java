package com.hoanghoa.userpurchased.service;

import com.hoanghoa.userpurchased.model.dto.request.UserLoginRequest;
import com.hoanghoa.userpurchased.model.dto.request.UserPurchasedRequest;
import com.hoanghoa.userpurchased.model.dto.request.UserRegisterRequest;
import com.hoanghoa.userpurchased.model.dto.response.UserLoginResponse;
import com.hoanghoa.userpurchased.model.dto.response.UserPurchasedResponse;
import com.hoanghoa.userpurchased.model.dto.response.UserRegisterResponse;

public interface IUserService {
    UserRegisterResponse register(UserRegisterRequest request);
    UserLoginResponse userLoginInfo(UserLoginRequest request);
    UserPurchasedResponse userPurchasedHistory(UserPurchasedRequest request);
}
