package com.hoanghoa.userpurchased.model.dto.request;

import javax.validation.constraints.NotNull;

public class UserPurchasedRequest {

    @NotNull(message = "Id is required")
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
