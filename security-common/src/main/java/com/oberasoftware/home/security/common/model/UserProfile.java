package com.oberasoftware.home.security.common.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Renze de Vries
 */
public class UserProfile {
    private String userId;
    private Map<String, String> properties = new HashMap<>();

    public UserProfile(String userId, Map<String, String> properties) {
        this.userId = userId;
        this.properties = properties;
    }

    public UserProfile(String userId) {
        this.userId = userId;
    }

    public UserProfile() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public void addProperty(String key, String value) {
        this.properties.put(key, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserProfile that = (UserProfile) o;

        if (!userId.equals(that.userId)) return false;
        return properties.equals(that.properties);

    }

    @Override
    public int hashCode() {
        int result = userId.hashCode();
        result = 31 * result + properties.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "UserProfile{" +
                "userId='" + userId + '\'' +
                ", properties=" + properties +
                '}';
    }
}
