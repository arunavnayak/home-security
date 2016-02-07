package com.oberasoftware.home.security.jasdb;

import com.oberasoftware.home.security.TokenStore;
import com.oberasoftware.home.security.model.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author Renze de Vries
 */
@Component
public class JasDBTokenStore implements TokenStore {

    @Autowired
    private JasDBSessionFactory sessionFactory;

    @Override
    public boolean storeToken(String clientId, Token token) {
        return false;
    }

    @Override
    public Optional<Token> validateToken(String token) {
        return null;
    }
}
