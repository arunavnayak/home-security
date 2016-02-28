package com.oberasoftware.home.security.common.api;

import com.oberasoftware.home.security.common.model.Role;
import com.oberasoftware.home.security.common.model.User;

import java.util.List;

/**
 * @author Renze de Vries
 */
public interface AuthenticatedResource {
    <T extends User> T getResource();

    List<Role> getRoles();
}
