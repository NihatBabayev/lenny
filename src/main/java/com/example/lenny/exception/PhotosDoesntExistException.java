package com.example.lenny.exception;

public class PhotosDoesntExistException extends RuntimeException{
    private final static String DEFAULT_MESSAGE = "Photos doesn't exist";
    public  PhotosDoesntExistException(){
        super(DEFAULT_MESSAGE);
    }

}
