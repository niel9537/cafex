package com.p3lb.cafex.model.Struk;

import com.google.gson.annotations.SerializedName;

public class Struk {
    @SerializedName("id_transaksi")
    private String id_transaksi;
    @SerializedName("total_bayar")
    private String total_bayar;
    @SerializedName("nama_pembeli")
    private String nama_pembeli;
    @SerializedName("nama_diskon")
    private String nama_diskon;

    public Struk(String id_transaksi, String total_bayar, String nama_pembeli, String nama_diskon) {
        this.id_transaksi = id_transaksi;
        this.total_bayar = total_bayar;
        this.nama_pembeli = nama_pembeli;
        this.nama_diskon = nama_diskon;
    }

    public String getId_transaksi() {
        return id_transaksi;
    }

    public void setId_transaksi(String id_transaksi) {
        this.id_transaksi = id_transaksi;
    }

    public String getTotal_bayar() {
        return total_bayar;
    }

    public void setTotal_bayar(String total_bayar) {
        this.total_bayar = total_bayar;
    }

    public String getNama_pembeli() {
        return nama_pembeli;
    }

    public void setNama_pembeli(String nama_pembeli) {
        this.nama_pembeli = nama_pembeli;
    }

    public String getNama_diskon() {
        return nama_diskon;
    }

    public void setNama_diskon(String nama_diskon) {
        this.nama_diskon = nama_diskon;
    }
}
