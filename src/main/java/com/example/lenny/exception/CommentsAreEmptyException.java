package com.example.lenny.exception;

public class CommentsAreEmptyException extends RuntimeException{
    private static final String DEFAULT_MESSAGE = "Provided comments are empty";
    public CommentsAreEmptyException(){
        super(DEFAULT_MESSAGE);
    }
}
