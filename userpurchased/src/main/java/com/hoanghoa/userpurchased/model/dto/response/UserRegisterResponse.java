package com.hoanghoa.userpurchased.model.dto.response;

import com.hoanghoa.userpurchased.model.dto.AbstractBaseResponse;

import java.io.Serializable;

public class UserRegisterResponse extends AbstractBaseResponse implements Serializable {

    private String qrUrl;

    public UserRegisterResponse(String message) {
        super(message, false);
    }

    public UserRegisterResponse(String message, Boolean error) {
        super(message, error);
    }

    public UserRegisterResponse(String message, String qrUrl) {
        super(message, false);
        this.qrUrl = qrUrl;
    }

    public UserRegisterResponse() {
    }

    public String getQrUrl() {
        return qrUrl;
    }

    public void setQrUrl(String qrUrl) {
        this.qrUrl = qrUrl;
    }
}
