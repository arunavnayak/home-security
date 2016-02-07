package com.oberasoftware.home.security.token;

import com.oberasoftware.home.security.AuthenticatedUser;
import com.oberasoftware.home.security.AuthenticationManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * @author Renze de Vries
 */
@RestController
public class TokenEndpoint {
    private static final Logger LOG = LoggerFactory.getLogger(TokenEndpoint.class);

    private static final String PASSWORD_GRANT = "password";

    private AuthenticationManager authenticationManager;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<?> doPost(HttpServletRequest request) {
        String grantType = request.getParameter("grant_type");
        String clientId = request.getParameter("client_id");
        String clientSecret = request.getParameter("client_secret");

        if(PASSWORD_GRANT.equalsIgnoreCase(grantType)) {
            Optional<AuthenticatedUser> user =
                    authenticationManager.authenticate(clientId, clientSecret);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("");
        }

    }
}
