package com.p3lb.cafex.model.inventori;

import com.google.gson.annotations.SerializedName;

public class Inventori {
    @SerializedName("id_inventori")
    private String id_inventori;
    @SerializedName("nama_bahanbaku")
    private String nama_bahanbaku;
    @SerializedName("total_bahanbaku")
    private String total_bahanbaku;

    public Inventori(String id_inventori, String nama_bahanbaku, String total_bahanbaku) {
        this.id_inventori = id_inventori;
        this.nama_bahanbaku = nama_bahanbaku;
        this.total_bahanbaku = total_bahanbaku;
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

    public String getTotal_bahanbaku() {
        return total_bahanbaku;
    }

    public void setTotal_bahanbaku(String total_bahanbaku) {
        this.total_bahanbaku = total_bahanbaku;
    }
}
