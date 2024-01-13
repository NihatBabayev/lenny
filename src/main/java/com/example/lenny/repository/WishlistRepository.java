package com.example.lenny.repository;


import com.example.lenny.entity.Customer;
import com.example.lenny.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    Wishlist findByCustomer(Customer customer);

    void deleteByCustomer_Id(Long customerId);
}