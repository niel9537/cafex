package com.p3lb.cafex.model.listcabang;

import com.google.gson.annotations.SerializedName;

public class ListCabang {
    @SerializedName("kode_cabang")
    private String kode_cabang;
    @SerializedName("nama_cabang")
    private String nama_cabang;

    public ListCabang(String kode_cabang, String nama_cabang) {
        this.kode_cabang = kode_cabang;
        this.nama_cabang = nama_cabang;
    }

    public String getKode_cabang() {
        return kode_cabang;
    }

    public void setKode_cabang(String kode_cabang) {
        this.kode_cabang = kode_cabang;
    }

    public String getNama_cabang() {
        return nama_cabang;
    }

    public void setNama_cabang(String nama_cabang) {
        this.nama_cabang = nama_cabang;
    }
}
