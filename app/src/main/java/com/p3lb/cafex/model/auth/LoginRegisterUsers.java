package com.p3lb.cafex.model.auth;

import com.google.gson.annotations.SerializedName;
import com.p3lb.cafex.model.produk.Products;

import java.util.List;

public class LoginRegisterUsers {
    @SerializedName("status")
    String status;
    @SerializedName("result")
    List<Users> listDataUsers;
    @SerializedName("message")
    String message;

    public LoginRegisterUsers(String status, List<Users> listDataUsers, String message) {
        this.status = status;
        this.listDataUsers = listDataUsers;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Users> getListDataUsers() {
        return listDataUsers;
    }

    public void setListDataUsers(List<Users> listDataUsers) {
        this.listDataUsers = listDataUsers;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
