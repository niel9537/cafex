package com.p3lb.cafex.model.diskon;

import com.google.gson.annotations.SerializedName;

public class Diskon {
    @SerializedName("id_diskon")
    private String id_diskon;
    @SerializedName("min_bayar")
    private String min_bayar;
    @SerializedName("persen_diskon")
    private String persen_diskon;
    @SerializedName("harga_diskon")
    private String harga_diskon;
    @SerializedName("max_diskon")
    private String max_diskon;
    @SerializedName("exp_diskon")
    private String exp_diskon;

    public Diskon(String id_diskon, String min_bayar, String persen_diskon, String harga_diskon, String max_diskon, String exp_diskon) {
        this.id_diskon = id_diskon;
        this.min_bayar = min_bayar;
        this.persen_diskon = persen_diskon;
        this.harga_diskon = harga_diskon;
        this.max_diskon = max_diskon;
        this.exp_diskon = exp_diskon;
    }

    public String getId_diskon() {
        return id_diskon;
    }

    public void setId_diskon(String id_diskon) {
        this.id_diskon = id_diskon;
    }

    public String getMin_bayar() {
        return min_bayar;
    }

    public void setMin_bayar(String min_bayar) {
        this.min_bayar = min_bayar;
    }

    public String getPersen_diskon() {
        return persen_diskon;
    }

    public void setPersen_diskon(String persen_diskon) {
        this.persen_diskon = persen_diskon;
    }

    public String getHarga_diskon() {
        return harga_diskon;
    }

    public void setHarga_diskon(String harga_diskon) {
        this.harga_diskon = harga_diskon;
    }

    public String getMax_diskon() {
        return max_diskon;
    }

    public void setMax_diskon(String max_diskon) {
        this.max_diskon = max_diskon;
    }

    public String getExp_diskon() {
        return exp_diskon;
    }

    public void setExp_diskon(String exp_diskon) {
        this.exp_diskon = exp_diskon;
    }
}

