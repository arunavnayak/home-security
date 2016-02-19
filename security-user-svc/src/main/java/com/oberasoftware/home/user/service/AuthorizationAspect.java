package com.oberasoftware.home.user.service;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Renze de Vries
 */
@Aspect
@Component
public class AuthorizationAspect {
    private static final Logger LOG = LoggerFactory.getLogger(AuthorizationAspect.class);


//    @Around("execution(* com.oberasoftware.home.user.service.ValidationEndpoint.*(..))")
    @Around("@annotation(com.oberasoftware.home.user.service.Secured) && args(request)")
    public Object authorizeRequest(ProceedingJoinPoint jp, HttpServletRequest request) {
        LOG.info("Authorizing request?");

        try {
            return jp.proceed();
        } catch (Throwable e) {
            LOG.error("", e);
            return null;
        }
    }
}
