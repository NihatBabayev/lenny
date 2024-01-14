package com.example.lenny.config;

import com.example.lenny.dto.ResponseModel;
import com.example.lenny.exception.*;
import jakarta.validation.ConstraintViolationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

@ControllerAdvice
public class ExceptionHandlingConfig {

    @ExceptionHandler
            ({
                    UserAlreadyExistsException.class,
                    UsernameNotFoundException.class,
                    InvalidOtpCodeException.class,
                    ProductIsNullException.class,
                    IOException.class,
                    PhotoDoesntExistException.class,
                    PhotosDoesntExistException.class,
                    CommentsAreEmptyException.class,
                    UserIsNotCustomerException.class,
                    ProductAlreadyExistsInWishlistException.class,
                    ProductDoesntExistInWishlistException.class,
                    ProductAlreadyUnmarkedException.class,
                    ProductAlreadyMarkedException.class
            })
    public ResponseEntity<ResponseModel<String>> handleCustomExceptions(Exception ex) throws Exception {
        ResponseModel<String> exceptionResponseModel = new ResponseModel<>();
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        StringBuilder errorMessage = new StringBuilder(ex.getMessage());


        if (ex instanceof UserAlreadyExistsException
                || ex instanceof UsernameNotFoundException
                || ex instanceof InvalidOtpCodeException
                || ex instanceof ProductIsNullException
                || ex instanceof IOException
                || ex instanceof  PhotoDoesntExistException
                || ex instanceof  PhotosDoesntExistException
                || ex instanceof  CommentsAreEmptyException
                || ex instanceof  UserIsNotCustomerException
                || ex instanceof  ProductAlreadyExistsInWishlistException
                || ex instanceof  ProductDoesntExistInWishlistException
                || ex instanceof  ProductAlreadyUnmarkedException
                || ex instanceof  ProductAlreadyMarkedException
        ) {
            httpStatus = HttpStatus.BAD_REQUEST;

        }


        exceptionResponseModel.setMessage(String.valueOf(errorMessage));
        return new ResponseEntity<>(exceptionResponseModel, httpStatus);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResponseModel<String>> handleConstraintViolationException(ConstraintViolationException ex) {
        StringBuilder errorMessage = new StringBuilder();
        ResponseModel<String> responseModel = new ResponseModel<>();
        ex.getConstraintViolations().forEach(v -> {
            String message = v.getMessage();
            errorMessage.append(message).append(". ");
        });
        responseModel.setMessage(errorMessage.toString());

        return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
    }


}