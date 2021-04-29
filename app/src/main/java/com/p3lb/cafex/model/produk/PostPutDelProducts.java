package com.p3lb.cafex.model.produk;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostPutDelProducts {
    @SerializedName("status")
    String status;
    @SerializedName("result")
    List<Products> listDataProducts;
    @SerializedName("message")
    String message;

    public PostPutDelProducts(String status, List<Products> listDataProducts, String message) {
        this.status = status;
        this.listDataProducts = listDataProducts;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public List<Products> getListDataProducts() {
        return listDataProducts;
    }
    public void setListDataProducts(List<Products> listDataProducts) {
        this.listDataProducts = listDataProducts;
    }
}
