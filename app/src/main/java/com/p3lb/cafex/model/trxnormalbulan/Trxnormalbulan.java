package com.p3lb.cafex.model.trxnormalbulan;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class Trxnormalbulan {
    @SerializedName("total_transaksi")
    @Nullable
    private String total_transaksi;
    @SerializedName("jumlah_transaksi")
    @Nullable
    private String jumlah_transaksi;

    public Trxnormalbulan(@Nullable String total_transaksi, @Nullable String jumlah_transaksi) {
        this.total_transaksi = total_transaksi;
        this.jumlah_transaksi = jumlah_transaksi;
    }

    @Nullable
    public String getTotal_transaksi() {
        return total_transaksi;
    }

    public void setTotal_transaksi(@Nullable String total_transaksi) {
        this.total_transaksi = total_transaksi;
    }

    @Nullable
    public String getJumlah_transaksi() {
        return jumlah_transaksi;
    }

    public void setJumlah_transaksi(@Nullable String jumlah_transaksi) {
        this.jumlah_transaksi = jumlah_transaksi;
    }
}
