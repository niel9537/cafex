package com.p3lb.cafex.model.trxrefundbulan;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Posttrxrefundbulan {
    @SerializedName("status")
    String status;
    @SerializedName("result")
    List<Trxrefundbulan> trxrefundbulanList;
    @SerializedName("message")
    String message;

    public Posttrxrefundbulan(String status, List<Trxrefundbulan> trxrefundbulanList, String message) {
        this.status = status;
        this.trxrefundbulanList = trxrefundbulanList;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Trxrefundbulan> getTrxrefundbulanList() {
        return trxrefundbulanList;
    }

    public void setTrxrefundbulanList(List<Trxrefundbulan> trxrefundbulanList) {
        this.trxrefundbulanList = trxrefundbulanList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
