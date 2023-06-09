package com.hoanghoa.userpurchased.model.dto.response;

import com.hoanghoa.userpurchased.model.dao.entity.UserPurchased;
import com.hoanghoa.userpurchased.model.dto.AbstractBaseResponse;

import java.io.Serializable;
import java.util.List;

public class UserPurchasedResponse extends AbstractBaseResponse implements Serializable {
    private List<UserPurchased> userPurchasedList;

    public UserPurchasedResponse(){}

    public UserPurchasedResponse(String message) {
        super(message, false);
    }

    public UserPurchasedResponse(String message, Boolean error) {
        super(message, error);
    }

    public List<UserPurchased> getUserPurchasedList() {
        return userPurchasedList;
    }

    public void setUserPurchasedList(List<UserPurchased> userPurchasedList) {
        this.userPurchasedList = userPurchasedList;
    }
}
