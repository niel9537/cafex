package com.p3lb.cafex.model.inventori;

import com.google.gson.annotations.SerializedName;

public class Inventori {
    @SerializedName("id_inventori")
    private String id_inventori;
    @SerializedName("nama_bahanbaku")
    private String nama_bahanbaku;
    @SerializedName("tanggal_buat")
    private String tanggal_buat;

    public Inventori(String id_inventori, String nama_bahanbaku, String tanggal_buat) {
        this.id_inventori = id_inventori;
        this.nama_bahanbaku = nama_bahanbaku;
        this.tanggal_buat = tanggal_buat;
    }

    public String getId_inventori() {
        return id_inventori;
    }

    public void setId_inventori(String id_inventori) {
        this.id_inventori = id_inventori;
    }

    public String getNama_bahanbaku() {
        return nama_bahanbaku;
    }

    public void setNama_bahanbaku(String nama_bahanbaku) {
        this.nama_bahanbaku = nama_bahanbaku;
    }

    public String getTanggal_buat() {
        return tanggal_buat;
    }

    public void setTanggal_buat(String tanggal_buat) {
        this.tanggal_buat = tanggal_buat;
    }
}
