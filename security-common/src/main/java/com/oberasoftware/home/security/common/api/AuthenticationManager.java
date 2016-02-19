package com.oberasoftware.home.security.common.api;

import java.util.Optional;

/**
 * @author Renze de Vries
 */
public interface AuthenticationManager {

    Optional<AuthenticatedUser> authenticate(String clientId, String password);

    boolean validate(String clientId, String token);


}
