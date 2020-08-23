package com.example.demo.exception;

public class InvalidInputException extends Exception {
    public InvalidInputException(String errorMessage) {
        super( errorMessage);
    }
}
