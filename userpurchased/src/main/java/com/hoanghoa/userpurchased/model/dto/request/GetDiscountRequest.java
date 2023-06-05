package com.hoanghoa.userpurchased.model.dto.request;

import javax.validation.constraints.NotNull;

public class GetDiscountRequest {

    @NotNull(message = "Id is required")
    private Integer storeId;

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }
}
