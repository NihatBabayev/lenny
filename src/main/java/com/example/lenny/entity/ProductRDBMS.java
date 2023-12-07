package com.example.lenny.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;

@Entity
@Table(name = "products", schema = "lenny")
@Data
public class ProductRDBMS {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    private double price;

    private String description;

    private double rating;

    @Column(name = "sold_number")
    private int soldNumber;

    private String color;

    private String type;

    private String category;

    //    private String photoName;
    @Column(columnDefinition = "TEXT")
    private String photo;
    //    private Map<String, String> specifications;
    @OneToOne
    @JoinColumn(name = "merchant_id")
    private Merchant merchant;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private ArrayList<Comment> comments = new ArrayList<>();


}
