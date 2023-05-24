package com.hoanghoa.userpurchased.model.dao.entity;

import com.hoanghoa.userpurchased.constant.UserRank;
import com.hoanghoa.userpurchased.util.StringUtil;

public class UserPurchased {

    private String name;
    private Integer purchasedCount;
    private UserRank userRank;
    private Integer discountRemain = 0;

    public static UserPurchased mapToUserPurchased(Object[] obj) {
        UserPurchased userPurchased = new UserPurchased();

        userPurchased.setName(StringUtil.valueOf(obj[0]));
        userPurchased.setPurchasedCount(Integer.valueOf(StringUtil.valueOf(obj[1])));
        userPurchased.setUserRank(UserRank.getValue(StringUtil.valueOf(obj[2])));
        userPurchased.setDiscountRemain(Integer.valueOf(StringUtil.valueOf(obj[3])));

        return userPurchased;
    }

    public String getName() {
        return name;
    }

    public void setName(String username) {
        this.name = username;
    }

    public Integer getPurchasedCount() {
        return purchasedCount;
    }

    public void setPurchasedCount(Integer purchasedCount) {
        this.purchasedCount = purchasedCount;
    }

    public UserRank getUserRank() {
        return userRank;
    }

    public void setUserRank(UserRank userRank) {
        this.userRank = userRank;
    }

    public Integer getDiscountRemain() {
        return discountRemain;
    }

    public void setDiscountRemain(Integer discountRemain) {
        this.discountRemain = discountRemain;
    }
}
