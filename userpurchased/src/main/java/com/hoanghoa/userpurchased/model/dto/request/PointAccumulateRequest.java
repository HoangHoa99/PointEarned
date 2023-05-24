package com.hoanghoa.userpurchased.model.dto.request;

import com.hoanghoa.userpurchased.constant.UserType;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class PointAccumulateRequest {

    @NotNull(message = "User id is required")
    private Integer id;
    @NotEmpty(message = "Phone number is required")
    private String phoneNumber;
    @NotNull(message = "User type is required")
    private UserType userType;
    @NotNull(message = "Purchased number is required")
    @Min(value = 1, message = "Purchased number must be greater than or equal to 1")
    private Integer purchasedCount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public Integer getPurchasedCount() {
        return purchasedCount;
    }

    public void setPurchasedCount(Integer purchasedCount) {
        this.purchasedCount = purchasedCount;
    }
}
