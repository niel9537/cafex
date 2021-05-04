package com.p3lb.cafex.model.transaksi;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostTransaksi {
    @SerializedName("status")
    String status;
    @SerializedName("result")
    List<Transaksi> listDataTransaksi;
    @SerializedName("message")
    String message;

    public PostTransaksi(String status, List<Transaksi> listDataTransaksi, String message) {
        this.status = status;
        this.listDataTransaksi = listDataTransaksi;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Transaksi> getListDataTransaksi() {
        return listDataTransaksi;
    }

    public void setListDataTransaksi(List<Transaksi> listDataTransaksi) {
        this.listDataTransaksi = listDataTransaksi;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
