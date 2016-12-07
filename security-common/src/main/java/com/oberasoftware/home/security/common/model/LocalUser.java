package com.oberasoftware.home.security.common.model;

import com.oberasoftware.jasdb.api.entitymapper.annotations.JasDBEntity;
import com.oberasoftware.jasdb.api.entitymapper.annotations.JasDBProperty;

import java.util.HashMap;
import java.util.List;

/**
 * @author Renze de Vries
 */
@JasDBEntity(bagName = "LocalUsers")
public class LocalUser extends User {
    private String passwordHash;
    private String salt;

    public LocalUser(String userId, String userName, String userMail, List<String> roles, String passwordHash, String salt) {
        super(userId, userName, userMail, roles, new HashMap<>());
        this.passwordHash = passwordHash;
        this.salt = salt;
    }

    public LocalUser() {
    }

    @JasDBProperty
    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    @JasDBProperty
    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        LocalUser localUser = (LocalUser) o;

        if (!passwordHash.equals(localUser.passwordHash)) return false;
        return salt.equals(localUser.salt);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + passwordHash.hashCode();
        result = 31 * result + salt.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "LocalUser{" +
                "passwordHash='" + passwordHash + '\'' +
                ", salt='" + salt + '\'' +
                '}';
    }
}
