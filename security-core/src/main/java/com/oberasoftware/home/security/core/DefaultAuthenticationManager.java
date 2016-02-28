package com.oberasoftware.home.security.core;

import com.oberasoftware.home.security.common.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Optional;
import java.util.UUID;

import static com.google.common.collect.Lists.newArrayList;

/**
 * @author Renze de Vries
 */
@Component
public class DefaultAuthenticationManager implements AuthenticationManager {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultAuthenticationManager.class);

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private AuthenticationProvider provider;

    @Autowired
    private UserService userService;

    @PostConstruct
    public void initialize() {
        if(!userService.findUser("admin").isPresent()) {
            String adminPassword = UUID.randomUUID().toString();
            userService.createUser("admin", adminPassword, "admin@local", newArrayList("admin"));
            userService.createUser("simpleuser", adminPassword, "admin@local", newArrayList("user"));
            userService.createUser("internaluser", adminPassword, "admin@local", newArrayList("trustedResource"));
            LOG.info("No Admin user existed, created user 'admin' with password '{}'", adminPassword);
        }
    }

    @Override
    public boolean validate(String clientId, String token) {
        LOG.debug("Validating token in the token store: {}", token);
        return tokenStore.validateToken(clientId, token).isPresent();
    }

    @Override
    public Optional<AuthenticatedResource> authenticate(String clientId, String password) {
        return provider.authenticate(clientId, password);
    }
}
