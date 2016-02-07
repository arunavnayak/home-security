package com.oberasoftware.home.security;

import com.oberasoftware.home.security.model.Token;

/**
 * @author Renze de Vries
 */
public interface AuthenticationManager extends AuthenticationProvider {

    boolean validate(String clientId, Token token);


}
