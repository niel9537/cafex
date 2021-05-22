package com.p3lb.cafex.model.user;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostUser {
    @SerializedName("status")
    String status;
    @SerializedName("result")
    List<User> userList;
    @SerializedName("message")
    String message;

    public PostUser(String status, List<User> userList, String message) {
        this.status = status;
        this.userList = userList;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
