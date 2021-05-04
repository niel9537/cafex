package com.p3lb.cafex.model.transaksi;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostPutDelCart {
    @SerializedName("status")
    String status;
    @SerializedName("result")
    List<Cart> listDataCart;
    @SerializedName("message")
    String message;

    public PostPutDelCart(String status, List<Cart> listDataCart, String message) {
        this.status = status;
        this.listDataCart = listDataCart;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Cart> getListDataCart() {
        return listDataCart;
    }

    public void setListDataCart(List<Cart> listDataCart) {
        this.listDataCart = listDataCart;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
