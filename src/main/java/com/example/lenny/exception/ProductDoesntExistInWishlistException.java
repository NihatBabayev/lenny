package com.example.lenny.exception;

public class ProductDoesntExistInWishlistException extends RuntimeException{
    private final static String DEFAULT_MESSAGE = "Product doesn't exist in wishlist";
    public ProductDoesntExistInWishlistException(){
        super(DEFAULT_MESSAGE);
    }
}
