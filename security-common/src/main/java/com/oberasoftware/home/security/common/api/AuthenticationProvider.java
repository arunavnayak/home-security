package com.oberasoftware.home.security.common.api;

import java.util.Optional;

/**
 * @author Renze de Vries
 */
public interface AuthenticationProvider {
    Optional<AuthenticatedUser> authenticate(String clientId, String password);
}
