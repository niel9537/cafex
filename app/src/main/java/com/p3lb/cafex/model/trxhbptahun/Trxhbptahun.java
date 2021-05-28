package com.p3lb.cafex.model.trxhbptahun;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class Trxhbptahun {
    @SerializedName("total_transaksi")
    private String total_transaksi;
    @SerializedName("total_biayaproduk")
    @Nullable
    private String total_biayaproduk;

    public Trxhbptahun(String total_transaksi, @Nullable String total_biayaproduk) {
        this.total_transaksi = total_transaksi;
        this.total_biayaproduk = total_biayaproduk;
    }

    public String getTotal_transaksi() {
        return total_transaksi;
    }

    public void setTotal_transaksi(String total_transaksi) {
        this.total_transaksi = total_transaksi;
    }

    @Nullable
    public String getTotal_biayaproduk() {
        return total_biayaproduk;
    }

    public void setTotal_biayaproduk(@Nullable String total_biayaproduk) {
        this.total_biayaproduk = total_biayaproduk;
    }
}
