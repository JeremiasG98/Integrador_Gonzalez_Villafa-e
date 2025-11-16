package com.tpintegrador.domain.exception;

public class EndDateBeforeStartDateException extends RuntimeException {
    public EndDateBeforeStartDateException(String message) {
        super(message
        );
    }
}
