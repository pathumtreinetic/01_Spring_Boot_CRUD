package com.example.__learn.exception;

public class ThisUserNotValidRequest extends RuntimeException{
    public ThisUserNotValidRequest(String message) {
        super(message);
    }
}
