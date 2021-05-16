package com.p3lb.cafex.model.transaksi;

import com.google.gson.annotations.SerializedName;

public class Transaksi {
    @SerializedName("id_transaksi")
    private String id_transaksi;
    @SerializedName("id_cabang")
    private String id_cabang;
    @SerializedName("id_diskon")
    private String id_diskon;
    @SerializedName("nama_pembeli")
    private String nama_pembeli;
    @SerializedName("total_bayar")
    private String total_bayar;
    @SerializedName("nama_user")
    private String nama_user;
    @SerializedName("tanggal")
    private String tanggal;
    @SerializedName("status")
    private String status;

    public Transaksi(String id_transaksi, String id_cabang, String id_diskon, String nama_pembeli, String total_bayar, String nama_user, String tanggal, String status) {
        this.id_transaksi = id_transaksi;
        this.id_cabang = id_cabang;
        this.id_diskon = id_diskon;
        this.nama_pembeli = nama_pembeli;
        this.total_bayar = total_bayar;
        this.nama_user = nama_user;
        this.tanggal = tanggal;
        this.status = status;
    }

    public String getId_diskon() {
        return id_diskon;
    }

    public void setId_diskon(String id_diskon) {
        this.id_diskon = id_diskon;
    }

    public String getId_transaksi() {
        return id_transaksi;
    }

    public void setId_transaksi(String id_transaksi) {
        this.id_transaksi = id_transaksi;
    }

    public String getId_cabang() {
        return id_cabang;
    }

    public void setId_cabang(String id_cabang) {
        this.id_cabang = id_cabang;
    }

    public String getNama_pembeli() {
        return nama_pembeli;
    }

    public void setNama_pembeli(String nama_pembeli) {
        this.nama_pembeli = nama_pembeli;
    }

    public String getTotal_bayar() {
        return total_bayar;
    }

    public void setTotal_bayar(String total_bayar) {
        this.total_bayar = total_bayar;
    }

    public String getNama_user() {
        return nama_user;
    }

    public void setNama_user(String nama_user) {
        this.nama_user = nama_user;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
