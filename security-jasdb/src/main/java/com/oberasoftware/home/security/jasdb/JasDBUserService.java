package com.oberasoftware.home.security.jasdb;

import com.oberasoftware.home.security.UserService;
import com.oberasoftware.home.security.model.User;
import org.springframework.stereotype.Component;

/**
 * @author Renze de Vries
 */
@Component
public class JasDBUserService implements UserService {
    @Override
    public User createUser(String clientId, String password, String userName, String email) {
        return null;
    }

    @Override
    public boolean deleteUser(String clientId) {
        return false;
    }
}
