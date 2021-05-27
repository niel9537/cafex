package com.p3lb.cafex.model.trxdiskonbulan;

import com.google.gson.annotations.SerializedName;
import com.p3lb.cafex.model.trxnormalbulan.Trxnormalbulan;

import java.util.List;

public class Posttrxdiskonbulan {
    @SerializedName("status")
    String status;
    @SerializedName("result")
    List<Trxdiskonbulan> trxdiskonbulanList;
    @SerializedName("message")
    String message;

    public Posttrxdiskonbulan(String status, List<Trxdiskonbulan> trxdiskonbulanList, String message) {
        this.status = status;
        this.trxdiskonbulanList = trxdiskonbulanList;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Trxdiskonbulan> getTrxdiskonbulanList() {
        return trxdiskonbulanList;
    }

    public void setTrxdiskonbulanList(List<Trxdiskonbulan> trxdiskonbulanList) {
        this.trxdiskonbulanList = trxdiskonbulanList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
