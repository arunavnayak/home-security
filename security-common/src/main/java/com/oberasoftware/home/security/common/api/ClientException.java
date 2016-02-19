package com.oberasoftware.home.security.common.api;

import com.oberasoftware.home.api.exceptions.RuntimeHomeAutomationException;

/**
 * @author Renze de Vries
 */
public class ClientException extends RuntimeHomeAutomationException {
    public ClientException(String message, Throwable e) {
        super(message, e);
    }

    public ClientException(String message) {
        super(message);
    }
}
