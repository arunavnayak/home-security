package com.oberasoftware.home.user.service.model;

/**
 * @author Renze de Vries
 */
public class ControllerResourceModel {
    private String controllerId;

    public ControllerResourceModel(String controllerId) {
        this.controllerId = controllerId;
    }

    public ControllerResourceModel() {
    }

    public String getControllerId() {
        return controllerId;
    }

    public void setControllerId(String controllerId) {
        this.controllerId = controllerId;
    }
}
