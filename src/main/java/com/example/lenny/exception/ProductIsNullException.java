package com.example.lenny.exception;

public class ProductIsNullException extends RuntimeException{
    private static final String DEFAULT_MESSAGE = "Product is null";
    public ProductIsNullException(){
        super(DEFAULT_MESSAGE);
    }
}
