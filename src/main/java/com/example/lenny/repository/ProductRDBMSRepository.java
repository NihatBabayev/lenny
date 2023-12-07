package com.example.lenny.repository;

import com.example.lenny.entity.ProductRDBMS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRDBMSRepository extends JpaRepository<ProductRDBMS, Long> {
    ProductRDBMS findByName(String name);

}
