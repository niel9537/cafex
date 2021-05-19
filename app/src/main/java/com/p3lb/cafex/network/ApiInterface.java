package com.p3lb.cafex.network;

import com.p3lb.cafex.model.User.PostUser;
import com.p3lb.cafex.model.auth.ConfirmDeleteUsers;
import com.p3lb.cafex.model.auth.GetUsers;
import com.p3lb.cafex.model.auth.LoginRegisterUsers;
import com.p3lb.cafex.model.auth.Users;
import com.p3lb.cafex.model.cabang.Cabang;
import com.p3lb.cafex.model.cabang.PostCabang;
import com.p3lb.cafex.model.diskon.Diskon;
import com.p3lb.cafex.model.diskon.PostDiskon;
import com.p3lb.cafex.model.produk.GetProducts;
import com.p3lb.cafex.model.produk.PostPutDelProducts;
import com.p3lb.cafex.model.refund.PostRefund;
import com.p3lb.cafex.model.refund.Refund;
import com.p3lb.cafex.model.transaksi.PostPutDelCart;
import com.p3lb.cafex.model.transaksi.PostPutDelTransaksi;
import com.p3lb.cafex.model.transaksi.PostTransaksi;

import java.util.List;

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
                                          @Part("biaya_produk") RequestBody biaya_produk,
                                          @Part("kategori_produk") RequestBody kategori_produk,
                                          @Part("tanggal_produk") RequestBody tanggal_produk,
                                          @Part("flag") RequestBody flag);
    @Multipart
    @POST("products")
    Call<PostPutDelProducts> postUpdateProducts(@Part MultipartBody.Part foto_produk,
                                                @Part("id_produk") RequestBody id_produk,
                                                @Part("nama_produk") RequestBody nama_produk,
                                                @Part("harga_produk") RequestBody harga_produk,
                                                @Part("biaya_produk") RequestBody biaya_produk,
                                                @Part("kategori_produk") RequestBody kategori_produk,
                                                @Part("tanggal_produk") RequestBody tanggal_produk,
                                                @Part("flag") RequestBody flag);
    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "products", hasBody = true)
    Call<PostPutDelProducts> deleteProducts(@Field("id_produk") String id_produk);

    //===========================Users==================================//
    @GET("users")
    Call<GetUsers> getUsers();

    @POST("users/tampilregistrasi")
    @FormUrlEncoded
    Call<LoginRegisterUsers> tampilregistrasiuser(@Field("id_cabang") String id_cabang);

    @POST("users/getidcabang")
    @FormUrlEncoded
    Call<LoginRegisterUsers> cekidcabang(@Field("id_cabang") String id_cabang);

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

    @POST("users/updateregistrasi")
    @FormUrlEncoded
    Call<ConfirmDeleteUsers> konfirmasiuser(@Field("id_user") String id_user,
                                            @Field("id_cabang") String id_cabang,
                                            @Field("nama_user") String nama_user);

    @POST("users/deleteregistrasi")
    @FormUrlEncoded
    Call<ConfirmDeleteUsers> deletekonfirmasiuser(@Field("id_user") String id_user,
                                            @Field("id_cabang") String id_cabang,
                                            @Field("nama_user") String nama_user);

    @POST("users/tampiluser")
    @FormUrlEncoded
    Call<PostUser> showuser(@Field("id_cabang") String id_cabang);

    @POST("users/updateuser")
    @FormUrlEncoded
    Call<PostUser> updateuser(@Field("id_user") String id_user,
                              @Field("nama_user") String nama_user,
                              @Field("nohp_user") String nohp_user,
                              @Field("noktp_user") String noktp_user,
                              @Field("email_user") String email_user);

    @POST("users/deleteuser")
    @FormUrlEncoded
    Call<PostUser> deleteuser(@Field("id_user") String id_user,
                              @Field("nama_user") String nama_user);

    //===========================Transaksi==================================//
    @POST("transaksi/carirefund")
    @FormUrlEncoded
    Call<PostRefund> getrefund(@Field("id_cabang") String id_cabang,
                               @Field("id_transaksi") String id_transaksi);
    @POST("transaksi/updaterefund")
    @FormUrlEncoded
    Call<PostRefund> updaterefund(@Field("id_cabang") String id_cabang,
                               @Field("id_transaksi") String id_transaksi);
    @POST("detailtransaksi/addcart")
    @FormUrlEncoded
    Call<PostPutDelTransaksi> addcart(@Field("id_produk") String id_produk,
                                      @Field("id_cabang") String id_cabang,
                                      @Field("nama_user") String nama_user,
                                      @Field("jumlah_item") String jumlah_item,
                                      @Field("harga_subtotal") String harga_subtotal,
                                      @Field("nama_pembeli") String nama_pembeli);
    @POST("detailtransaksi/deletecart")
    @FormUrlEncoded
    Call<PostPutDelTransaksi> deletecart(@Field("id_detailtransaksi") String id_detailtransaksi,
                                        @Field("id_cabang") String id_cabang,
                                        @Field("nama_user") String nama_user);

    @POST("detailtransaksi")
    @FormUrlEncoded
    Call<PostPutDelCart> getCart(@Field("id_cabang") String id_cabang);

    @POST("transaksi/bayar")
    @FormUrlEncoded
    Call<PostTransaksi> addTransaksi(@Field("id_cabang") String id_cabang,
                                      @Field("nama_pembeli") String nama_pembeli,
                                      @Field("nama_user") String nama_user,
                                      @Field("total_bayar") String total_bayar);

    @POST("transaksi/diskonbayar")
    @FormUrlEncoded
    Call<PostTransaksi> addTransaksiDiskon(@Field("id_cabang") String id_cabang,
                                           @Field("id_diskon") String id_diskon,
                                           @Field("nama_pembeli") String nama_pembeli,
                                           @Field("nama_user") String nama_user,
                                           @Field("total_bayar") String total_bayar);

    @POST("transaksi/updatecart")
    @FormUrlEncoded
    Call<PostTransaksi> updatecart(@Field("id_cabang") String id_cabang,
                                      @Field("nama_pembeli") String nama_pembeli,
                                      @Field("nama_user") String nama_user);
    //===========================Diskon==================================//
    @POST("diskon/getdiskonpersen")
    @FormUrlEncoded
    Call<Diskon> getdiskonpersen(@Field("nama_diskon") String nama_diskon);

    @POST("diskon/adddiskon")
    @FormUrlEncoded
    Call<Diskon> adddiskon(@Field("nama_diskon") String nama_diskon,
                           @Field("min_bayar") String min_bayar,
                           @Field("persen_diskon") String persen_diskon,
                           @Field("harga_diskon") String harga_diskon,
                           @Field("max_diskon") String max_diskon,
                           @Field("exp_diskon") String exp_diskon);

    @GET("diskon")
    Call<PostDiskon> getdiskon();

    @POST("diskon/updatediskon")
    @FormUrlEncoded
    Call<Diskon> updatediskon(@Field("id_diskon") String id_diskon,
                                  @Field("nama_diskon") String nama_diskon,
                                  @Field("min_bayar") String min_bayar,
                                  @Field("persen_diskon") String persen_diskon,
                                  @Field("harga_diskon") String harga_diskon,
                                  @Field("max_diskon") String max_diskon,
                                  @Field("exp_diskon") String exp_diskon);

    @POST("diskon/deletediskon")
    @FormUrlEncoded
    Call<Diskon> deletediskon(@Field("id_diskon") String id_diskon);

    //==========================Cabang======================================//
    @POST("cabang/addcabang")
    @FormUrlEncoded
    Call<Cabang> addcabang(@Field("nama_cabang") String nama_cabang,
                           @Field("notelp_cabang") String notelp_cabang,
                           @Field("alamat_cabang") String alamat_cabang);

    @GET("cabang")
    Call<PostCabang> getcabang();

    @POST("cabang/updatecabang")
    @FormUrlEncoded
    Call<Cabang> updatecabang(@Field("id_cabang") String id_cabang,
                              @Field("nama_cabang") String nama_cabang,
                              @Field("notelp_cabang") String notelp_cabang,
                              @Field("alamat_cabang") String alamat_cabang);

    @POST("cabang/deletecabang")
    @FormUrlEncoded
    Call<Cabang> deletecabang(@Field("id_cabang") String id_cabang);

    @POST("cabang/registerowner")
    @FormUrlEncoded
    Call<Users> registerownercabang(@Field("nama_user") String nama_user);


}
