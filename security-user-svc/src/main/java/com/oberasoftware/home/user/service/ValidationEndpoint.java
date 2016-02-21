package com.oberasoftware.home.user.service;

import com.oberasoftware.home.security.common.api.UserService;
import com.oberasoftware.home.security.common.model.User;
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
    private UserService userService;

    @Secured
    @RequestMapping(method = RequestMethod.GET, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> validate(HttpServletRequest request) {
        String clientId = request.getParameter("client_id");
        String token = request.getParameter("token");
        LOG.info("Validating token: {}", token);

        Optional<User> user = userService.findUser(clientId);

        if(user.isPresent()) {
            LOG.debug("Valid token: {} for user: {}", token, clientId);
            User internalUser = user.get();

            //wrap in a new response model to isolate sensitive fields
            return ResponseEntity.ok(new User(internalUser.getUserId(), internalUser.getUserName(), internalUser.getUserMail()));
        } else {
            LOG.debug("The user: {} could not be found, valid token: {}", clientId, token);
            return sendResponse("unauthorized user");
        }
    }

    private ResponseEntity<?> sendResponse(String message) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("error", message));
    }
}
