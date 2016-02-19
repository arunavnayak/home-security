package com.oberasoftware.home.security;

import com.oberasoftware.home.security.model.Token;

import java.util.Optional;

/**
 * @author Renze de Vries
 */
public interface AuthenticationManager extends AuthenticationProvider {

    boolean validate(String clientId, String token);


}
