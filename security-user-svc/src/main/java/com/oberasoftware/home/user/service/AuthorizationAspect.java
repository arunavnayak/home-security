package com.oberasoftware.home.user.service;

import com.oberasoftware.home.security.common.api.AuthenticationManager;
import com.oberasoftware.home.security.common.api.OAuthException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

/**
 * @author Renze de Vries
 */
@Aspect
@Component
public class AuthorizationAspect {
    private static final Logger LOG = LoggerFactory.getLogger(AuthorizationAspect.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Around("@annotation(com.oberasoftware.home.user.service.Secured) && args(request)")
    public Object authorizeRequest(ProceedingJoinPoint jp, HttpServletRequest request) {
        String clientId = request.getParameter("client_id");
        String token = request.getParameter("token");
        LOG.info("Validating token: {}", token);

        if(!StringUtils.isEmpty(clientId) && !StringUtils.isEmpty(token)) {
            try {
                if(authenticationManager.validate(clientId, token)) {
                    return jp.proceed();
                } else {
                    LOG.debug("Token is invalid: {}", token);
                    return sendResponse("unauthorized user");
                }
            } catch(OAuthException e) {
                return sendResponse("unauthorized user: " + e.getMessage());
            } catch (Throwable throwable) {
                LOG.error("Unknown error authenticating user: " + clientId, throwable);
                return sendResponse("unauthorized user");
            }
        } else {
            LOG.error("Invalid request, missing clientId or token");
            return sendResponse("invalid request");
        }
    }

    private ResponseEntity<?> sendResponse(String message) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("error", message));
    }
}
