package com.example.lenny.repository;

import com.example.lenny.dto.MerchantDTO;
import com.example.lenny.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    @Query("select u from User u where u.merchant.id=?1")
    User findUserByMerchantId(Long id);
}
