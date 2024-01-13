package com.example.lenny.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "wishlist_products",schema = "lenny")
@Data
public class WishlistProduct {
    @Id
    @GeneratedValue
    private Long id;

    private Integer marked;

    @ManyToOne
    @JoinColumn(name = "wishlist_id")
    private Wishlist wishlist;

    @JoinColumn(name = "product_id")
    private Long productId;


}