package com.p3lb.cafex.model.trxtahunan;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Gettrxtahunan {
    @SerializedName("status")
    String status;
    @SerializedName("report")
    List<Report> reportList;
    @SerializedName("result")
    List<Result> resultList;
    @SerializedName("message")
    String message;

    public Gettrxtahunan(String status, List<Report> reportList, List<Result> resultList, String message) {
        this.status = status;
        this.reportList = reportList;
        this.resultList = resultList;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Report> getReportList() {
        return reportList;
    }

    public void setReportList(List<Report> reportList) {
        this.reportList = reportList;
    }

    public List<Result> getResultList() {
        return resultList;
    }

    public void setResultList(List<Result> resultList) {
        this.resultList = resultList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
