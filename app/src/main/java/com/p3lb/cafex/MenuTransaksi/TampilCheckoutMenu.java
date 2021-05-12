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

import es.dmoral.toasty.Toasty;
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
    String id_detail = "";
    private int totalbyr = 0;
    public static TampilCheckoutMenu mi;
    List<Cart> cartList ;




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
        Intent mIntent = getIntent();
        id_detail = mIntent.getStringExtra("id_detailtransaksi");
        mApiInterface = ApiHelper.getClient().create(ApiInterface.class);
        mi=this;

        refresh();
        btnBayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(totalBayar.getText().toString().equals("Rp 0")){
                    Toasty.error(TampilCheckoutMenu.this, "Keranjang masih kosong", Toast.LENGTH_LONG).show();
                    return;
                }else{
                    if (namaPembeli.getText().toString().isEmpty()) {
                        Toasty.error(TampilCheckoutMenu.this, "Masukkan nama pembeli", Toast.LENGTH_SHORT).show();
                        return;
                    }else{
                        Toasty.normal(TampilCheckoutMenu.this, "Sukses", Toast.LENGTH_SHORT).show();
                        bayar();
                    }
                }

            }
        });

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
                    Toasty.success(getApplicationContext(), "Transaksi berhasil di proses", Toast.LENGTH_SHORT).show();
                    updatecart();

                }
                else {
                    Log.d("RETRO", "ON FAIL : " + response.message());
                    Toasty.error(getApplicationContext(), "Transaksi gagal di proses", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(TampilCheckoutMenu.this, TampilDataMenu.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<PostTransaksi> call, Throwable t) {
                Log.d("RETRO", "ON FAILURE : " + t.getMessage());
                Toasty.error(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            }
        });
    }

    void updatecart(){
        Call<PostTransaksi> postTransaksiCall = mApiInterface.updatecart(
                idcabang,
                namaPembeli.getText().toString(),
                username);
        postTransaksiCall.enqueue(new Callback<PostTransaksi>() {
            @Override
            public void onResponse(Call<PostTransaksi> call, Response<PostTransaksi> response) {
                if(response.isSuccessful()) {
                    Log.d("RETRO", "ON SUCCESS : " + response.message());
                    Toasty.success(getApplicationContext(), "Update berhasil", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(TampilCheckoutMenu.this, TampilDataMenu.class);
                    startActivity(intent);

                }
                else {
                    Log.d("RETRO", "ON FAIL : " + response.message());
                    Toasty.error(getApplicationContext(), "Update gagal", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(TampilCheckoutMenu.this, TampilDataMenu.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<PostTransaksi> call, Throwable t) {
                Log.d("RETRO", "ON FAILURE : " + t.getMessage());
                Toasty.success(getApplicationContext(), "Sukses di update", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(TampilCheckoutMenu.this, TampilDataMenu.class);
                startActivity(intent);
            }
        });
    }

    void deletecart(String iddelete){
        Call<PostPutDelTransaksi> postPutDelTransaksiCall = mApiInterface.deletecart(
                iddelete,
                idcabang,
                username);

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
