package com.example.lenny.repository;

import com.example.lenny.entity.Category;
import com.example.lenny.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByName(String name);
    @Query("SELECT p FROM Product p WHERE (:location IS NULL OR p.location = :location) " +
            "AND (:category IS NULL OR p.category.name = :category) " +
            "AND (:priceStart IS NULL OR p.price >= :priceStart) " +
            "AND (:priceEnd IS NULL OR p.price <= :priceEnd)")
    List<Product> findByFilter(@Param("location") String location,
                               @Param("category") String category,
                               @Param("priceStart") Double priceStart,
                               @Param("priceEnd") Double priceEnd);


    @Query("SELECT p FROM Product p WHERE (:location IS NULL OR p.location = :location) " +
            "AND (:category IS NULL OR p.category.name = :category) " +
            "AND (:priceStart IS NULL OR p.price >= :priceStart) " +
            "AND (:priceEnd IS NULL OR p.price <= :priceEnd) " +
            "AND (:searchKeyword IS NULL OR LOWER(p.name) LIKE %:searchKeyword%)")
    List<Product> findByFilterAndNameContainingIgnoreCase(@Param("location") String location,
                                                          @Param("category") String category,
                                                          @Param("priceStart") Double priceStart,
                                                          @Param("priceEnd") Double priceEnd,
                                                          @Param("searchKeyword") String searchKeyword);

    Product findByNameAndCategoryAndDescriptionAndLocationAndColorAndPriceAndSoldNumber(String name,
                                                                                        Category category,
                                                                                        String description,
                                                                                        String location,
                                                                                        String color,
                                                                                        double price,
                                                                                        int soldNumber);
    Product findByNameAndLocation(String productName, String location);
}
