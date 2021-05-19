package com.p3lb.cafex.model.cabang;

import com.google.gson.annotations.SerializedName;

public class Cabang {
    @SerializedName("id_cabang")
    private String id_cabang;
    @SerializedName("nama_cabang")
    private String nama_cabang;
    @SerializedName("notelp_cabang")
    private String notelp_cabang;
    @SerializedName("alamat_cabang")
    private String alamat_cabang;
    @SerializedName("tanggal_cabang")
    private String tanggal_cabang;
    @SerializedName("update_cabang")
    private String update_cabang;

    public Cabang(String id_cabang, String nama_cabang, String notelp_cabang, String alamat_cabang, String tanggal_cabang, String update_cabang) {
        this.id_cabang = id_cabang;
        this.nama_cabang = nama_cabang;
        this.notelp_cabang = notelp_cabang;
        this.alamat_cabang = alamat_cabang;
        this.tanggal_cabang = tanggal_cabang;
        this.update_cabang = update_cabang;
    }

    public String getId_cabang() {
        return id_cabang;
    }

    public void setId_cabang(String id_cabang) {
        this.id_cabang = id_cabang;
    }

    public String getNama_cabang() {
        return nama_cabang;
    }

    public void setNama_cabang(String nama_cabang) {
        this.nama_cabang = nama_cabang;
    }

    public String getNotelp_cabang() {
        return notelp_cabang;
    }

    public void setNotelp_cabang(String notelp_cabang) {
        this.notelp_cabang = notelp_cabang;
    }

    public String getAlamat_cabang() {
        return alamat_cabang;
    }

    public void setAlamat_cabang(String alamat_cabang) {
        this.alamat_cabang = alamat_cabang;
    }

    public String getTanggal_cabang() {
        return tanggal_cabang;
    }

    public void setTanggal_cabang(String tanggal_cabang) {
        this.tanggal_cabang = tanggal_cabang;
    }

    public String getUpdate_cabang() {
        return update_cabang;
    }

    public void setUpdate_cabang(String update_cabang) {
        this.update_cabang = update_cabang;
    }
}
