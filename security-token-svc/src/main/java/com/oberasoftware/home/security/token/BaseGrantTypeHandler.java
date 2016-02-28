package com.oberasoftware.home.security.token;

import com.oberasoftware.home.security.common.api.*;
import com.oberasoftware.home.security.common.model.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * @author Renze de Vries
 */
public abstract class BaseGrantTypeHandler implements GrantTypeHandler {
    private static final Logger LOG = LoggerFactory.getLogger(BaseGrantTypeHandler.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenManager tokenManager;

    @Autowired
    private TokenStore tokenStore;

    @Override
    public ResponseEntity<?> authenticate(String clientId, HttpServletRequest request) {
        LOG.debug("Handling password grant for client: {}", clientId);
        String clientSecret = request.getParameter("client_secret");
        Optional<AuthenticatedResource> u = authenticationManager.authenticate(clientId, clientSecret);
        if(u.isPresent()) {
            Token token = tokenManager.generateToken(clientId);
            if(tokenStore.storeToken(clientId, token)) {
                LOG.debug("Token: {} stored for clientId: {}", token, clientId);

                return ResponseEntity.ok().body(token);
            } else {
                LOG.error("Could not store token: {} for client: {}", token, clientId);
                throw new OAuthException("Cannot store token");
            }
        } else {
            LOG.debug("Could not authenticate user: {}", clientId);
            throw new OAuthException("Unauthorized");
        }
    }
}
