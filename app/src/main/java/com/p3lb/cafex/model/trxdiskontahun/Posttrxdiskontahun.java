package com.p3lb.cafex.model.trxdiskontahun;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Posttrxdiskontahun {
    @SerializedName("status")
    String status;
    @SerializedName("result")
    List<Trxdiskontahun> trxdiskontahunList;
    @SerializedName("message")
    String message;

    public Posttrxdiskontahun(String status, List<Trxdiskontahun> trxdiskontahunList, String message) {
        this.status = status;
        this.trxdiskontahunList = trxdiskontahunList;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Trxdiskontahun> getTrxdiskontahunList() {
        return trxdiskontahunList;
    }

    public void setTrxdiskontahunList(List<Trxdiskontahun> trxdiskontahunList) {
        this.trxdiskontahunList = trxdiskontahunList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
