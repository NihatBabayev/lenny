package com.example.lenny.exception;

public class ProductAlreadyUnmarkedException extends RuntimeException{
    private static final String DEFAULT_MESSAGE = "Product is already unmarked";
    public ProductAlreadyUnmarkedException(){
        super(DEFAULT_MESSAGE);
    }
}
