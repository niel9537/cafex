package com.p3lb.cafex.network;

import com.p3lb.cafex.model.ProdukDAO;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {
    //===========================Produk==================================//
    @GET("api/produk")
    Call<List<ProdukDAO>> getProdukDAO();
    @Multipart
    @POST("api/produk")

    Call<ResponseBody> addProdukDAO(@Part("nama_produk") RequestBody nama_produk,
                                    @Part("id_kategori") RequestBody kategori_produk,
                                    @Part("jumlah_produk") RequestBody jumlah_produk,
                                    @Part("harga_produk") RequestBody harga_produk,
                                    @Part MultipartBody.Part foto_produk);


}
