package com.example.lenny.dto;

import lombok.Data;

@Data
public class CommentDTO {
    private Integer rating;
    private Integer likeNumber;
    private String text;
    private String localDateTime;
    private String author;
}
