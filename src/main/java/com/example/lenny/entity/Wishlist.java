package com.example.lenny.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "wishlists",schema = "lenny")
@Data
public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;



}
