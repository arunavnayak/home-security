package com.oberasoftware.home.security.common.api;

import com.oberasoftware.home.security.common.model.Token;

import java.util.Optional;

/**
 * @author Renze de Vries
 */
public interface TokenStore {
    boolean storeToken(String clientId, Token token);

    Optional<Token> validateToken(String clientId, String token);
}
