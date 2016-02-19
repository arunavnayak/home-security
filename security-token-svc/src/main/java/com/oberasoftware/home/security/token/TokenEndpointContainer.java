package com.oberasoftware.home.security.token;

import com.oberasoftware.home.security.core.CommonConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

/**
 * @author Renze de Vries
 */
@SpringBootApplication
@ComponentScan
@Import(CommonConfiguration.class)
public class TokenEndpointContainer {
    private static final Logger LOG = LoggerFactory.getLogger(TokenEndpointContainer.class);

    public static void main(String[] args) {
        LOG.info("Starting token endpoint");
        new SpringApplication(TokenEndpointContainer.class).run(args);
    }
}
