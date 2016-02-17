package com.oberasoftware.home.user.service;

import com.oberasoftware.home.security.jasdb.JasDBConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

/**
 * @author Renze de Vries
 */
@SpringBootApplication
@EnableAspectJAutoProxy
@Import(JasDBConfiguration.class)
public class UserServiceContainer {
    private static final Logger LOG = LoggerFactory.getLogger(UserServiceContainer.class);

    public static void main(String[] args) {
        LOG.info("Starting user service endpoint");
        SpringApplication.run(UserServiceContainer.class, args);
    }
}
