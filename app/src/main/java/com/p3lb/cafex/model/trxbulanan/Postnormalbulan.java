package com.p3lb.cafex.model.trxbulanan;

import com.google.gson.annotations.SerializedName;
import com.p3lb.cafex.model.trxdiskonbulan.Trxdiskonbulan;

import java.util.List;

public class Postnormalbulan {
    @SerializedName("status")
    String status;
    @SerializedName("result")
    List<Normalbulan> normalbulanList;
    @SerializedName("message")
    String message;

    public Postnormalbulan(String status, List<Normalbulan> normalbulanList, String message) {
        this.status = status;
        this.normalbulanList = normalbulanList;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Normalbulan> getNormalbulanList() {
        return normalbulanList;
    }

    public void setNormalbulanList(List<Normalbulan> normalbulanList) {
        this.normalbulanList = normalbulanList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
