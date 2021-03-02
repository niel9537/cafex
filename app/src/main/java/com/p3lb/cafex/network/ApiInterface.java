package com.p3lb.cafex.network;

import com.p3lb.cafex.model.ProdukDAO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {
    //===========================Produk==================================//
    @GET("produk")
    Call<List<ProdukDAO>> getProdukDAO();


}
