package com.example.api.handler;

import com.example.api.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException){
        ResourceExceptionDetails rfnDetails = ResourceExceptionDetails
                .Builder
                .aResourceNotFoundDetails()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .title("Not found")
                .detail(resourceNotFoundException.getMessage())
                .developerMessage(resourceNotFoundException.getClass().getName())
                .build();
        return new ResponseEntity<>(rfnDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BussinessException.class)
    public ResponseEntity<?> handleResourceNegocioException(BussinessException resourceNotFoundException){
        ResourceExceptionDetails rfnDetails = ResourceExceptionDetails
                .Builder
                .aResourceNotFoundDetails()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .title("Bussiness error")
                .detail(resourceNotFoundException.getMessage())
                .developerMessage(resourceNotFoundException.getClass().getName())
                .build();
        return new ResponseEntity<>(rfnDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidToken.class)
    public ResponseEntity<?> handleResourceTokenException(InvalidToken invalidToken){
        ResourceExceptionDetails rfnDetails = ResourceExceptionDetails
                .Builder
                .aResourceNotFoundDetails()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.FORBIDDEN.value())
                .title("Token invalid")
                .detail(invalidToken.getMessage())
                .developerMessage(invalidToken.getClass().getName())
                .build();
        return new ResponseEntity<>(rfnDetails, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleResourceException(BussinessException resourceNotFoundException){
        ResourceExceptionDetails rfnDetails = ResourceExceptionDetails
                .Builder
                .aResourceNotFoundDetails()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .title("Invalid argument")
                .detail(resourceNotFoundException.getMessage())
                .developerMessage(resourceNotFoundException.getClass().getName())
                .build();
        return new ResponseEntity<>(rfnDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HeaderBadRequest.class)
    public ResponseEntity<?> handleResourceBadRequestHeaderException(HeaderBadRequest resourceNotFoundException){
        ResourceExceptionDetails rfnDetails = ResourceExceptionDetails
                .Builder
                .aResourceNotFoundDetails()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .title("Not found")
                .detail(resourceNotFoundException.getMessage())
                .developerMessage(resourceNotFoundException.getClass().getName())
                .build();
        return new ResponseEntity<>(rfnDetails, HttpStatus.BAD_REQUEST);
    }

}
