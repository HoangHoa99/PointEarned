package com.hoanghoa.userpurchased.repository;

import com.hoanghoa.userpurchased.model.dao.DiscountSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscountSettingRepository extends JpaRepository<DiscountSetting, Integer> {

    @Query(value = "DELETE FROM discount_setting WHERE store_id = ?1", nativeQuery = true)
    void deleteAllStoreSettingById(Integer storeId);

    @Query(value = "SELECT * FROM discount_setting WHERE store_id IN (?1)", nativeQuery = true)
    List<DiscountSetting> getAllByIds(List<Integer> storeIds);
}
