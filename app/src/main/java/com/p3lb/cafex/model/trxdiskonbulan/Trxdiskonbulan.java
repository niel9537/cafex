package com.p3lb.cafex.model.trxdiskonbulan;

import com.google.gson.annotations.SerializedName;

public class Trxdiskonbulan {
    @SerializedName("total_diskon")
    private String total_diskon;
    @SerializedName("jumlah_transaksidiskon")
    private String jumlah_transaksidiskon;

    public Trxdiskonbulan(String total_diskon, String jumlah_transaksidiskon) {
        this.total_diskon = total_diskon;
        this.jumlah_transaksidiskon = jumlah_transaksidiskon;
    }

    public String getTotal_diskon() {
        return total_diskon;
    }

    public void setTotal_diskon(String total_diskon) {
        this.total_diskon = total_diskon;
    }

    public String getJumlah_transaksidiskon() {
        return jumlah_transaksidiskon;
    }

    public void setJumlah_transaksidiskon(String jumlah_transaksidiskon) {
        this.jumlah_transaksidiskon = jumlah_transaksidiskon;
    }
}
