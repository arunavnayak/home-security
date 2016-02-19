package com.oberasoftware.home.security.token;

import com.oberasoftware.home.security.common.api.*;
import com.oberasoftware.home.security.common.model.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * @author Renze de Vries
 */
@Component
public class PasswordGrantTypeHandler implements GrantTypeHandler {
    private static final Logger LOG = LoggerFactory.getLogger(PasswordGrantTypeHandler.class);

    private static final String PASSWORD_GRANT = "password";

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
        Optional<AuthenticatedUser> u = authenticationManager.authenticate(clientId, clientSecret);
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

    @Override
    public String getSupportedGrantType() {
        return PASSWORD_GRANT;
    }
}
