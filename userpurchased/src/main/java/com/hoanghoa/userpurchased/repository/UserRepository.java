package com.hoanghoa.userpurchased.repository;

import com.hoanghoa.userpurchased.model.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(nativeQuery = true, value = "SELECT * FROM users WHERE phone_number = ?1 AND user_type = ?2")
    User findByPhoneAndType(String phoneNumber, String userType);

    @Query(nativeQuery = true, value = "SELECT EXISTS(SELECT 1 FROM users WHERE phone_number = ?1)")
    boolean existedUserByPhone(String phoneNumber);

    @Query(nativeQuery = true, value = "SELECT EXISTS(SELECT 1 FROM users WHERE id = ?1 AND user_type = ?2)")
    boolean existedUserByIdAndType(Integer id, String userType);

    @Query(nativeQuery = true, value = "SELECT * FROM users WHERE phone_number = ?1")
    Optional<User> findByPhone(String phoneNumber);

    @Query(nativeQuery = true, value = "SELECT u.username, purchased_count, user_rank, discount_remain" +
            " FROM point_count pc" +
            " JOIN users u ON u.id = pc.store_id" +
            " WHERE pc.user_id = ?1")
    List<Object[]> userPurchasedList(Integer userId);

    @Query(nativeQuery = true, value = "SELECT u.username, purchased_count, user_rank, discount_remain" +
            " FROM point_count pc" +
            " JOIN users u ON u.id = pc.user_id" +
            " WHERE pc.store_id = ?1")
    List<Object[]> userHistoryList(Integer userId);
}
