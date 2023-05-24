package com.hoanghoa.userpurchased.model.dto.request;

import com.hoanghoa.userpurchased.constant.UserType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserRegisterRequest {

    @NotEmpty(message = "The phone number is required")
    private String phoneNumber;

    @NotEmpty(message = "The username is required")
    private String username;

    @NotNull(message = "The user type is required")
    private UserType userType;

    public UserRegisterRequest(String phoneNumber, String username, UserType userType) {
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.userType = userType;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
