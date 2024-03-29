package com.p3lb.cafex.network;

import com.p3lb.cafex.model.Struk.PostStruk;
import com.p3lb.cafex.model.auth.Login;
import com.p3lb.cafex.model.auth.LoginUsers;
import com.p3lb.cafex.model.bahanbaku.PostBahanbaku;
import com.p3lb.cafex.model.check.cek;
import com.p3lb.cafex.model.inventori.PostInventori;
import com.p3lb.cafex.model.listcabang.GetCabang;
import com.p3lb.cafex.model.trxbulanan.Getnormalbulan;
import com.p3lb.cafex.model.trxbulanan.History;
import com.p3lb.cafex.model.trxbulanan.Postnormalbulan;
import com.p3lb.cafex.model.trxdiskonbulan.Posttrxdiskonbulan;
import com.p3lb.cafex.model.trxdiskonbulanan.Postdiskonlbulan;
import com.p3lb.cafex.model.trxdiskontahun.Posttrxdiskontahun;
import com.p3lb.cafex.model.trxhbpbulan.Posttrxhbpbulan;
import com.p3lb.cafex.model.trxhbptahun.Posttrxhbptahun;
import com.p3lb.cafex.model.trxnormalbulan.Posttrxnormalbulan;
import com.p3lb.cafex.model.trxnormaltahun.Posttrxnormaltahun;
import com.p3lb.cafex.model.trxrefundbulan.Posttrxrefundbulan;
import com.p3lb.cafex.model.trxrefundbulanan.Postrefundbulan;
import com.p3lb.cafex.model.trxrefundtahun.Posttrxrefundtahun;
import com.p3lb.cafex.model.trxtahunan.Gettrxtahunan;
import com.p3lb.cafex.model.user.PostUser;
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

    @POST("products/searchproduk")
    @FormUrlEncoded
    Call<GetProducts> searchproduk(@Field("nama_produk") String nama_produk);

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
    //@HTTP(method = "DELETE", path = "products", hasBody = true)
    @POST("products/hapusproduk")
    Call<PostPutDelProducts> deleteProducts(@Field("id_produk") String id_produk);

    //===========================Users==================================//
    @POST("users/listcabang")
    @FormUrlEncoded
    Call<PostCabang> daftarcabang(@Field("status") String status);

    @GET("users")
    Call<GetUsers> getUsers();

    @POST("users/getusername")
    @FormUrlEncoded
    Call<LoginRegisterUsers> getusername(@Field("nama_user") String nama_user);

    @POST("users/searchuser")
    @FormUrlEncoded
    Call<PostUser> searchuser(@Field("nama_user") String nama_user,
                                @Field("id_cabang") String id_cabang);

    @POST("users/tampilregistrasi")
    @FormUrlEncoded
    Call<LoginRegisterUsers> tampilregistrasiuser(@Field("id_cabang") String id_cabang);

    @POST("users/getidcabang")
    @FormUrlEncoded
    Call<LoginRegisterUsers> cekidcabang(@Field("id_cabang") String id_cabang);

    @POST("users/forgotpass")
    @FormUrlEncoded
    Call<LoginRegisterUsers> forgotpass(@Field("nama_user") String nama_user);
    @POST("users/resetpassword")
    @FormUrlEncoded
    Call<LoginRegisterUsers> resetpass(@Field("nama_user") String nama_user,
                                       @Field("id_cabang") String id_cabang,
                                       @Field("password_user") String password_user);

    @POST("users/verifikasikode")
    @FormUrlEncoded
    Call<LoginRegisterUsers> verifikasikode(@Field("nama_user") String nama_user,
                                            @Field("id_cabang") String id_cabang,
                                            @Field("password_user") String password_user);

    @POST("users/login")
    @FormUrlEncoded
    Call<Login> loginUsers(@Field("nama_user") String nama_user,
                           @Field("password_user") String password_user);


    @POST("users/register")
    @FormUrlEncoded
    Call<LoginRegisterUsers> regisUser(@Field("nama_cabang") String nama_cabang,
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

    @POST("users/profileuser")
    @FormUrlEncoded
    Call<PostUser> profileuser(@Field("id_cabang") String id_cabang,
                               @Field("nama_user") String nama_user);

    @POST("users/updateprofile")
    @FormUrlEncoded
    Call<LoginRegisterUsers> updateprofile(@Field("id_user") String id_user,
                                           @Field("id_cabang") String id_cabang,
                                           @Field("nama_user") String nama_user,
                                           @Field("nohp_user") String nohp_user,
                                           @Field("noktp_user") String noktp_user,
                                           @Field("email_user") String email_user,
                                           @Field("jabatan_user") String jabatan_user);

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
    @POST("transaksi/struk")
    @FormUrlEncoded
    Call<PostStruk> getstruk(@Field("id_cabang") String id_cabang,
                              @Field("nama_user") String nama_user);

    @POST("transaksi/carirefund")
    @FormUrlEncoded
    Call<PostRefund> getrefund(@Field("id_cabang") String id_cabang,
                               @Field("id_transaksi") String id_transaksi);
    @POST("transaksi/caririwayat")
    @FormUrlEncoded
    Call<PostRefund> getriwayat(@Field("id_cabang") String id_cabang,
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

    @POST("transaksi/historitransaksi")
    @FormUrlEncoded
    Call<Getnormalbulan> historitransaksi(@Field("id_cabang") String id_cabang);
    //===========================Diskon==================================//
    @POST("diskon/listdiskon")
    @FormUrlEncoded
    Call<PostDiskon> listdiskon(@Field("status") String status);

    @POST("diskon/getdiskonpersen")
    @FormUrlEncoded
    Call<Diskon> getdiskonpersen(@Field("nama_diskon") String nama_diskon);

    @POST("diskon/searchdiskon")
    @FormUrlEncoded
    Call<PostDiskon> searchdiskon(@Field("nama_diskon") String nama_diskon);

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

    @POST("cabang/listcabang")
    @FormUrlEncoded
    Call<GetCabang> listcabang(@Field("status") String status);

    @POST("cabang/addcabang")
    @FormUrlEncoded
    Call<Cabang> addcabang(@Field("nama_cabang") String nama_cabang,
                           @Field("notelp_cabang") String notelp_cabang,
                           @Field("alamat_cabang") String alamat_cabang);

    @GET("cabang")
    Call<PostCabang> getcabang();

    @POST("cabang/searchcabang")
    @FormUrlEncoded
    Call<PostCabang> searchcabang(@Field("nama_cabang") String nama_cabang);

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

    //==========================Inventori======================================//
    @POST("inventori/tampilinventori")
    @FormUrlEncoded
    Call<PostInventori> getInventori(@Field("id_cabang") String id_cabang);

    @POST("inventori/tambahinventori")
    @FormUrlEncoded
    Call<PostInventori> addbahanbaku(@Field("id_cabang") String id_cabang,
                                     @Field("nama_bahanbaku") String nama_bahanbaku);
    @POST("inventori/deleteinventori")
    @FormUrlEncoded
    Call<PostInventori> deleteinventori(@Field("id_cabang") String id_cabang,
                                        @Field("id_inventori") String id_inventori);
    @POST("inventori/tampilbahanbaku")
    @FormUrlEncoded
    Call<PostBahanbaku> getbahanbaku(@Field("id_inventori") String id_inventori,
                                     @Field("id_cabang") String id_cabang,
                                     @Field("nama_bahanbaku") String nama_bahanbaku);
    @POST("inventori/tambahbahanbaku")
    @FormUrlEncoded
    Call<PostBahanbaku> tambahbahanbaku(@Field("id_inventori") String id_inventori,
                                        @Field("id_cabang") String id_cabang,
                                        @Field("nama_bahanbaku") String nama_bahanbaku,
                                        @Field("jumlah_bahanbaku") String jumlah_bahanbaku,
                                        @Field("harga_bahanbaku") String harga_bahanbaku,
                                        @Field("exp_bahanbaku") String exp_bahanbaku,
                                        @Field("tanggal_keluar") String tanggal_keluar);
    @POST("inventori/getbahanbaku")
    @FormUrlEncoded
    Call<PostBahanbaku> ambilbahanbaku(@Field("id_inventori") String id_inventori,
                                        @Field("id_cabang") String id_cabang,
                                        @Field("nama_bahanbaku") String nama_bahanbaku);

    @POST("inventori/fifobahanbaku")
    @FormUrlEncoded
    Call<PostBahanbaku> updatebahanbaku(@Field("id_cabang") String id_cabang,
                                        @Field("id_inventori") String id_inventori,
                                        @Field("nama_bahanbaku") String nama_bahanbaku,
                                        @Field("jumlah_bahanbaku") String jumlah_bahanbaku);

    //==========================Laporan======================================//
    @POST("laporan/gettransaksibulan")
    @FormUrlEncoded
    Call<Posttrxnormalbulan> gettransaksibulan(@Field("id_cabang") String id_cabang,
                                               @Field("tanggal") String tanggal);

    @POST("laporan/gettransaksidiskonbulan")
    @FormUrlEncoded
    Call<Posttrxdiskonbulan> gettransaksidiskonbulan(@Field("id_cabang") String id_cabang,
                                               @Field("tanggal") String tanggal);

    @POST("laporan/gettransaksirefundbulan")
    @FormUrlEncoded
    Call<Posttrxrefundbulan> gettransaksirefundbulan(@Field("id_cabang") String id_cabang,
                                                     @Field("tanggal") String tanggal);

    @POST("laporan/gettransaksihbpbulan")
    @FormUrlEncoded
    Call<Posttrxhbpbulan> gettransaksihbpbulan(@Field("id_cabang") String id_cabang,
                                               @Field("tanggal") String tanggal);

    @POST("laporan/trxbulanan")
    @FormUrlEncoded
    Call<Postnormalbulan> gettrxbulanan(@Field("id_cabang") String id_cabang,
                                        @Field("tanggal") String tanggal);

    @POST("laporan/trxbulananwithdiskon")
    @FormUrlEncoded
    Call<Postdiskonlbulan> gettrxdiskonbulanan(@Field("id_cabang") String id_cabang,
                                               @Field("tanggal") String tanggal);

    @POST("laporan/trxbulananwithrefund")
    @FormUrlEncoded
    Call<Postrefundbulan> gettrxrefundbulanan(@Field("id_cabang") String id_cabang,
                                              @Field("tanggal") String tanggal);

    @POST("laporan/gettransaksitahun")
    @FormUrlEncoded
    Call<Posttrxnormaltahun> gettransaksitahun(@Field("id_cabang") String id_cabang,
                                               @Field("tanggal") String tanggal);

    @POST("laporan/gettransaksidiskontahun")
    @FormUrlEncoded
    Call<Posttrxdiskontahun> gettransaksidiskontahun(@Field("id_cabang") String id_cabang,
                                                     @Field("tanggal") String tanggal);

    @POST("laporan/gettransaksirefundtahun")
    @FormUrlEncoded
    Call<Posttrxrefundtahun> gettransaksirefundtahun(@Field("id_cabang") String id_cabang,
                                                     @Field("tanggal") String tanggal);

    @POST("laporan/gettransaksihbptahun")
    @FormUrlEncoded
    Call<Posttrxhbptahun> gettransaksihbptahun(@Field("id_cabang") String id_cabang,
                                               @Field("tanggal") String tanggal);

    @POST("laporan/gettransaksiperbulan")
    @FormUrlEncoded
    Call<Gettrxtahunan> gettransaksiperbulan(@Field("id_cabang") String id_cabang,
                                             @Field("tanggal") String tanggal);
    @POST("laporan/cektahun")
    @FormUrlEncoded
    Call<cek> cektahun(@Field("id_cabang") String id_cabang,
                       @Field("tanggal") String tanggal);
    @POST("laporan/cekbulan")
    @FormUrlEncoded
    Call<cek> cekbulan(@Field("id_cabang") String id_cabang,
                       @Field("tanggal") String tanggal);

}
