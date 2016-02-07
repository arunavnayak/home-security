package com.oberasoftware.home.security;

import com.oberasoftware.home.security.model.Role;
import com.oberasoftware.home.security.model.User;

import java.util.List;

/**
 * @author Renze de Vries
 */
public interface AuthenticatedUser {
    User getUser();

    List<Role> getRoles();
}
