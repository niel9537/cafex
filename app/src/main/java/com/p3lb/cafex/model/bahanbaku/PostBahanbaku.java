package com.p3lb.cafex.model.bahanbaku;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostBahanbaku {
    @SerializedName("status")
    String status;
    @SerializedName("result")
    List<Bahanbaku> bahanbakuList;
    @SerializedName("message")
    String message;

    public PostBahanbaku(String status, List<Bahanbaku> bahanbakuList, String message) {
        this.status = status;
        this.bahanbakuList = bahanbakuList;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Bahanbaku> getBahanbakuList() {
        return bahanbakuList;
    }

    public void setBahanbakuList(List<Bahanbaku> bahanbakuList) {
        this.bahanbakuList = bahanbakuList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
