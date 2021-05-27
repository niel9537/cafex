package com.p3lb.cafex.model.trxhbpbulan;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Posttrxhbpbulan {
    @SerializedName("status")
    String status;
    @SerializedName("result")
    List<Trxhbpbulan> trxhbpbulanList;
    @SerializedName("message")
    String message;

    public Posttrxhbpbulan(String status, List<Trxhbpbulan> trxhbpbulanList, String message) {
        this.status = status;
        this.trxhbpbulanList = trxhbpbulanList;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Trxhbpbulan> getTrxhbpbulanList() {
        return trxhbpbulanList;
    }

    public void setTrxhbpbulanList(List<Trxhbpbulan> trxhbpbulanList) {
        this.trxhbpbulanList = trxhbpbulanList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
