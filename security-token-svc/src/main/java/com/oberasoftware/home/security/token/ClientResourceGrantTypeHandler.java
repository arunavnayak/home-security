package com.oberasoftware.home.security.token;

import org.springframework.stereotype.Component;

/**
 * @author Renze de Vries
 */
@Component
public class ClientResourceGrantTypeHandler extends BaseGrantTypeHandler {
    private static final String CLIENT_GRANT = "client_credentials";

    @Override
    public String getSupportedGrantType() {
        return CLIENT_GRANT;
    }
}
