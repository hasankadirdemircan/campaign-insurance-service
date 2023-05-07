package com.allianz.insurance.exception;

public class DefaultExceptionHandler extends RuntimeException{
    public DefaultExceptionHandler(String message) {
        super(message);
    }
}
