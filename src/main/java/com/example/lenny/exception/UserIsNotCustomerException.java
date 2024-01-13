package com.example.lenny.exception;

public class UserIsNotCustomerException extends RuntimeException{
    private final static String DEFAULT_MESSAGE = "User is not customer";
    public UserIsNotCustomerException(){
        super(DEFAULT_MESSAGE);
    }
}
