package com.p3lb.cafex.model.trxhbpbulan;

import com.google.gson.annotations.SerializedName;

public class Trxhbpbulan {
    @SerializedName("total_transaksi")
    private String total_transaksi;
    @SerializedName("total_biayaproduk")
    private String total_biayaproduk;

    public Trxhbpbulan(String total_transaksi, String total_biayaproduk) {
        this.total_transaksi = total_transaksi;
        this.total_biayaproduk = total_biayaproduk;
    }

    public String getTotal_transaksi() {
        return total_transaksi;
    }

    public void setTotal_transaksi(String total_transaksi) {
        this.total_transaksi = total_transaksi;
    }

    public String getTotal_biayaproduk() {
        return total_biayaproduk;
    }

    public void setTotal_biayaproduk(String total_biayaproduk) {
        this.total_biayaproduk = total_biayaproduk;
    }
}
