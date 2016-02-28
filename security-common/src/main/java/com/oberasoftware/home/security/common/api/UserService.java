package com.oberasoftware.home.security.common.api;

import com.oberasoftware.home.security.common.model.User;

import java.util.List;
import java.util.Optional;

/**
 * @author Renze de Vries
 */
public interface UserService {
    User createUser(String userName, String password, String email, List<String> roles);

    User updateUser(String userName, String password, String email, List<String> roles, List<String> controllers);

    User updateRoles(String userName, List<String> roles);

    User createControllerUser(String userName, String controllerId, String password);

    Optional<User> findUser(String clientId);

    boolean deleteUser(String clientId);
}
