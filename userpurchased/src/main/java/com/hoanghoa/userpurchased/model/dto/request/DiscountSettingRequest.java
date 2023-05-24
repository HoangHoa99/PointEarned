package com.hoanghoa.userpurchased.model.dto.request;

import com.hoanghoa.userpurchased.constant.SettingKey;
import com.hoanghoa.userpurchased.constant.UserRank;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;

public class DiscountSettingRequest {
    @NotNull(message = "Store id must be provided")
    private Integer storeId;
    @NotEmpty(message = "Discount setting must be provided")
    private Map<UserRank, Map<SettingKey, String>> discountSettings;

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Map<UserRank, Map<SettingKey, String>> getDiscountSettings() {
        return discountSettings;
    }

    public void setDiscountSettings(Map<UserRank, Map<SettingKey, String>> discountSettings) {
        this.discountSettings = discountSettings;
    }
}
