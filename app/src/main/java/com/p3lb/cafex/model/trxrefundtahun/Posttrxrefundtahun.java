package com.p3lb.cafex.model.trxrefundtahun;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Posttrxrefundtahun {
    @SerializedName("status")
    String status;
    @SerializedName("result")
    List<Trxrefundtahun> trxrefundtahunList;
    @SerializedName("message")
    String message;

    public Posttrxrefundtahun(String status, List<Trxrefundtahun> trxrefundtahunList, String message) {
        this.status = status;
        this.trxrefundtahunList = trxrefundtahunList;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Trxrefundtahun> getTrxrefundtahunList() {
        return trxrefundtahunList;
    }

    public void setTrxrefundtahunList(List<Trxrefundtahun> trxrefundtahunList) {
        this.trxrefundtahunList = trxrefundtahunList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
