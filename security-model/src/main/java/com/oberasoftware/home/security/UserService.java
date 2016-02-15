package com.oberasoftware.home.security;

import com.oberasoftware.home.security.model.User;

import java.util.Optional;

/**
 * @author Renze de Vries
 */
public interface UserService {
    User createUser(String clientId, String password, String email);

    Optional<User> findUser(String clientId);

    boolean deleteUser(String clientId);
}
