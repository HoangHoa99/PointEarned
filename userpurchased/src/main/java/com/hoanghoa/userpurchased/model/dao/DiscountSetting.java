package com.hoanghoa.userpurchased.model.dao;

import com.hoanghoa.userpurchased.constant.UserRank;
import com.hoanghoa.userpurchased.model.BaseEntity;
import com.hoanghoa.userpurchased.model.dao.DiscountSettingId;

import javax.persistence.*;

@Entity
@Table(name = "discount_setting")
public class DiscountSetting extends BaseEntity {

    @Id
    @EmbeddedId
    private DiscountSettingId discountSettingId;

    @Column(name = "least_number")
    private Integer leastNumber;

    @Column(name = "discount")
    private Float discountValue;

    @Column(name = "discount_limit")
    private Integer discountLimit;

    public DiscountSettingId getDiscountSettingId() {
        return discountSettingId;
    }

    public void setDiscountSettingId(DiscountSettingId discountSettingId) {
        this.discountSettingId = discountSettingId;
    }

    public Integer getLeastNumber() {
        return leastNumber;
    }

    public void setLeastNumber(Integer leastNumber) {
        this.leastNumber = leastNumber;
    }

    public Float getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(Float discountValue) {
        this.discountValue = discountValue;
    }

    public Integer getDiscountLimit() {
        return discountLimit;
    }

    public void setDiscountLimit(Integer discountLimit) {
        this.discountLimit = discountLimit;
    }
}

