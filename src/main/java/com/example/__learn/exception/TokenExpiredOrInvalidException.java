package com.example.__learn.exception;

public class TokenExpiredOrInvalidException extends RuntimeException{
    public TokenExpiredOrInvalidException(String message) {
        super(message);
    }
}
