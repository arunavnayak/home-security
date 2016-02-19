package com.oberasoftware.home.security.common.api;

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
