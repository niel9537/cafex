package com.p3lb.cafex.model.listcabang;

import com.google.gson.annotations.SerializedName;
import com.p3lb.cafex.model.cabang.Cabang;

import java.util.List;

public class GetCabang {
    @SerializedName("status")
    String status;
    @SerializedName("result")
    List<ListCabang> listCabangList;
    @SerializedName("message")
    String message;

    public GetCabang(String status, List<ListCabang> listCabangList, String message) {
        this.status = status;
        this.listCabangList = listCabangList;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ListCabang> getListCabangList() {
        return listCabangList;
    }

    public void setListCabangList(List<ListCabang> listCabangList) {
        this.listCabangList = listCabangList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
