package com.p3lb.cafex.model.bahanbaku;

import com.google.gson.annotations.SerializedName;

public class Bahanbaku {
    @SerializedName("id_detailinventori")
    private String idDetailinventori;
    @SerializedName("jumlah_bahanbaku")
    private String jumlahBahanbaku;
    @SerializedName("harga_bahanbaku")
    private String hargaBahanbaku;
    @SerializedName("exp_bahanbaku")
    private String expBahanbaku;
    @SerializedName("tanggal_masuk")
    private String tanggalMasuk;
    @SerializedName("tanggal_keluar")
    private String tanggalKeluar;

    public Bahanbaku(String idDetailinventori, String jumlahBahanbaku, String hargaBahanbaku, String expBahanbaku, String tanggalMasuk, String tanggalKeluar) {
        this.idDetailinventori = idDetailinventori;
        this.jumlahBahanbaku = jumlahBahanbaku;
        this.hargaBahanbaku = hargaBahanbaku;
        this.expBahanbaku = expBahanbaku;
        this.tanggalMasuk = tanggalMasuk;
        this.tanggalKeluar = tanggalKeluar;
    }

    public String getIdDetailinventori() {
        return idDetailinventori;
    }

    public void setIdDetailinventori(String idDetailinventori) {
        this.idDetailinventori = idDetailinventori;
    }

    public String getJumlahBahanbaku() {
        return jumlahBahanbaku;
    }

    public void setJumlahBahanbaku(String jumlahBahanbaku) {
        this.jumlahBahanbaku = jumlahBahanbaku;
    }

    public String getHargaBahanbaku() {
        return hargaBahanbaku;
    }

    public void setHargaBahanbaku(String hargaBahanbaku) {
        this.hargaBahanbaku = hargaBahanbaku;
    }

    public String getExpBahanbaku() {
        return expBahanbaku;
    }

    public void setExpBahanbaku(String expBahanbaku) {
        this.expBahanbaku = expBahanbaku;
    }

    public String getTanggalMasuk() {
        return tanggalMasuk;
    }

    public void setTanggalMasuk(String tanggalMasuk) {
        this.tanggalMasuk = tanggalMasuk;
    }

    public String getTanggalKeluar() {
        return tanggalKeluar;
    }

    public void setTanggalKeluar(String tanggalKeluar) {
        this.tanggalKeluar = tanggalKeluar;
    }
}
