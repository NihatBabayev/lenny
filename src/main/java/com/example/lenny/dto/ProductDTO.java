package com.example.lenny.dto;

import com.example.lenny.entity.Comment;
import lombok.Data;
import java.util.List;

@Data
public class ProductDTO {
    String name;
    double rating;
    double price;
    String description;
    int soldNumber;
    String photo;
    List<String>  photos;
    MerchantDTO merchant;
    String category;
    String location;
    List<Comment> comments;


    Integer ratingFive;
    Integer ratingFour;
    Integer ratingThree;
    Integer ratingTwo;
    Integer ratingOne;
    Integer totalReviews;
}
