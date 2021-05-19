package com.p3lb.cafex.model.refund;

import com.google.gson.annotations.SerializedName;

public class Refund {
    @SerializedName("id_transaksi")
    private String idTransaksi;
    @SerializedName("nama_pembeli")
    private String namaPembeli;
    @SerializedName("tanggal")
    private String tanggal;
    @SerializedName("total_bayar")
    private String totalBayar;
    @SerializedName("nama_diskon")
    private String namaDiskon;
    @SerializedName("jumlah_item")
    private String jumlahItem;
    @SerializedName("nama_produk")
    private String namaProduk;

    public Refund(String idTransaksi, String namaPembeli, String tanggal, String totalBayar, String namaDiskon, String jumlahItem, String namaProduk) {
        this.idTransaksi = idTransaksi;
        this.namaPembeli = namaPembeli;
        this.tanggal = tanggal;
        this.totalBayar = totalBayar;
        this.namaDiskon = namaDiskon;
        this.jumlahItem = jumlahItem;
        this.namaProduk = namaProduk;
    }

    public String getIdTransaksi() {
        return idTransaksi;
    }

    public void setIdTransaksi(String idTransaksi) {
        this.idTransaksi = idTransaksi;
    }

    public String getNamaPembeli() {
        return namaPembeli;
    }

    public void setNamaPembeli(String namaPembeli) {
        this.namaPembeli = namaPembeli;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getTotalBayar() {
        return totalBayar;
    }

    public void setTotalBayar(String totalBayar) {
        this.totalBayar = totalBayar;
    }

    public String getNamaDiskon() {
        return namaDiskon;
    }

    public void setNamaDiskon(String namaDiskon) {
        this.namaDiskon = namaDiskon;
    }

    public String getJumlahItem() {
        return jumlahItem;
    }

    public void setJumlahItem(String jumlahItem) {
        this.jumlahItem = jumlahItem;
    }

    public String getNamaProduk() {
        return namaProduk;
    }

    public void setNamaProduk(String namaProduk) {
        this.namaProduk = namaProduk;
    }
}
