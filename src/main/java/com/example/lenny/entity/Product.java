//package com.example.lenny.entity;
//
//import com.example.lenny.dto.MerchantDTO;
//import jakarta.persistence.Column;
//import lombok.Data;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.mongodb.core.mapping.Document;
//
//import java.util.Map;
//
//@Document(collection = "products")
//@Data
//public class Product {
//
//    @Id
//    private String id;
//
//    private String name;
//
//    private double price;
//
//    private String description;
//
//    private double rating;
//
//    @Column(name = "sold_number")
//    private int soldNumber;
//
//    private String color;
//
//    private String type;
//
//    private String category;
//
//    private String photoName;
//
//    private Map<String, String> specifications;
//
//    private MerchantDTO merchant;
//
//}