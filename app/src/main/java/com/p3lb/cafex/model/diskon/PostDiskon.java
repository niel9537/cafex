package com.p3lb.cafex.model.diskon;

import com.google.gson.annotations.SerializedName;
import com.p3lb.cafex.model.produk.Products;

import java.util.List;

public class PostDiskon {
    @SerializedName("status")
    String status;
    @SerializedName("diskon")
    List<Diskon> diskonList = null;
    @SerializedName("message")
    String message;

    public PostDiskon(String status, List<Diskon> diskonList, String message) {
        this.status = status;
        this.diskonList = diskonList;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Diskon> getDiskonList() {
        return diskonList;
    }

    public void setDiskonList(List<Diskon> diskonList) {
        this.diskonList = diskonList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
