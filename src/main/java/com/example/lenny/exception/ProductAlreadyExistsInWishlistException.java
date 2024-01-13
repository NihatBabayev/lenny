package com.example.lenny.exception;

public class ProductAlreadyExistsInWishlistException extends RuntimeException{
    private final static String DEFAULT_MESSAGE = "Product already exists in wishlist";
    public ProductAlreadyExistsInWishlistException(){
        super(DEFAULT_MESSAGE);
    }
}
