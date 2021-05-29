package com.p3lb.cafex.model.trxtahunan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Report {
    @SerializedName("total_transaksi")
    private String totalTransaksi;

    public Report(String totalTransaksi) {
        this.totalTransaksi = totalTransaksi;
    }

    public String getTotalTransaksi() {
        return totalTransaksi;
    }

    public void setTotalTransaksi(String totalTransaksi) {
        this.totalTransaksi = totalTransaksi;
    }
}