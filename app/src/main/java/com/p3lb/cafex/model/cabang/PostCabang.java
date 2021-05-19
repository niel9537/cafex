package com.p3lb.cafex.model.cabang;

import com.google.gson.annotations.SerializedName;
import com.p3lb.cafex.model.diskon.Diskon;

import java.util.List;

public class PostCabang {
    @SerializedName("status")
    String status;
    @SerializedName("cabang")
    List<Cabang> cabangList;
    @SerializedName("message")
    String message;

    public PostCabang(String status, List<Cabang> cabangList, String message) {
        this.status = status;
        this.cabangList = cabangList;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Cabang> getCabangList() {
        return cabangList;
    }

    public void setCabangList(List<Cabang> cabangList) {
        this.cabangList = cabangList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
