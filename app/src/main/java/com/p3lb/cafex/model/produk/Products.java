package com.p3lb.cafex.model.produk;

import com.google.gson.annotations.SerializedName;

public class Products {
    @SerializedName("id_produk")
    private String id_produk;
    @SerializedName("nama_produk")
    private String nama_produk;
    @SerializedName("harga_produk")
    private String harga_produk;
    @SerializedName("jumlah_produk")
    private String jumlah_produk;
    @SerializedName("tanggal_produk")
    private String tanggal_produk;
    @SerializedName("kategori_produk")
    private String kategori_produk;
    @SerializedName("foto_produk")
    private String foto_produk;

    public Products(String id_produk, String nama_produk, String harga_produk, String jumlah_produk, String tanggal_produk, String kategori_produk, String foto_produk) {
        this.id_produk = id_produk;
        this.nama_produk = nama_produk;
        this.harga_produk = harga_produk;
        this.jumlah_produk = jumlah_produk;
        this.tanggal_produk = tanggal_produk;
        this.kategori_produk = kategori_produk;
        this.foto_produk = foto_produk;
    }

    public String getId_produk() {
        return id_produk;
    }

    public void setId_produk(String id_produk) {
        this.id_produk = id_produk;
    }

    public String getNama_produk() {
        return nama_produk;
    }

    public void setNama_produk(String nama_produk) {
        this.nama_produk = nama_produk;
    }

    public String getHarga_produk() {
        return harga_produk;
    }

    public void setHarga_produk(String harga_produk) {
        this.harga_produk = harga_produk;
    }

    public String getJumlah_produk() {
        return jumlah_produk;
    }

    public void setJumlah_produk(String jumlah_produk) {
        this.jumlah_produk = jumlah_produk;
    }

    public String getTanggal_produk() {
        return tanggal_produk;
    }

    public void setTanggal_produk(String tanggal_produk) {
        this.tanggal_produk = tanggal_produk;
    }

    public String getKategori_produk() {
        return kategori_produk;
    }

    public void setKategori_produk(String kategori_produk) {
        this.kategori_produk = kategori_produk;
    }

    public String getFoto_produk() {
        return foto_produk;
    }

    public void setFoto_produk(String foto_produk) {
        this.foto_produk = foto_produk;
    }
}
