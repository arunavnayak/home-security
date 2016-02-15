package com.oberasoftware.home.security.validation;

import com.oberasoftware.home.security.GrantTypeHandler;
import com.oberasoftware.home.security.OAuthException;
import com.oberasoftware.home.security.TokenStore;
import com.oberasoftware.home.security.UserService;
import com.oberasoftware.home.security.model.Token;
import com.oberasoftware.home.security.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Optional;

/**
 * @author Renze de Vries
 */
@RestController
@RequestMapping("/me")
public class ValidationEndpoint {
    private static final Logger LOG = LoggerFactory.getLogger(ValidationEndpoint.class);

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> validate(HttpServletRequest request) {
        String clientId = request.getParameter("client_id");
        String token = request.getParameter("token");
        LOG.debug("Validating token: {}", token);

        try {
            Optional<Token> validatedToken = tokenStore.validateToken(clientId, token);
            if(validatedToken.isPresent()) {
                Optional<User> user = userService.findUser(clientId);

                return ResponseEntity.ok(user.get());
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Collections.singletonMap("error", "unauthorized user"));
            }

        } catch(OAuthException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("error", "unauthorized: " + e.getMessage()));
        }
    }

}
