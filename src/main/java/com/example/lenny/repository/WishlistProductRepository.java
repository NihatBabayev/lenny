package com.example.lenny.repository;

import com.example.lenny.entity.WishlistProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WishlistProductRepository extends JpaRepository<WishlistProduct, Long> {

    @Modifying
    @Query("UPDATE WishlistProduct wp SET wp.marked = :marked WHERE wp.id = :wishlistProductId")
    void setMarkedField(@Param("wishlistProductId") Long wishlistProductId, @Param("marked") int marked);

}
