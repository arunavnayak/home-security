package com.oberasoftware.home.security.common.api;

import com.oberasoftware.home.security.common.model.Token;

/**
 * @author Renze de Vries
 */
public interface TokenManager {

    Token generateToken(String clientId);

    Token generateToken(String clientId, Token.TOKEN_TYPE type);
}
