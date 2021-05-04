package com.p3lb.cafex.network;

import com.p3lb.cafex.model.auth.GetUsers;
import com.p3lb.cafex.model.auth.LoginRegisterUsers;
import com.p3lb.cafex.model.produk.GetProducts;
import com.p3lb.cafex.model.produk.PostPutDelProducts;
import com.p3lb.cafex.model.transaksi.PostPutDelCart;
import com.p3lb.cafex.model.transaksi.PostPutDelTransaksi;
import com.p3lb.cafex.model.transaksi.PostTransaksi;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {
    //===========================Produk==================================//
    @GET("products")
    Call<GetProducts> getProducts();
    @Multipart
    @POST("products")
    Call<PostPutDelProducts> postProducts(@Part MultipartBody.Part foto_produk,
                                          @Part("nama_produk") RequestBody nama_produk,
                                          @Part("harga_produk") RequestBody harga_produk,
                                          @Part("kategori_produk") RequestBody kategori_produk,
                                          @Part("tanggal_produk") RequestBody tanggal_produk,
                                          @Part("flag") RequestBody flag);
    @Multipart
    @POST("products")
    Call<PostPutDelProducts> postUpdateProducts(@Part MultipartBody.Part foto_produk,
                                                @Part("id_produk") RequestBody id_produk,
                                                @Part("nama_produk") RequestBody nama_produk,
                                                @Part("harga_produk") RequestBody harga_produk,
                                                @Part("kategori_produk") RequestBody kategori_produk,
                                                @Part("tanggal_produk") RequestBody tanggal_produk,
                                                @Part("flag") RequestBody flag);
    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "products", hasBody = true)
    Call<PostPutDelProducts> deleteProducts(@Field("id_produk") String id_produk);

    //===========================Users==================================//
    @GET("users")
    Call<GetUsers> getUsers();


    @POST("users/login")
    @FormUrlEncoded
    Call<LoginRegisterUsers> loginUsers(@Field("id_cabang") String id_cabang,
                                        @Field("nama_user") String nama_user,
                                        @Field("password_user") String password_user,
                                        @Field("jabatan_user") int jabatan_user);
    @POST("users/register")
    @FormUrlEncoded
    Call<LoginRegisterUsers> regisUser(@Field("id_cabang") String id_cabang,
                                       @Field("nama_user") String nama_user,
                                       @Field("nohp_user") String nohp_user,
                                       @Field("noktp_user") String noktp_user,
                                       @Field("email_user") String email_user,
                                       @Field("password_user") String password_user,
                                       @Field("jabatan_user") int jabatan_user);
    //===========================Transaksi==================================//

    @POST("detailtransaksi/addcart")
    @FormUrlEncoded
    Call<PostPutDelTransaksi> addcart(@Field("id_produk") String id_produk,
                                      @Field("id_cabang") String id_cabang,
                                      @Field("nama_user") String nama_user,
                                      @Field("jumlah_item") String jumlah_item,
                                      @Field("harga_subtotal") String harga_subtotal,
                                      @Field("nama_pembeli") String nama_pembeli);

    @POST("detailtransaksi")
    @FormUrlEncoded
    Call<PostPutDelCart> getCart(@Field("id_cabang") String id_cabang);

    @POST("transaksi/bayar")
    @FormUrlEncoded
    Call<PostTransaksi> addTransaksi( @Field("id_cabang") String id_cabang,
                                      @Field("nama_pembeli") String nama_pembeli,
                                      @Field("nama_user") String nama_user,
                                      @Field("total_bayar") String total_bayar);


}
