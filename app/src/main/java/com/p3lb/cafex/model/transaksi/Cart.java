package com.p3lb.cafex.model.transaksi;

import com.google.gson.annotations.SerializedName;

public class Cart {
    @SerializedName("foto_produk")
    private String foto_produk;
    @SerializedName("kategori_produk")
    private String kategori_produk;
    @SerializedName("nama_produk")
    private String nama_produk;
    @SerializedName("jumlah_item")
    private String jumlah_item;
    @SerializedName("harga_subtotal")
    private String harga_subtotal;

    public Cart(String foto_produk, String kategori_produk, String nama_produk, String jumlah_item, String harga_subtotal) {
        this.foto_produk = foto_produk;
        this.kategori_produk = kategori_produk;
        this.nama_produk = nama_produk;
        this.jumlah_item = jumlah_item;
        this.harga_subtotal = harga_subtotal;
    }

    public String getFoto_produk() {
        return foto_produk;
    }

    public void setFoto_produk(String foto_produk) {
        this.foto_produk = foto_produk;
    }

    public String getKategori_produk() {
        return kategori_produk;
    }

    public void setKategori_produk(String kategori_produk) {
        this.kategori_produk = kategori_produk;
    }

    public String getNama_produk() {
        return nama_produk;
    }

    public void setNama_produk(String nama_produk) {
        this.nama_produk = nama_produk;
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
}
