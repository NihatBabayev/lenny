package com.example.lenny.dto;

import com.example.lenny.entity.ProductRDBMS;
import lombok.Data;

@Data
public class ProductDTO {
    String name;
    double rating;
    private double price;
    private String description;
    int soldNumber;
    public static ProductDTO mapToDTO(ProductRDBMS product) {
        ProductDTO dto = new ProductDTO();
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setDescription(product.getDescription());
        dto.setRating(product.getRating());
        dto.setSoldNumber(product.getSoldNumber());
        return dto;
    }
}
