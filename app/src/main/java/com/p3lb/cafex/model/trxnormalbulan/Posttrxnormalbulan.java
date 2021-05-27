package com.p3lb.cafex.model.trxnormalbulan;

import com.google.gson.annotations.SerializedName;
import com.p3lb.cafex.model.refund.Refund;

import java.util.List;

public class Posttrxnormalbulan {
    @SerializedName("status")
    String status;
    @SerializedName("result")
    List<Trxnormalbulan> trxnormalbulanList;
    @SerializedName("message")
    String message;

    public Posttrxnormalbulan(String status, List<Trxnormalbulan> trxnormalbulanList, String message) {
        this.status = status;
        this.trxnormalbulanList = trxnormalbulanList;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Trxnormalbulan> getTrxnormalbulanList() {
        return trxnormalbulanList;
    }

    public void setTrxnormalbulanList(List<Trxnormalbulan> trxnormalbulanList) {
        this.trxnormalbulanList = trxnormalbulanList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
