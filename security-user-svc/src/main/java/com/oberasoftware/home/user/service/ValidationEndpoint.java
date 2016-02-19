package com.oberasoftware.home.user.service;

import com.oberasoftware.home.security.common.api.AuthenticationManager;
import com.oberasoftware.home.security.common.api.OAuthException;
import com.oberasoftware.home.security.common.api.UserService;
import com.oberasoftware.home.security.common.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
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
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Secured
    @RequestMapping(method = RequestMethod.GET, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> validate(HttpServletRequest request) {
        String clientId = request.getParameter("client_id");
        String token = request.getParameter("token");
        LOG.info("Validating token: {}", token);

        if(!StringUtils.isEmpty(clientId) && !StringUtils.isEmpty(token)) {
            try {
                if(authenticationManager.validate(clientId, token)) {
                    LOG.info("Token: {} is valid for client: {}", token, clientId);
                    Optional<User> user = userService.findUser(clientId);

                    return ResponseEntity.ok(user.get());
                } else {
                    LOG.info("Token is invalid");
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                            .body(Collections.singletonMap("error", "unauthorized user"));
                }

            } catch(OAuthException e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Collections.singletonMap("error", "unauthorized: " + e.getMessage()));
            }
        } else {
            LOG.error("Invalid request");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("error", "invalid request"));

        }
    }

}
