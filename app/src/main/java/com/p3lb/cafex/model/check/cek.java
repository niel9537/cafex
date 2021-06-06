package com.p3lb.cafex.model.check;

import com.google.gson.annotations.SerializedName;
import com.p3lb.cafex.model.diskon.Diskon;

import java.util.List;

public class cek {
    @SerializedName("pesan")
    String pesan;

    public cek(String pesan) {
        this.pesan = pesan;
    }

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }
}
