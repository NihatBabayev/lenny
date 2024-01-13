package com.example.lenny.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Table(name = "products", schema = "lenny")
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Product{
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

    private String location;



    @Column(columnDefinition = "TEXT")
    private String photo;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Photo> photos = new HashSet<>();

    //    private Map<String, String> specifications;
    @ManyToOne
    @JoinColumn(name = "merchant_id")
    private Merchant merchant;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;


    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<Comment> comments = new HashSet<>();

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, description, rating, soldNumber, color, location, photo);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Product product = (Product) obj;
        return Objects.equals(id, product.id) &&
                Objects.equals(name, product.name) &&
                Objects.equals(price, product.price) &&
                Objects.equals(description, product.description) &&
                Objects.equals(rating, product.rating) &&
                Objects.equals(soldNumber, product.soldNumber) &&
                Objects.equals(color, product.color) &&
                Objects.equals(location, product.location) &&
                Objects.equals(photo, product.photo);
    }


}
