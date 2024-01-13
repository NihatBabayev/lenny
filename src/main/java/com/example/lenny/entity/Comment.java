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
    private Integer rating;
    private Integer likeNumber;
    private String text;
    private String localDateTime;
    private String author;


    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
