package com.oberasoftware.home.security;

import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Renze de Vries
 */
public interface GrantTypeHandler {
    ResponseEntity<?> authenticate(String clientId, HttpServletRequest request);

    String getSupportedGrantType();
}
