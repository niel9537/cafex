package com.p3lb.cafex.model.transaksi;

import com.google.gson.annotations.SerializedName;

public class DetailTransaksi {
    @SerializedName("id_detailtransaksi")
    private String id_detailtransaksi;
    @SerializedName("id_produk")
    private String id_produk;
    @SerializedName("id_cabang")
    private String id_cabang;
    @SerializedName("nama_user")
    private String nama_user;
    @SerializedName("jumlah_item")
    private String jumlah_item;
    @SerializedName("harga_subtotal")
    private String harga_subtotal;
    @SerializedName("tanggal_buat")
    private String tanggal_buat;
    @SerializedName("nama_pembeli")
    private String nama_pembeli;
    @SerializedName("status")
    private String status;

    public DetailTransaksi(String id_detailtransaksi, String id_produk, String id_cabang, String nama_user, String jumlah_item, String harga_subtotal, String tanggal_buat, String nama_pembeli, String status) {
        this.id_detailtransaksi = id_detailtransaksi;
        this.id_produk = id_produk;
        this.id_cabang = id_cabang;
        this.nama_user = nama_user;
        this.jumlah_item = jumlah_item;
        this.harga_subtotal = harga_subtotal;
        this.tanggal_buat = tanggal_buat;
        this.nama_pembeli = nama_pembeli;
        this.status = status;
    }

    public String getId_detailtransaksi() {
        return id_detailtransaksi;
    }

    public void setId_detailtransaksi(String id_detailtransaksi) {
        this.id_detailtransaksi = id_detailtransaksi;
    }

    public String getId_produk() {
        return id_produk;
    }

    public void setId_produk(String id_produk) {
        this.id_produk = id_produk;
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

    public String getJumlah_item() {
        return jumlah_item;
    }

    public void setJumlah_item(String jumlah_item) {
        this.jumlah_item = jumlah_item;
    }

    public String getHarga_subtotal() {
        return harga_subtotal;
    }

    public void setHarga_subtotal(String harga_subtotal) {
        this.harga_subtotal = harga_subtotal;
    }

    public String getTanggal_buat() {
        return tanggal_buat;
    }

    public void setTanggal_buat(String tanggal_buat) {
        this.tanggal_buat = tanggal_buat;
    }

    public String getNama_pembeli() {
        return nama_pembeli;
    }

    public void setNama_pembeli(String nama_pembeli) {
        this.nama_pembeli = nama_pembeli;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
