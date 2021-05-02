package com.p3lb.cafex.model.transaksi;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostPutDelTransaksi {
    @SerializedName("status")
    String status;
    @SerializedName("result")
    List<DetailTransaksi> listDataDetailTransaksi;
    @SerializedName("message")
    String message;

    public PostPutDelTransaksi(String status, List<DetailTransaksi> listDataDetailTransaksi, String message) {
        this.status = status;
        this.listDataDetailTransaksi = listDataDetailTransaksi;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DetailTransaksi> getListDataDetailTransaksi() {
        return listDataDetailTransaksi;
    }

    public void setListDataDetailTransaksi(List<DetailTransaksi> listDataDetailTransaksi) {
        this.listDataDetailTransaksi = listDataDetailTransaksi;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
