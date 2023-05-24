package com.hoanghoa.userpurchased.model.dao;

import com.hoanghoa.userpurchased.constant.UserRank;
import com.hoanghoa.userpurchased.model.BaseEntity;
import com.hoanghoa.userpurchased.model.dao.PointCountId;

import javax.persistence.*;

@Entity
@Table(name = "point_count")
public class PointCount extends BaseEntity {

    @Id
    @EmbeddedId
    private PointCountId pointCountId;

    @Column(name = "purchased_count")
    private Integer purchasedCount = 0;

    @Column(name = "user_rank")
    @Enumerated(EnumType.STRING)
    private UserRank userRank;

    @Column(name = "discount_id")
    private Integer discountId;

    @Column(name = "discount_remain")
    private Integer discountRemain;

    public PointCountId getPointCountId() {
        return pointCountId;
    }

    public void setPointCountId(PointCountId pointCountId) {
        this.pointCountId = pointCountId;
    }

    public Integer getPurchasedCount() {
        return purchasedCount;
    }

    public void setPurchasedCount(Integer purchasedCount) {
        this.purchasedCount += purchasedCount;
    }

    public UserRank getUserRank() {
        return userRank;
    }

    public void setUserRank(UserRank userRank) {
        this.userRank = userRank;
    }

    public Integer getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Integer discountId) {
        this.discountId = discountId;
    }

    public Integer getDiscountRemain() {
        return discountRemain;
    }

    public void setDiscountRemain(Integer discountRemain) {
        this.discountRemain = discountRemain;
    }
}