package com.hoanghoa.userpurchased.model.dto.response;

import com.hoanghoa.userpurchased.constant.SettingKey;
import com.hoanghoa.userpurchased.constant.UserRank;

import java.util.Map;

public class GetDiscountResponse extends BaseResponse {

    public GetDiscountResponse() {}
    public GetDiscountResponse(String message, boolean error) {
        super(message, error);
    }

    private Map<UserRank, Map<SettingKey, String>> discountSettings;

    public Map<UserRank, Map<SettingKey, String>> getDiscountSettings() {
        return discountSettings;
    }

    public void setDiscountSettings(Map<UserRank, Map<SettingKey, String>> discountSettings) {
        this.discountSettings = discountSettings;
    }
}
