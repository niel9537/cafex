package com.p3lb.cafex.model.trxhbptahun;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Posttrxhbptahun {
    @SerializedName("status")
    String status;
    @SerializedName("result")
    List<Trxhbptahun> trxhbptahunList;
    @SerializedName("message")
    String message;

    public Posttrxhbptahun(String status, List<Trxhbptahun> trxhbptahunList, String message) {
        this.status = status;
        this.trxhbptahunList = trxhbptahunList;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Trxhbptahun> getTrxhbptahunList() {
        return trxhbptahunList;
    }

    public void setTrxhbptahunList(List<Trxhbptahun> trxhbptahunList) {
        this.trxhbptahunList = trxhbptahunList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
