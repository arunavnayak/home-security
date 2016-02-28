package com.oberasoftware.home.security.client;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Renze de Vries
 */
public class AuthClient extends BaseClient {
    public AuthClient(String endpointUrl) {
        super(endpointUrl);
    }

    public String getToken(String clientId, String clientSecret) {
        Map<String, String> clientParams = new HashMap<>();
        clientParams.put("client_id", clientId);
        clientParams.put("client_secret", clientSecret);
        clientParams.put("grant_type", "password");

        ClientResponse response = doInternalRequest(getEndpointUrl(), clientParams, "".getBytes(), REQUEST_MODE.POST);


        return null;
    }
}
