package com.epam.esm.persistence.repository.exception;

public class NotEnoughDataForRegistrationException extends RuntimeException {
    public NotEnoughDataForRegistrationException() {
    }

    public NotEnoughDataForRegistrationException(String message) {
        super(message);
    }

    public NotEnoughDataForRegistrationException(String message, Throwable cause) {
        super(message, cause);
    }
}
