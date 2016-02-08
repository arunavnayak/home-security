package com.oberasoftware.home.security;

import com.oberasoftware.home.security.model.User;

/**
 * @author Renze de Vries
 */
public interface UserService {
    User createUser(String clientId, String password, String email);

    boolean deleteUser(String clientId);
}
