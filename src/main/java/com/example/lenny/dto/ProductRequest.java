package com.example.lenny.dto;

import com.example.lenny.entity.Comment;
import lombok.Data;

import java.util.List;

@Data
public class ProductRequest {
    private String name;

    private double price;

    private String description;

//    private double rating;

    private int soldNumber;

    private String color;

    private String category;

    private String location;

    private String photo;

    private List<String> photos;

    private List<Comment> comments;
}