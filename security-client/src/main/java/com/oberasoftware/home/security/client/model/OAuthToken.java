package com.oberasoftware.home.security.client.model;

/**
 * @author Renze de Vries
 */
public class OAuthToken {
    private final String token;
    private final long expires;

    public OAuthToken(String token, long expires) {
        this.token = token;
        this.expires = expires;
    }

    public String getToken() {
        return token;
    }

    public long getExpires() {
        return expires;
    }

    @Override
    public String toString() {
        return "OAuthToken{" +
                "token='" + token + '\'' +
                ", expires=" + expires +
                '}';
    }
}
