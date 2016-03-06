package com.oberasoftware.home.security.client;

/**
 * @author Renze de Vries
 */
public class ClientException extends RuntimeException {
    public ClientException() {
    }

    public ClientException(String message) {
        super(message);
    }

    public ClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
