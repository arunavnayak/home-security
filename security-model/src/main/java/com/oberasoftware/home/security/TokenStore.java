package com.oberasoftware.home.security;

import com.oberasoftware.home.security.model.Token;

import java.util.Optional;

/**
 * @author Renze de Vries
 */
public interface TokenStore {
    boolean storeToken(String clientId, Token token);

    Optional<Token> validateToken(String token);
}
