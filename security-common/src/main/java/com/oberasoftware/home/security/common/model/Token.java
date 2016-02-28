package com.oberasoftware.home.security.common.model;

import com.oberasoftware.jasdb.api.entitymapper.annotations.JasDBEntity;
import com.oberasoftware.jasdb.api.entitymapper.annotations.JasDBProperty;

/**
 * @author Renze de Vries
 */
@JasDBEntity(bagName = "Tokens")
public class Token {

    public enum TOKEN_TYPE {
        USER_TOKEN,
        ITEM_TOKEN
    }

    private String tokenId;
    private String resourceId;
    private String token;
    private long expiresIn;
    private TOKEN_TYPE tokenType;

    public Token(String tokenId, String resourceId, String token, long expiresIn, TOKEN_TYPE tokenType) {
        this.tokenId = tokenId;
        this.resourceId = resourceId;
        this.token = token;
        this.expiresIn = expiresIn;
        this.tokenType = tokenType;
    }

    public Token() {
    }

    @JasDBProperty
    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    @JasDBProperty
    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    @JasDBProperty
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @JasDBProperty
    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    @JasDBProperty
    public TOKEN_TYPE getTokenType() {
        return tokenType;
    }

    public void setTokenType(TOKEN_TYPE tokenType) {
        this.tokenType = tokenType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Token token1 = (Token) o;

        if (expiresIn != token1.expiresIn) return false;
        if (!tokenId.equals(token1.tokenId)) return false;
        if (!resourceId.equals(token1.resourceId)) return false;
        if (!token.equals(token1.token)) return false;
        return tokenType == token1.tokenType;

    }

    @Override
    public int hashCode() {
        int result = tokenId.hashCode();
        result = 31 * result + resourceId.hashCode();
        result = 31 * result + token.hashCode();
        result = 31 * result + (int) (expiresIn ^ (expiresIn >>> 32));
        result = 31 * result + tokenType.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Token{" +
                "tokenId='" + tokenId + '\'' +
                ", resourceId='" + resourceId + '\'' +
                ", token='" + token + '\'' +
                ", expiresIn=" + expiresIn +
                ", tokenType=" + tokenType +
                '}';
    }
}
