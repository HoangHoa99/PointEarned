package com.hoanghoa.userpurchased.model.dto.request;

import javax.validation.constraints.NotEmpty;

public class UserLoginRequest {

    @NotEmpty(message = "Phone number is required")
    private String phoneNumber;

    public UserLoginRequest(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
