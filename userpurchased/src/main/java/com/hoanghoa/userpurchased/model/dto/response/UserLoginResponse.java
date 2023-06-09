package com.hoanghoa.userpurchased.model.dto.response;

import com.hoanghoa.userpurchased.constant.UserType;
import com.hoanghoa.userpurchased.model.dao.entity.UserPurchased;
import com.hoanghoa.userpurchased.model.dto.AbstractBaseResponse;

import java.io.Serializable;
import java.util.List;

public class UserLoginResponse extends AbstractBaseResponse implements Serializable {

    private Integer id;
    private String username;
    private UserType userType;
    private String qrUrl;

    public UserLoginResponse(String message) {
        super(message, false);
    }

    public UserLoginResponse(String message, Boolean error) {
        super(message, error);
    }

    public UserLoginResponse() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getQrUrl() {
        return qrUrl;
    }

    public void setQrUrl(String qrUrl) {
        this.qrUrl = qrUrl;
    }
}
