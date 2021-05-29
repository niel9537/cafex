package com.p3lb.cafex.model.trxbulanan;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Getnormalbulan {
    @SerializedName("status")
    String status;
    @SerializedName("result")
    List<History> historyList;
    @SerializedName("message")
    String message;

    public Getnormalbulan(String status, List<History> historyList, String message) {
        this.status = status;
        this.historyList = historyList;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<History> getHistoryList() {
        return historyList;
    }

    public void setHistoryList(List<History> historyList) {
        this.historyList = historyList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
