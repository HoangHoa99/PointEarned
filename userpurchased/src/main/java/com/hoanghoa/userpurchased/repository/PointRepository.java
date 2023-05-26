package com.hoanghoa.userpurchased.repository;

import com.hoanghoa.userpurchased.model.dao.PointCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PointRepository extends JpaRepository<PointCount, Integer> {
    @Query(nativeQuery = true, value = "SELECT * FROM point_count WHERE user_id = ?1 AND store_id = ?2")
    Optional<PointCount> pointCountByUser(Integer userId, Integer storeId);

    @Query(nativeQuery = true, value = "SELECT DISTINCT(store_id) FROM point_count")
    List<Integer> distinctStoreId();
}
