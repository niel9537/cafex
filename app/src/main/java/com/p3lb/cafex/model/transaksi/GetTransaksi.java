package com.p3lb.cafex.model.transaksi;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetTransaksi {
    @SerializedName("status")
    String status;
    @SerializedName("result")
    List<DetailTransaksi> listDataUsers;
    @SerializedName("message")
    String message;

    public GetTransaksi(String status, List<DetailTransaksi> listDataUsers, String message) {
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

    public List<DetailTransaksi> getListDataUsers() {
        return listDataUsers;
    }

    public void setListDataUsers(List<DetailTransaksi> listDataUsers) {
        this.listDataUsers = listDataUsers;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
