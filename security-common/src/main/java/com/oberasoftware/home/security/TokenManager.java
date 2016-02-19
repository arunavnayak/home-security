package com.oberasoftware.home.security;

import com.oberasoftware.home.security.model.Token;

import java.util.Optional;

/**
 * @author Renze de Vries
 */
public interface TokenManager {

    Token generateToken(String clientId);

    Token generateToken(String clientId, Token.TOKEN_TYPE type);
}
