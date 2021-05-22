package com.p3lb.cafex.model.inventori;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostInventori {
    @SerializedName("status")
    String status;
    @SerializedName("result")
    List<Inventori> inventoriList;
    @SerializedName("message")
    String message;

    public PostInventori(String status, List<Inventori> inventoriList, String message) {
        this.status = status;
        this.inventoriList = inventoriList;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Inventori> getInventoriList() {
        return inventoriList;
    }

    public void setInventoriList(List<Inventori> inventoriList) {
        this.inventoriList = inventoriList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
