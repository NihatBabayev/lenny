package com.example.lenny.exception;

public class ProductAlreadyMarkedException extends RuntimeException{
    private final static String DEFAULT_MESSAGE = "Product is already marked";
    public ProductAlreadyMarkedException(){
        super(DEFAULT_MESSAGE);

    }
}
