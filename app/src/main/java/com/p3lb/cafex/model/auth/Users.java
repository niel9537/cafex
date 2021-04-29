package com.p3lb.cafex.model.auth;

import com.google.gson.annotations.SerializedName;

public class Users {
    @SerializedName("id_user")
    private String id_user;
    @SerializedName("id_cabang")
    private String id_cabang;
    @SerializedName("nama_user")
    private String nama_user;
    @SerializedName("nohp_user")
    private String nohp_user;
    @SerializedName("noktp_user")
    private String noktp_user;
    @SerializedName("email_user")
    private String email_user;
    @SerializedName("password_user")
    private String password_user;
    @SerializedName("jabatan_user")
    private String jabatan_user;
    @SerializedName("status_user")
    private String status_user;

    public Users(String id_user, String id_cabang, String nama_user, String nohp_user, String noktp_user, String email_user, String password_user, String jabatan_user, String status_user) {
        this.id_user = id_user;
        this.id_cabang = id_cabang;
        this.nama_user = nama_user;
        this.nohp_user = nohp_user;
        this.noktp_user = noktp_user;
        this.email_user = email_user;
        this.password_user = password_user;
        this.jabatan_user = jabatan_user;
        this.status_user = status_user;
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

    public String getNohp_user() {
        return nohp_user;
    }

    public void setNohp_user(String nohp_user) {
        this.nohp_user = nohp_user;
    }

    public String getNoktp_user() {
        return noktp_user;
    }

    public void setNoktp_user(String noktp_user) {
        this.noktp_user = noktp_user;
    }

    public String getEmail_user() {
        return email_user;
    }

    public void setEmail_user(String email_user) {
        this.email_user = email_user;
    }

    public String getPassword_user() {
        return password_user;
    }

    public void setPassword_user(String password_user) {
        this.password_user = password_user;
    }

    public String getJabatan_user() {
        return jabatan_user;
    }

    public void setJabatan_user(String jabatan_user) {
        this.jabatan_user = jabatan_user;
    }

    public String getStatus_user() {
        return status_user;
    }

    public void setStatus_user(String status_user) {
        this.status_user = status_user;
    }
}
