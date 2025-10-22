package com.mexcorgo.exception;

public class UnauthorizedDepartmentAccessException extends RuntimeException{
    public UnauthorizedDepartmentAccessException(String message) {
        super(message);
    }
}
