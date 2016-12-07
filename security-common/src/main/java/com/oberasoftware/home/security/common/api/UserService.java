package com.oberasoftware.home.security.common.api;

import com.oberasoftware.home.security.common.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Renze de Vries
 */
public interface UserService {
    User createUser(String userName, String password, String email, List<String> roles);

    User updateUser(String userName, String password, String email, List<String> roles, Map<String, String> metadata);

    User updateMetadata(String userName, Map<String, String> metadata);

    User setMetadata(String userName, String key, String value);

    User updateRoles(String userName, List<String> roles);

    Optional<User> findUser(String clientId);

    boolean deleteUser(String clientId);
}
