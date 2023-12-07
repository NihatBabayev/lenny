package com.example.lenny.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "comments",schema = "lenny")
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductRDBMS product;

}
