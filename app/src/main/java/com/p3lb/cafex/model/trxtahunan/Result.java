package com.p3lb.cafex.model.trxtahunan;

import com.google.gson.annotations.SerializedName;

public class Result {
    @SerializedName("id_transaksi")
    private String idTransaksi;
    @SerializedName("bulan")
    private String bulan;
    @SerializedName("total_biayaproduk")
    private String totalBiayaproduk;

    public Result(String idTransaksi, String bulan, String totalBiayaproduk) {
        this.idTransaksi = idTransaksi;
        this.bulan = bulan;
        this.totalBiayaproduk = totalBiayaproduk;
    }

    public String getIdTransaksi() {
        return idTransaksi;
    }

    public void setIdTransaksi(String idTransaksi) {
        this.idTransaksi = idTransaksi;
    }

    public String getBulan() {
        return bulan;
    }

    public void setBulan(String bulan) {
        this.bulan = bulan;
    }

    public String getTotalBiayaproduk() {
        return totalBiayaproduk;
    }

    public void setTotalBiayaproduk(String totalBiayaproduk) {
        this.totalBiayaproduk = totalBiayaproduk;
    }
}
