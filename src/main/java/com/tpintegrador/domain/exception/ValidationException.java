package com.tpintegrador.domain.exception;

// Similar a ExceptionsPerson de tu proyecto original, pero con un nombre más genérico
public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}