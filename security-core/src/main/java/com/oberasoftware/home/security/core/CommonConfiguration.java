package com.oberasoftware.home.security.core;

import com.oberasoftware.home.security.jasdb.JasDBConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author Renze de Vries
 */
@Configuration
@ComponentScan
@Import(JasDBConfiguration.class)
public class CommonConfiguration {
}
