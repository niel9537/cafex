package com.p3lb.cafex.model.trxnormaltahun;

import com.google.gson.annotations.SerializedName;

public class Trxnormaltahun {
    @SerializedName("total_transaksi")
    private String total_transaksi;
    @SerializedName("jumlah_transaksi")
    private String jumlah_transaksi;

    public Trxnormaltahun(String total_transaksi, String jumlah_transaksi) {
        this.total_transaksi = total_transaksi;
        this.jumlah_transaksi = jumlah_transaksi;
    }

    public String getTotal_transaksi() {
        return total_transaksi;
    }

    public void setTotal_transaksi(String total_transaksi) {
        this.total_transaksi = total_transaksi;
    }

    public String getJumlah_transaksi() {
        return jumlah_transaksi;
    }

    public void setJumlah_transaksi(String jumlah_transaksi) {
        this.jumlah_transaksi = jumlah_transaksi;
    }
}
