package com.oberasoftware.home.security.token;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Renze de Vries
 */
@SpringBootApplication
@ComponentScan
public class TokenEndpointContainer {
    private static final Logger LOG = LoggerFactory.getLogger(TokenEndpointContainer.class);

    public static void main(String[] args) {
        LOG.info("Starting token endpoint");
        new SpringApplication(TokenEndpointContainer.class).run(args);
    }
}
