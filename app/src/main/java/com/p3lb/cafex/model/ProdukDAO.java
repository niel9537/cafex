package com.p3lb.cafex.model;

import com.google.gson.annotations.SerializedName;

public class ProdukDAO {
    @SerializedName("ID_PRODUK")
    private String id_produk;
    @SerializedName("NAMA_PRODUK")
    private String nama_produk;
    @SerializedName("JUMLAH_PRODUK")
    private String jumlah_produk;
    @SerializedName("HARGA_PRODUK")
    private String harga_produk;
    @SerializedName("FOTO_PRODUK")
    private String foto_produk;
    @SerializedName("ID_KATEGORI")
    private String id_kategori;

    public ProdukDAO(String id_produk, String nama_produk, String jumlah_produk, String harga_produk, String foto_produk, String id_kategori) {
        this.id_produk = id_produk;
        this.nama_produk = nama_produk;
        this.jumlah_produk = jumlah_produk;
        this.harga_produk = harga_produk;
        this.foto_produk = foto_produk;
        this.id_kategori = id_kategori;
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

    public String getJumlah_produk() {
        return jumlah_produk;
    }

    public void setJumlah_produk(String jumlah_produk) {
        this.jumlah_produk = jumlah_produk;
    }

    public String getHarga_produk() {
        return harga_produk;
    }

    public void setHarga_produk(String harga_produk) {
        this.harga_produk = harga_produk;
    }

    public String getFoto_produk() {
        return foto_produk;
    }

    public void setFoto_produk(String foto_produk) {
        this.foto_produk = foto_produk;
    }

    public String getId_kategori() {
        return id_kategori;
    }

    public void setId_kategori(String id_kategori) {
        this.id_kategori = id_kategori;
    }
}
