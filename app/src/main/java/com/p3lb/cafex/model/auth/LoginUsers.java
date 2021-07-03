package com.p3lb.cafex.model.auth;

import com.google.gson.annotations.SerializedName;

public class LoginUsers {
    @SerializedName("id_user")
    private String id_user;
    @SerializedName("id_cabang")
    private String id_cabang;
    @SerializedName("jabatan_user")
    private String jabatan_user;
    @SerializedName("nama_cabang")
    private String nama_cabang;

    public LoginUsers(String id_user, String id_cabang, String jabatan_user, String nama_cabang) {
        this.id_user = id_user;
        this.id_cabang = id_cabang;
        this.jabatan_user = jabatan_user;
        this.nama_cabang = nama_cabang;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getId_cabang() {
        return id_cabang;
    }

    public void setId_cabang(String id_cabang) {
        this.id_cabang = id_cabang;
    }

    public String getJabatan_user() {
        return jabatan_user;
    }

    public void setJabatan_user(String jabatan_user) {
        this.jabatan_user = jabatan_user;
    }

    public String getNama_cabang() {
        return nama_cabang;
    }

    public void setNama_cabang(String nama_cabang) {
        this.nama_cabang = nama_cabang;
    }
}
