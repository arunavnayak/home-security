package com.oberasoftware.home.user.service.aspects;

import com.google.common.collect.Sets;
import com.oberasoftware.home.security.common.api.AuthenticationManager;
import com.oberasoftware.home.security.common.api.OAuthException;
import com.oberasoftware.home.security.common.api.UserService;
import com.oberasoftware.home.security.common.model.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author Renze de Vries
 */
@Aspect
@Component
public class AuthorizationAspect {
    private static final Logger LOG = LoggerFactory.getLogger(AuthorizationAspect.class);
    private static final String NOT_AUTHORIZED_MESSAGE = "Not authorized";

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Around("@annotation(com.oberasoftware.home.user.service.aspects.OwnerSecured)")
    public Object authorizeRequest(ProceedingJoinPoint jp) {
        HttpServletRequest request = getRequest(jp);
        String clientId = request.getParameter("client_id");
        String token = request.getParameter("token");
        LOG.debug("Validating user token: {}", token);

        return authenticated(clientId, token) ? executeJointPoint(jp, clientId) : sendResponse(NOT_AUTHORIZED_MESSAGE);
    }

    @Around("@annotation(com.oberasoftware.home.user.service.aspects.PrivilegedSecured)")
    public Object authorizePrivilegedRequests(ProceedingJoinPoint jp) {
        HttpServletRequest request = getRequest(jp);
        String clientId = request.getParameter("client_id");
        String token = request.getParameter("token");
        LOG.debug("Validating internal resource token: {}", token);

        if(authenticated(clientId, token)) {
            MethodSignature signature = (MethodSignature) jp.getSignature();
            PrivilegedSecured secured = signature.getMethod().getAnnotation(PrivilegedSecured.class);
            Set<String> allowedRoles = Sets.newHashSet(secured.allowedRoles());

            Optional<User> user = userService.findUser(clientId);
            if(user.isPresent()) {
                List<String> roles = user.get().getRoles();

                boolean allowed = roles.stream().anyMatch(allowedRoles::contains);
                LOG.debug("Client: {} with roles: {} is allowed: {}", clientId, roles, allowed);
                if(allowed) {
                    return executeJointPoint(jp, clientId);
                }
            } else {
                LOG.debug("User: {} cannot be found", clientId);
            }
        } else {
            LOG.debug("User: {} is not authenticated", clientId);
        }

        return sendResponse(NOT_AUTHORIZED_MESSAGE);
    }

    private HttpServletRequest getRequest(ProceedingJoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Optional<Object> optionalHttpRequestParam = Arrays.stream(args)
                .filter(a -> HttpServletRequest.class.isAssignableFrom(a.getClass())).findFirst();
        if(optionalHttpRequestParam.isPresent()) {
            return (HttpServletRequest) optionalHttpRequestParam.get();
        } else {
            throw new OAuthException("Unable to find http request");
        }
    }

    private Object executeJointPoint(ProceedingJoinPoint jp, String clientId) {
        try {
            return jp.proceed();
        } catch (Throwable throwable) {
            LOG.error("Unknown error authenticating user: " + clientId, throwable);
            return sendResponse("unauthorized user");
        }
    }

    private boolean authenticated(String clientId, String token) throws OAuthException {
        if(!StringUtils.isEmpty(clientId) && !StringUtils.isEmpty(token)) {
            if(authenticationManager.validate(clientId, token)) {
                LOG.debug("Token is valid");
                return true;
            } else {
                LOG.debug("Token is invalid: {}", token);
                return false;
            }
        } else {
            LOG.error("Invalid request, missing clientId or token");
        }

        return false;
    }

    private ResponseEntity<?> sendResponse(String message) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("error", message));
    }
}
