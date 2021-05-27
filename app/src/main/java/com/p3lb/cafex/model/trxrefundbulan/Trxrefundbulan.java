package com.p3lb.cafex.model.trxrefundbulan;

import com.google.gson.annotations.SerializedName;

public class Trxrefundbulan {
    @SerializedName("total_refund")
    private String total_refund;
    @SerializedName("jumlah_transaksirefund")
    private String jumlah_transaksirefund;

    public Trxrefundbulan(String total_refund, String jumlah_transaksirefund) {
        this.total_refund = total_refund;
        this.jumlah_transaksirefund = jumlah_transaksirefund;
    }

    public String getTotal_refund() {
        return total_refund;
    }

    public void setTotal_refund(String total_refund) {
        this.total_refund = total_refund;
    }

    public String getJumlah_transaksirefund() {
        return jumlah_transaksirefund;
    }

    public void setJumlah_transaksirefund(String jumlah_transaksirefund) {
        this.jumlah_transaksirefund = jumlah_transaksirefund;
    }
}
