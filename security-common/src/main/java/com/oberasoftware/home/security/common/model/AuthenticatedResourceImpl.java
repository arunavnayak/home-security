package com.oberasoftware.home.security.common.model;


import com.oberasoftware.home.security.common.api.AuthenticatedResource;

import java.util.List;

/**
 * @author Renze de Vries
 */
public class AuthenticatedResourceImpl<T extends User> implements AuthenticatedResource {

    private final T resource;
    private final List<Role> roles;

    public AuthenticatedResourceImpl(T resource, List<Role> roles) {
        this.resource = resource;
        this.roles = roles;
    }

    @Override
    public T getResource() {
        return resource;
    }

    @Override
    public List<Role> getRoles() {
        return roles;
    }

    @Override
    public String toString() {
        return "AuthenticatedResourceImpl{" +
                "resource=" + resource +
                ", roles=" + roles +
                '}';
    }
}
