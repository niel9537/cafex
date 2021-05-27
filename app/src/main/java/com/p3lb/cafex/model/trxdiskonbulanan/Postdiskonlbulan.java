package com.p3lb.cafex.model.trxdiskonbulanan;

import com.google.gson.annotations.SerializedName;
import com.p3lb.cafex.model.trxbulanan.Normalbulan;

import java.util.List;

public class Postdiskonlbulan {
    @SerializedName("status")
    String status;
    @SerializedName("result")
    List<Diskonbulan> diskonbulanList;
    @SerializedName("message")
    String message;

    public Postdiskonlbulan(String status, List<Diskonbulan> diskonbulanList, String message) {
        this.status = status;
        this.diskonbulanList = diskonbulanList;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Diskonbulan> getDiskonbulanList() {
        return diskonbulanList;
    }

    public void setDiskonbulanList(List<Diskonbulan> diskonbulanList) {
        this.diskonbulanList = diskonbulanList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
