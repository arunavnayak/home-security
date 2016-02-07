package com.oberasoftware.home.security.token;

import com.oberasoftware.home.security.AuthenticatedUser;
import com.oberasoftware.home.security.AuthenticationManager;
import com.oberasoftware.home.security.GrantTypeHandler;
import com.oberasoftware.home.security.OAuthException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Renze de Vries
 */
@RestController
public class TokenEndpoint {
    private static final Logger LOG = LoggerFactory.getLogger(TokenEndpoint.class);

    @Autowired
    private List<GrantTypeHandler> grantHandlers;

    private Map<String, GrantTypeHandler> mappedHandlers;

    @PostConstruct
    public void initialize() {
        mappedHandlers = grantHandlers.stream()
                .collect(Collectors.toMap(GrantTypeHandler::getSupportedGrantType, g -> g));
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> doPost(HttpServletRequest request) {
        String grantType = request.getParameter("grant_type");
        String clientId = request.getParameter("client_id");
        LOG.debug("Received a token request of grant type: {} for client_id: {}", grantType, clientId);

        try {
            GrantTypeHandler handler = getHandler(grantType);
            return handler.authenticate(clientId, request);
        } catch(OAuthException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("error", "unauthorized: " + e.getMessage()));
        }
    }

    private GrantTypeHandler getHandler(String grantType) {
        if(mappedHandlers.containsKey(grantType)) {
            return mappedHandlers.get(grantType);
        } else {
            throw new OAuthException("Unsupported grant type: " + grantType);
        }
    }
}
