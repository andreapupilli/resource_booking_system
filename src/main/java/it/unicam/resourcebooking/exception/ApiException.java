package it.unicam.resourcebooking.exception;

import org.springframework.http.HttpStatus;

/**
 * Eccezione applicativa con status HTTP esplicito.
 * Utile per restituire errori 4xx/5xx coerenti sia alle API REST sia alla UI.
 */
public class ApiException extends RuntimeException {

    private final HttpStatus status;

    public ApiException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
