package com.p3lb.cafex.model.auth;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ConfirmDeleteUsers {
    @SerializedName("id_user")
    private String id_user;
    @SerializedName("id_cabang")
    private String id_cabang;
    @SerializedName("nama_user")
    private String nama_user;

    public ConfirmDeleteUsers(String id_user, String id_cabang, String nama_user) {
        this.id_user = id_user;
        this.id_cabang = id_cabang;
        this.nama_user = nama_user;
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

    public String getNama_user() {
        return nama_user;
    }

    public void setNama_user(String nama_user) {
        this.nama_user = nama_user;
    }
}
