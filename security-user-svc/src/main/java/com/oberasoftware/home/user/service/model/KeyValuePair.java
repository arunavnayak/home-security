package com.oberasoftware.home.user.service.model;

/**
 * @author Renze de Vries
 */
public class KeyValuePair {
    private String key;
    private String value;

    public KeyValuePair() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "KeyValuePair{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
