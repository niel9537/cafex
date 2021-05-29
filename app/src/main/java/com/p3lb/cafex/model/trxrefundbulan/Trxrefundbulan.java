package com.p3lb.cafex.model.trxrefundbulan;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class Trxrefundbulan {
    @SerializedName("total_refund")
    @Nullable
    private String total_refund;
    @SerializedName("jumlah_transaksirefund")
    @Nullable
    private String jumlah_transaksirefund;

    public Trxrefundbulan(@Nullable String total_refund, @Nullable String jumlah_transaksirefund) {
        this.total_refund = total_refund;
        this.jumlah_transaksirefund = jumlah_transaksirefund;
    }

    @Nullable
    public String getTotal_refund() {
        return total_refund;
    }

    public void setTotal_refund(@Nullable String total_refund) {
        this.total_refund = total_refund;
    }

    @Nullable
    public String getJumlah_transaksirefund() {
        return jumlah_transaksirefund;
    }

    public void setJumlah_transaksirefund(@Nullable String jumlah_transaksirefund) {
        this.jumlah_transaksirefund = jumlah_transaksirefund;
    }
}
