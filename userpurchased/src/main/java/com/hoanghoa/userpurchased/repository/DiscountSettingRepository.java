package com.hoanghoa.userpurchased.repository;

import com.hoanghoa.userpurchased.model.dao.DiscountSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountSettingRepository extends JpaRepository<DiscountSetting, Integer> {

    @Query(value = "DELETE FROM discount_setting WHERE store_id = ?1", nativeQuery = true)
    void deleteAllStoreSettingById(Integer storeId);
}
