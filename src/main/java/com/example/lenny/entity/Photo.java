package com.example.lenny.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "photos", schema = "lenny")
@Data
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String photo;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
