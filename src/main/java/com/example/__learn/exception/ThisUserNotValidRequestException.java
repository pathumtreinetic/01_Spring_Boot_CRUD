package com.example.__learn.exception;

public class ThisUserNotValidRequestException extends RuntimeException{
    public ThisUserNotValidRequestException(String message) {
        super(message);
    }
}
