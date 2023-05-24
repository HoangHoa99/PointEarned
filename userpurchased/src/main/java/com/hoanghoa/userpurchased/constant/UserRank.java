package com.hoanghoa.userpurchased.constant;


import org.apache.commons.lang3.EnumUtils;

public enum UserRank {
    BRONZE,
    SILVER,
    GOLD,
    DIAMOND;

    public static UserRank getValue(String val) {
        if(EnumUtils.isValidEnumIgnoreCase(UserRank.class, val)) {
            return UserRank.valueOf(val);
        }

        return null;
    }
}
