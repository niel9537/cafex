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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.p3lb.cafex.Config;
import com.p3lb.cafex.MenuAuth.LoginKasir;
import com.p3lb.cafex.MenuAuth.RegistrasiUser;
import com.p3lb.cafex.MenuProduk.EditDataProduk;
import com.p3lb.cafex.MenuProduk.TampilDataProduk;
import com.p3lb.cafex.R;
import com.p3lb.cafex.adapter.CartsAdapter;
import com.p3lb.cafex.adapter.MenusAdapter;
import com.p3lb.cafex.model.auth.LoginRegisterUsers;
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

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TampilCheckoutMenu extends AppCompatActivity{
    TextView totalBayar;
    EditText namaPembeli;
    Button btnBayar;
    ApiInterface mApiInterface;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_ID = "id";
    String idcabang = "";
    String username = "";
    private int totalbyr = 0;
    public static TampilCheckoutMenu mi;
    List<Cart> cartList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_datacart);
        totalBayar = (TextView) findViewById(R.id.totalBayar);
        namaPembeli = (EditText) findViewById(R.id.namaPembeli);
        btnBayar = (Button) findViewById(R.id.btnBayar);
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        idcabang = sharedPreferences.getString(KEY_ID,null);
        username = sharedPreferences.getString(KEY_USERNAME,null);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycleView);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mApiInterface = ApiHelper.getClient().create(ApiInterface.class);
        mi=this;

        refresh();
        btnBayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bayar();
            }
        });

    }

    private int totalbayar(List<Cart> cartList) {

        int totalPrice = 0;
        for (int i = 0; i < cartList.size(); i++) {
            int harga = Integer.parseInt(cartList.get(i).getHarga_subtotal());
            totalPrice += harga ;
        }
        return totalPrice;
    }

    public void refresh() {
        ApiInterface mApiInterface = ApiHelper.getClient().create(ApiInterface.class);
        Call<PostPutDelCart> call = mApiInterface.getCart(idcabang);
        call.enqueue(new Callback<PostPutDelCart>() {
            @Override
            public void onResponse(Call<PostPutDelCart> call, Response<PostPutDelCart>
                    response) {
                List<Cart> cartList = response.body().getListDataCart();
                Log.d("Retrofit Get", "Jumlah item keranjang: " +
                        String.valueOf(cartList.size()));
                totalbyr = totalbayar(cartList);
                totalBayar.setText(String.valueOf("Rp "+totalbyr));
                mAdapter = new CartsAdapter(cartList);
                mRecyclerView.setAdapter(mAdapter);

            }

            @Override
            public void onFailure(Call<PostPutDelCart> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
                Toast.makeText(TampilCheckoutMenu.this, "Gagal memuat keranjang  " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    void bayar(){
        String bayartotal = String.valueOf(totalbyr);
        Call<PostTransaksi> postTransaksiCall = mApiInterface.addTransaksi(
                idcabang,
                namaPembeli.getText().toString(),
                username,
                bayartotal);

        postTransaksiCall.enqueue(new Callback<PostTransaksi>() {
            @Override
            public void onResponse(Call<PostTransaksi> call, Response<PostTransaksi> response) {
                if(response.isSuccessful()) {
                    Log.d("RETRO", "ON SUCCESS : " + response.message());
                    Toast.makeText(getApplicationContext(), "Transaksi berhasil di proses", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(TampilCheckoutMenu.this, TampilDataMenu.class);
                    startActivity(intent);

                }
                else {
                    Log.d("RETRO", "ON FAIL : " + response.message());
                    Toast.makeText(getApplicationContext(), "Transaksi gagal di proses", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(TampilCheckoutMenu.this, TampilDataMenu.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<PostTransaksi> call, Throwable t) {
                Log.d("RETRO", "ON FAILURE : " + t.getMessage());
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            }
        });
    }
}
