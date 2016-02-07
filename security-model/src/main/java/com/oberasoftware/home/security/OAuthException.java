package com.oberasoftware.home.security;

import com.oberasoftware.home.api.exceptions.RuntimeHomeAutomationException;

/**
 * @author Renze de Vries
 */
public class OAuthException extends RuntimeHomeAutomationException {
    public OAuthException(String message, Throwable e) {
        super(message, e);
    }

    public OAuthException(String message) {
        super(message);
    }
}
