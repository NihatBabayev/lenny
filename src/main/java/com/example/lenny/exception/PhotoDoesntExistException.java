package com.example.lenny.exception;

public class PhotoDoesntExistException extends RuntimeException{
    private final static String DEFAULT_MESSAGE = "Photo doesnt exist";

    public PhotoDoesntExistException(){
        super(DEFAULT_MESSAGE);
    }
}
