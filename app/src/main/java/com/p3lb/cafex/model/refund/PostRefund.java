package com.p3lb.cafex.model.refund;

import com.google.gson.annotations.SerializedName;
import com.p3lb.cafex.model.diskon.Diskon;

import java.util.List;

public class PostRefund {
    @SerializedName("status")
    String status;
    @SerializedName("result")
    List<Refund> refundList;
    @SerializedName("message")
    String message;

    public PostRefund(String status, List<Refund> refundList, String message) {
        this.status = status;
        this.refundList = refundList;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Refund> getRefundList() {
        return refundList;
    }

    public void setRefundList(List<Refund> refundList) {
        this.refundList = refundList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
