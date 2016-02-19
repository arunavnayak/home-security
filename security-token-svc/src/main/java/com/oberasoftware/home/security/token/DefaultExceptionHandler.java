package com.oberasoftware.home.security.token;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Renze de Vries
 */
@ControllerAdvice
public class DefaultExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultExceptionHandler.class);

    public static final String DEFAULT_ERROR_VIEW = "error";

    @ExceptionHandler(value = Exception.class)
    public void defaultErrorHandler(Exception e, HttpServletRequest req, HttpServletResponse response) throws Exception {
        response.setStatus(403);
        response.getWriter().write("{\"error\":\"unauthorized\"}");
    }

}
