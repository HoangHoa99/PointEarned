package com.hoanghoa.userpurchased.model.dao;

import com.hoanghoa.userpurchased.constant.UserRank;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
public class DiscountSettingId implements Serializable {

    @Column(name = "store_id")
    private Integer storeId;

    @Column(name = "user_rank")
    @Enumerated(EnumType.STRING)
    private UserRank userRank;

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public UserRank getUserRank() {
        return userRank;
    }

    public void setUserRank(UserRank userRank) {
        this.userRank = userRank;
    }
}
