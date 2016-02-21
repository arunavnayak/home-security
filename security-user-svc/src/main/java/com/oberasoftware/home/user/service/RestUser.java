package com.oberasoftware.home.user.service;


import com.oberasoftware.home.security.common.model.User;

/**
 * This model wraps the regular User model, to shield some internal sensitive details.
 *
 * @author Renze de Vries
 */
public class RestUser extends User {
    private String userId;
    private String userName;
    private String userEmail;

    public RestUser(String userId, String userName, String userEmail) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
    }

    public RestUser() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
