package com.example.__learn.exception;

import com.example.__learn.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<ApiResponse> handleCartNotFound(CartNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(ex.getMessage(), null));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ApiResponse> handleProductNotFound(ProductNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(ex.getMessage(), null));
    }


}
