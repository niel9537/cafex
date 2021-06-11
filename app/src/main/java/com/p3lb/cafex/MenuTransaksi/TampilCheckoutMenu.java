package com.p3lb.cafex.MenuTransaksi;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.p3lb.cafex.Config;
import com.p3lb.cafex.MenuAuth.LoginAdmin;
import com.p3lb.cafex.MenuAuth.LoginKasir;
import com.p3lb.cafex.MenuAuth.RegistrasiUser;
import com.p3lb.cafex.MenuProduk.EditDataProduk;
import com.p3lb.cafex.MenuProduk.TampilDataProduk;
import com.p3lb.cafex.PrintoothActivity;
import com.p3lb.cafex.R;
import com.p3lb.cafex.adapter.CartsAdapter;
import com.p3lb.cafex.adapter.MenusAdapter;
import com.p3lb.cafex.model.auth.LoginRegisterUsers;
import com.p3lb.cafex.model.diskon.Diskon;
import com.p3lb.cafex.model.diskon.PostDiskon;
import com.p3lb.cafex.model.produk.GetProducts;
import com.p3lb.cafex.model.produk.PostPutDelProducts;
import com.p3lb.cafex.model.produk.Products;
import com.p3lb.cafex.model.transaksi.Cart;
import com.p3lb.cafex.model.transaksi.GetTransaksi;
import com.p3lb.cafex.model.transaksi.PostPutDelCart;
import com.p3lb.cafex.model.transaksi.PostPutDelTransaksi;
import com.p3lb.cafex.model.transaksi.PostTransaksi;
import com.p3lb.cafex.network.ApiHelper;
import com.p3lb.cafex.network.ApiInterface;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TampilCheckoutMenu extends AppCompatActivity{
    TextView totalBayar, grandTotal, Pesanan, diskoncut, teksdiskon;
    Spinner spinnerDiskon;
    EditText namaPembeli, diskon;
    Button btnBayar, btnDiskon, backcart;
    ApiInterface mApiInterface;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_ID = "id";
    private static final String KEY_TOTALBAYAR = "totalbayardiskon";
    private static final String KEY_BAYAR = "totalbayar";
    private static final String KEY_PESANAN = "pesanan";
    private static final String KEY_DISKON = "diskon";
    private static final String KEY_BIAYAPESANAN = "biayapesanan";

    String totalbayaruser = "";
    String namadiskon ="";
    String pesanan = "";
    String biayapesanan = "";
    String idcabang = "";
    String username = "";
    String id_detail = "";
    String persendiskon = "";
    String minbayar = "";
    String maxdiskon = "";
    String iddiskon = "";
    String hargadiskon="";
    private int totalbyr = 0;
    private int bayarwithdiskon = 0;
    public static TampilCheckoutMenu mi;
    List<Diskon> diskonkuList;
    List<Cart> cartList ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_datacart);
        totalBayar = (TextView) findViewById(R.id.totalBayar);
        Pesanan = (TextView) findViewById(R.id.pesanan);
        namaPembeli = (EditText) findViewById(R.id.namaPembeli);
        btnBayar = (Button) findViewById(R.id.btnBayar);
        teksdiskon = (TextView) findViewById(R.id.teksdiskon);
        diskoncut = (TextView) findViewById(R.id.potongandiskon);
        //diskon = (EditText) findViewById(R.id.diskon);
        btnDiskon = (Button) findViewById(R.id.btnDiskon);
        backcart = (Button) findViewById(R.id.backcart);
        grandTotal = (TextView) findViewById(R.id.grandTotal);
        spinnerDiskon = (Spinner) findViewById(R.id.spinnerDiskon);
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        idcabang = sharedPreferences.getString(KEY_ID,null);
        username = sharedPreferences.getString(KEY_USERNAME,null);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycleView);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        Intent mIntent = getIntent();
        id_detail = mIntent.getStringExtra("id_detailtransaksi");
        mApiInterface = ApiHelper.getClient().create(ApiInterface.class);
        mi=this;
        showcart();
        listdiskon();

        backcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TampilCheckoutMenu.this, TampilDataMenu.class);
                startActivity(intent);
            }
        });
        btnBayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(totalBayar.getText().toString().equals("Rp 0") || totalBayar.getText().toString().equals("0")){
                    Toasty.normal(TampilCheckoutMenu.this, "Keranjang masih kosong", Toast.LENGTH_SHORT).show();
                    return;
                }else{
//                    if (namaPembeli.getText().toString().isEmpty()) {
//                        Toasty.error(TampilCheckoutMenu.this, "Masukkan nama pembeli", Toast.LENGTH_SHORT).show();
//                        return;
//                    }else{
                        //Toasty.normal(TampilCheckoutMenu.this, "Sukses", Toast.LENGTH_SHORT).show();
//                        if(diskon.getText().toString().isEmpty()){
//                        if(diskon.getText().toString().isEmpty() || grandTotal.getText().toString().equals("0")){
                        if(grandTotal.getText().toString().equals("0")|| diskoncut.getText().toString().equals("")){
                            bayar();
                        }else{
                            diskonbayar();
                        }

                    //}
                }

            }
        });

        btnDiskon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(totalBayar.getText().toString().equals("0")){
                    Toasty.normal(TampilCheckoutMenu.this, "Keranjang masih kosong", Toast.LENGTH_SHORT).show();
                }else{
                    getdiskonpersen();
                }

            }
        });
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);

    }

    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            //Toasty.success(TampilCheckoutMenu.this, ""+ CartsAdapter.idkuu, Toast.LENGTH_SHORT).show();
            id_detail = CartsAdapter.idkuu;
            deletecart(id_detail);
            mAdapter.notifyDataSetChanged();
        }
    };

    private int totalbayar(List<Cart> cartList) {

        int totalPrice = 0;
        for (int i = 0; i < cartList.size(); i++) {
            int harga = Integer.parseInt(cartList.get(i).getHarga_subtotal());
            totalPrice += harga ;
        }
        return totalPrice;
    }

//    private int diskon(int totalbayar, String minbayar, String persendiskon, String maxdiskon) {
//        int grandtotal = 0;

//    }

    public void getdiskonpersen(){
        ApiInterface mApiInterface = ApiHelper.getClient().create(ApiInterface.class);
        Call<Diskon> call = mApiInterface.getdiskonpersen(spinnerDiskon.getSelectedItem().toString());
        call.enqueue(new Callback<Diskon>() {
            @Override
            public void onResponse(Call<Diskon> call, Response<Diskon> response) {
                if(response.isSuccessful()) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    iddiskon = response.body().getId_diskon();
                    namadiskon = response.body().getNama_diskon();
                    minbayar = response.body().getMin_bayar();
                    Log.d("MIN BAYAR",""+minbayar);
                    maxdiskon = response.body().getMax_diskon();
                    persendiskon = response.body().getPersen_diskon();
                    hargadiskon = response.body().getHarga_diskon();
                    //String ttl = sharedPreferences.getString(KEY_BAYAR,null);
                    String ttl = totalBayar.getText().toString();
                    Log.d("Diskon 1 :", ""+ttl);
                    int ttlbayar = Integer.parseInt(ttl);
                    int mnbayar = Integer.parseInt(minbayar);
                    Log.d("MIN BAYAR",""+mnbayar);
                    double prsndiskon = Double.parseDouble(persendiskon);
                    int mxdiskon = Integer.parseInt(maxdiskon);
                    int hrgdiskon = Integer.parseInt(hargadiskon);
                    int hasil = 0;
                    if(hrgdiskon == 0){
                        Log.d("Diskon 1 :", ""+ttlbayar);
                        if(ttlbayar > mnbayar){
                            Log.d("Diskon 2 :", ""+ttlbayar);
                            int dskn = (int) (ttlbayar*prsndiskon);
                            Log.d("Diskon 3 :", ""+dskn);
                            if(dskn > mxdiskon){
                                teksdiskon.setText("Diskon ");
                                hasil = ttlbayar - mxdiskon;
                                int result = ttlbayar-hasil;
                                diskoncut.setText("- Rp "+result);
                                Log.d("Diskon 5 :", ""+hasil);
                            }else{
                                teksdiskon.setText("Diskon ");
                                hasil = ttlbayar - dskn;
                                int result = ttlbayar-hasil;
                                diskoncut.setText("- Rp "+result);
                                Log.d("Diskon 4 :", ""+hasil);
                            }
                        }else{
                            teksdiskon.setText(" ");
                            int result = ttlbayar-hasil;
                            diskoncut.setText(" "+result);
                            Toasty.normal(TampilCheckoutMenu.this, "Minimun bayar "+mnbayar, Toast.LENGTH_SHORT).show();
                            hasil = ttlbayar;
                        }
                    }else{
                        if(ttlbayar>mnbayar){
                            teksdiskon.setText("Diskon ");
                            hasil = ttlbayar-hrgdiskon;
                            int result = ttlbayar-hasil;
                            diskoncut.setText("- Rp "+result);
                        }else{
                            teksdiskon.setText("");
                            diskoncut.setText("");
                            Toasty.normal(TampilCheckoutMenu.this, "Minimun bayar "+mnbayar, Toast.LENGTH_SHORT).show();
                            hasil = ttlbayar;
                        }

                    }
                    grandTotal.setText("Rp "+hasil);
                    if(hargadiskon.equals("")){
                        int per = (int) (prsndiskon*100);
                        editor.putString(KEY_DISKON,namadiskon+" "+per+"%");
                    }else{
                        int per = hrgdiskon;
                        editor.putString(KEY_DISKON,namadiskon+" Rp "+per+"");
                    }
                    editor.putString(KEY_TOTALBAYAR,String.valueOf(hasil));
                    Log.d("Bayar","Bayar "+hasil);
                    editor.apply();
                   // bayarwithdiskon = diskon(totalbyr,minbayar,maxdiskon,persendiskon);
                    //grandTotal.setText(String.valueOf("Rp "+bayarwithdiskon));
                }
                else {
                    Toasty.error(TampilCheckoutMenu.this, "Diskon tidak tersedia  ").show();
                }
            }

            @Override
            public void onFailure(Call<Diskon> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
                Toasty.error(TampilCheckoutMenu.this, "Gagal memuat diskon  " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void listdiskon() {
        ApiInterface mApiInterface = ApiHelper.getClient().create(ApiInterface.class);
        Call<PostDiskon> call = mApiInterface.listdiskon("1");
        call.enqueue(new Callback<PostDiskon>() {
            @Override
            public void onResponse(Call<PostDiskon> call, Response<PostDiskon>
                    response) {
                    //Storing data di list
                    diskonkuList = response.body().getDiskonList();
                    //Panggil method list
                    showListinSpinner();
            }

            @Override
            public void onFailure(Call<PostDiskon> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
                Toasty.error(TampilCheckoutMenu.this, "Gagal memuat diskon  " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showListinSpinner(){
        //String array to store all the book names
        String[] items = new String[diskonkuList.size()];

        //Traversing through the whole list to get all the names
        for(int i=0; i<diskonkuList.size(); i++){
            //Storing names to string array
            items[i] = diskonkuList.get(i).getNama_diskon();
        }
        //Spinner spinner = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        //setting adapter to spinner
        spinnerDiskon.setAdapter(adapter);
        //Creating an array adapter for list view
    }

    public void showcart() {
        ApiInterface mApiInterface = ApiHelper.getClient().create(ApiInterface.class);
        Call<PostPutDelCart> call = mApiInterface.getCart(idcabang);
        call.enqueue(new Callback<PostPutDelCart>() {
            @Override
            public void onResponse(Call<PostPutDelCart> call, Response<PostPutDelCart>
                    response) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                List<Cart> cartList = response.body().getListDataCart();
                Log.d("Retrofit Get", "Jumlah item keranjang: " +
                        String.valueOf(cartList.size()));
                for(Cart cart : cartList){
                    pesanan += ""+cart.getNama_produk()+"\n";
                    pesanan += "x"+cart.getJumlah_item()+"                   Rp "+cart.getHarga_subtotal()+"\n";
//                    pesanan += ""+cart.getNama_produk()+" x"+cart.getJumlah_item();
//                    pesanan += "    Rp "+cart.getHarga_subtotal()+" \n";
                }
                Log.d("Pesanan2", pesanan);
                editor.putString(KEY_PESANAN, pesanan);
                totalbyr = totalbayar(cartList);
                totalBayar.setText(String.valueOf(totalbyr));
                editor.putString(KEY_BAYAR,String.valueOf(totalbyr));
                Log.d("Bayar2","Bayar "+totalbyr);
                editor.apply();
                mAdapter = new CartsAdapter(cartList);
                new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mRecyclerView);
                mRecyclerView.setAdapter(mAdapter);

            }

            @Override
            public void onFailure(Call<PostPutDelCart> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
                Toasty.error(TampilCheckoutMenu.this, "Gagal memuat keranjang  " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    void bayar(){
        String bayartotal = String.valueOf(totalbyr);
        Call<PostTransaksi> postTransaksiCall;
        if(namaPembeli.getText().toString().isEmpty()){
            postTransaksiCall = mApiInterface.addTransaksi(
                    idcabang,
                    "-",
                    username,
                    bayartotal);
        }else {
            postTransaksiCall = mApiInterface.addTransaksi(
                    idcabang,
                    namaPembeli.getText().toString(),
                    username,
                    bayartotal);
        }
        postTransaksiCall.enqueue(new Callback<PostTransaksi>() {
            @Override
            public void onResponse(Call<PostTransaksi> call, Response<PostTransaksi> response) {
                if(response.isSuccessful()) {
                    Log.d("RETRO", "ON SUCCESS : " + response.message());
                    Toasty.success(getApplicationContext(), "Transaksi berhasil di proses", Toast.LENGTH_SHORT).show();
                    updatecart();
                    Intent intent = new Intent(TampilCheckoutMenu.this, PrintoothActivity.class);
                    startActivity(intent);


                }
                else {
                    Log.d("RETRO", "ON FAIL : " + response.message());
                    Toasty.error(getApplicationContext(), "Transaksi gagal di proses", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<PostTransaksi> call, Throwable t) {
                Log.d("RETRO", "ON FAILURE : " + t.getMessage());
                Toasty.error(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void diskonbayar(){
        String bayarttl = sharedPreferences.getString(KEY_TOTALBAYAR,null);
        Call<PostTransaksi> postTransaksiCall;
        if(namaPembeli.getText().toString().isEmpty()){
            postTransaksiCall = mApiInterface.addTransaksiDiskon(
                    idcabang,
                    iddiskon,
                    "-",
                    username,
                    bayarttl);
        }else {
            postTransaksiCall = mApiInterface.addTransaksiDiskon(
                    idcabang,
                    iddiskon,
                    namaPembeli.getText().toString(),
                    username,
                    bayarttl);
        }

        postTransaksiCall.enqueue(new Callback<PostTransaksi>() {
            @Override
            public void onResponse(Call<PostTransaksi> call, Response<PostTransaksi> response) {
                if(response.isSuccessful()) {
                    Log.d("RETRO", "ON SUCCESS : " + response.message());
                    Toasty.success(getApplicationContext(), "Transaksi berhasil di proses", Toast.LENGTH_SHORT).show();
                    updatecart();
                    Intent intent = new Intent(TampilCheckoutMenu.this, PrintoothActivity.class);
                    startActivity(intent);


                }
                else {
                    Log.d("RETRO", "ON FAIL : " + response.message());
                    Toasty.error(getApplicationContext(), "Transaksi gagal di proses", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<PostTransaksi> call, Throwable t) {
                Log.d("RETRO", "ON FAILURE : " + t.getMessage());
                Toasty.error(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void updatecart(){
        Call<PostTransaksi> postTransaksiCall;
        if(namaPembeli.getText().toString().isEmpty()){
            postTransaksiCall = mApiInterface.updatecart(
                    idcabang,
                    "-",
                    username);
        }else{
            postTransaksiCall = mApiInterface.updatecart(
                    idcabang,
                    namaPembeli.getText().toString(),
                    username);
        }
        postTransaksiCall.enqueue(new Callback<PostTransaksi>() {
            @Override
            public void onResponse(Call<PostTransaksi> call, Response<PostTransaksi> response) {
                if(response.isSuccessful()) {
                    Log.d("RETRO", "ON SUCCESS : " + response.message());
                    Toasty.success(getApplicationContext(), "Update berhasil", Toast.LENGTH_SHORT).show();

                }
                else {
                    Log.d("RETRO", "ON FAIL : " + response.message());
                    Toasty.error(getApplicationContext(), "Update gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PostTransaksi> call, Throwable t) {
                Log.d("RETRO", "ON FAILURE : " + t.getMessage());
                Toasty.success(getApplicationContext(), "gagal di update "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    void deletecart(String iddelete){
        Call<PostPutDelTransaksi> postPutDelTransaksiCall = mApiInterface.deletecart(
                iddelete,
                idcabang,
                username);
        Log.d("deletelog", ""+iddelete);
        postPutDelTransaksiCall.enqueue(new Callback<PostPutDelTransaksi>() {
            @Override
            public void onResponse(Call<PostPutDelTransaksi> call, Response<PostPutDelTransaksi> response) {
                if(response.isSuccessful()) {
                    Log.d("RETRO", "ON SUCCESS : " + response.message());
                    Toasty.success(getApplicationContext(), "Sukses hapus", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(TampilCheckoutMenu.this, TampilCheckoutMenu.class);
                    startActivity(intent);

                }
                else {
                    Log.d("RETRO", "ON FAIL : " + response.message());
                    Toasty.error(getApplicationContext(), "Gagal hapus", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(TampilCheckoutMenu.this, TampilCheckoutMenu.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<PostPutDelTransaksi> call, Throwable t) {
                Log.d("RETRO", "ON FAILURE : " + t.getMessage());
                Toasty.error(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            }
        });
    }


}
