package com.oberasoftware.home.security.jasdb;

import com.oberasoftware.home.security.AuthenticatedUser;
import com.oberasoftware.home.security.model.Role;
import com.oberasoftware.home.security.model.User;

import java.util.List;

/**
 * @author Renze de Vries
 */
public class AuthenticatedUserImpl implements AuthenticatedUser {

    private final User user;
    private final List<Role> roles;

    public AuthenticatedUserImpl(User user, List<Role> roles) {
        this.user = user;
        this.roles = roles;
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public List<Role> getRoles() {
        return roles;
    }

    @Override
    public String toString() {
        return "AuthenticatedUserImpl{" +
                "user=" + user +
                ", roles=" + roles +
                '}';
    }
}
