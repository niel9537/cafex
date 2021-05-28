package com.p3lb.cafex.model.trxnormaltahun;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Posttrxnormaltahun {
    @SerializedName("status")
    String status;
    @SerializedName("result")
    List<Trxnormaltahun> trxnormaltahunList;
    @SerializedName("message")
    String message;

    public Posttrxnormaltahun(String status, List<Trxnormaltahun> trxnormaltahunList, String message) {
        this.status = status;
        this.trxnormaltahunList = trxnormaltahunList;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Trxnormaltahun> getTrxnormaltahunList() {
        return trxnormaltahunList;
    }

    public void setTrxnormaltahunList(List<Trxnormaltahun> trxnormaltahunList) {
        this.trxnormaltahunList = trxnormaltahunList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
