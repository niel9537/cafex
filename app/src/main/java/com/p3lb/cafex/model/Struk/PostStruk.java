package com.p3lb.cafex.model.Struk;

import com.google.gson.annotations.SerializedName;
import com.p3lb.cafex.model.refund.Refund;

import java.util.List;

public class PostStruk {
    @SerializedName("status")
    String status;
    @SerializedName("result")
    List<Struk> strukList;
    @SerializedName("message")
    String message;

    public PostStruk(String status, List<Struk> strukList, String message) {
        this.status = status;
        this.strukList = strukList;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Struk> getStrukList() {
        return strukList;
    }

    public void setStrukList(List<Struk> strukList) {
        this.strukList = strukList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
