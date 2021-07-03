package com.p3lb.cafex.model.auth;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Login {
    @SerializedName("status")
    String status;
    @SerializedName("result")
    List<LoginUsers> loginUsers;
    @SerializedName("message")
    String message;

    public Login(String status, List<LoginUsers> loginUsers, String message) {
        this.status = status;
        this.loginUsers = loginUsers;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<LoginUsers> getLoginUsers() {
        return loginUsers;
    }

    public void setLoginUsers(List<LoginUsers> loginUsers) {
        this.loginUsers = loginUsers;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
