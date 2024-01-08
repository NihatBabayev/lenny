package com.example.lenny.repository;

import com.example.lenny.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT COUNT(c) FROM Comment c WHERE c.rating = :rating AND c.product.id = :product_id")
    Integer findNumberOfCommentsByRating(@Param("rating") double rating, @Param("product_id") Long product_id);

    @Query("SELECT COUNT(c) FROM Comment c WHERE c.product.id = :product_id")
    Integer findTotalReviewsByProductId(@Param("product_id") Long product_id);
}
