package com.oberasoftware.home.user.service.model;

import java.util.List;

/**
 * @author Renze de Vries
 */
public class CreateUserModel {
    private String userName;
    private String userEmail;
    private String userPassword;

    private List<String> roles;

    public CreateUserModel(String userName, String userEmail, String userPassword, List<String> roles) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.roles = roles;
    }

    public CreateUserModel() {
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

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "CreateUserModel{" +
                "userName='" + userName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userPassword='" + userPassword + '\'' +
                '}';
    }
}
