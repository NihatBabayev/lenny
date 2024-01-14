package com.example.lenny.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WishlistProduct that = (WishlistProduct) o;
        return Objects.equals(id, that.id) && Objects.equals(marked, that.marked);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, marked);
    }
}