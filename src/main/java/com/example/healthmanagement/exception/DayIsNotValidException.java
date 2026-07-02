package com.example.healthmanagement.exception;

public class DayIsNotValidException extends RuntimeException {
    public DayIsNotValidException(String message) {
        super(message);
    }
}
