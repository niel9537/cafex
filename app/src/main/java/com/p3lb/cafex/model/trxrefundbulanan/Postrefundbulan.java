package com.p3lb.cafex.model.trxrefundbulanan;

import com.google.gson.annotations.SerializedName;
import com.p3lb.cafex.model.trxdiskonbulanan.Diskonbulan;

import java.util.List;

public class Postrefundbulan {
    @SerializedName("status")
    String status;
    @SerializedName("result")
    List<Refundbulan> refundbulanList;
    @SerializedName("message")
    String message;

    public Postrefundbulan(String status, List<Refundbulan> refundbulanList, String message) {
        this.status = status;
        this.refundbulanList = refundbulanList;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Refundbulan> getRefundbulanList() {
        return refundbulanList;
    }

    public void setRefundbulanList(List<Refundbulan> refundbulanList) {
        this.refundbulanList = refundbulanList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
