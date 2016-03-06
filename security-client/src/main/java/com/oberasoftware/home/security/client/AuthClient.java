package com.oberasoftware.home.security.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oberasoftware.home.security.client.model.OAuthToken;
import com.oberasoftware.home.security.client.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Renze de Vries
 */
public class AuthClient extends BaseClient {
    private static final Logger LOG = LoggerFactory.getLogger(AuthClient.class);

    public AuthClient(String endpointUrl) {
        super(endpointUrl);
    }

    public OAuthToken getToken(String clientId, String clientSecret) {
        Map<String, String> clientParams = new HashMap<>();
        clientParams.put("client_id", clientId);
        clientParams.put("client_secret", clientSecret);
        clientParams.put("grant_type", "password");

        ClientResponse response = doInternalRequest(getEndpointUrl(), clientParams, "".getBytes(), REQUEST_MODE.POST);
        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode rootNode = mapper.readValue(response.getEntityAsString(), JsonNode.class);
            if(!rootNode.has("error") && rootNode.has("token")) {
                String token = rootNode.get("token").asText();
                long expires = rootNode.get("expires").asLong();
                LOG.debug("Received a token: {} expires in: {}", token, expires);
                return new OAuthToken(token, expires);
            } else {
                String message = rootNode.get("error").asText();
                throw new ClientException("Unable to obtain token: " + message);
            }
        } catch (IOException e) {
            throw new ClientException("Unable to obtain token");
        }
    }

    public User getUser(String clientId, String token) {
        Map<String, String> clientParams = new HashMap<>();
        clientParams.put("client_id", clientId);
        clientParams.put("token", token);

        ClientResponse response = doInternalRequest(getEndpointUrl(), clientParams, "".getBytes(), REQUEST_MODE.GET);
        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode rootNode = mapper.readValue(response.getEntityAsString(), JsonNode.class);
            if(!rootNode.has("error") && rootNode.has("userId")) {
                String userId = rootNode.get("userId").asText();
                String userName = rootNode.get("userName").asText();
                String userMail = rootNode.get("userEmail").asText();

                LOG.debug("Received a user with id: {} username: {}", userId, userName);
                return new User(userId, userName, userMail);
            } else {
                String message = rootNode.get("error").asText();
                throw new ClientException("Unable to obtain user details: " + message);
            }
        } catch (IOException e) {
            throw new ClientException("Unable to obtain user details");
        }
    }

    public User createController(String token, String controllerId) {
        return null;
    }
}
