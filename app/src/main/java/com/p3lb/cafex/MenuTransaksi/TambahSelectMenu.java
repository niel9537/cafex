package com.p3lb.cafex.MenuTransaksi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.p3lb.cafex.Config;
import com.p3lb.cafex.MenuAuth.LoginKasir;
import com.p3lb.cafex.MenuAuth.RegistrasiUser;
import com.p3lb.cafex.R;
import com.p3lb.cafex.model.auth.LoginRegisterUsers;
import com.p3lb.cafex.model.transaksi.PostPutDelTransaksi;
import com.p3lb.cafex.network.ApiHelper;
import com.p3lb.cafex.network.ApiInterface;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahSelectMenu extends AppCompatActivity {

    TextView namaProduk, hargaProduk, kategoriProduk, subTotalProduk, jumlahItem;
    EditText namaCustomer;
    String idProduk;
    Button btnMinus, btnPlus;
    ImageButton btnSimpan;
    ImageView fotoProduk;
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_ID = "id";

    ApiInterface apiInterface = ApiHelper.getClient().create(ApiInterface.class);
    int subtotal = 0, subtotalnaik = 0, hargas = 0;
    String harga = "";
    String price = "";
    String nama_user = "";
    String kode_cabang = "";
    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.minusBtn :
                    minusSubtotal();
                    break;
                case R.id.plusBtn :
                    plusSubtotal();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_menu);
        namaProduk = (TextView) findViewById(R.id.namaProduk);
        hargaProduk = (TextView) findViewById(R.id.hargaProduk);
        kategoriProduk = (TextView) findViewById(R.id.kategoriProduk);
        fotoProduk = (ImageView) findViewById(R.id.fotoProduk);
        subTotalProduk = (TextView) findViewById(R.id.subtotalProduk);
        jumlahItem = (TextView) findViewById(R.id.jumlahItem);
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        nama_user = sharedPreferences.getString(KEY_USERNAME,null);
        kode_cabang = sharedPreferences.getString(KEY_ID,null);
        btnMinus = (Button) findViewById(R.id.minusBtn);
        btnPlus = (Button) findViewById(R.id.plusBtn);
        btnSimpan = (ImageButton) findViewById(R.id.btnSimpan);
        btnPlus.setOnClickListener(clickListener);
        btnMinus.setOnClickListener(clickListener);
        //Inisialisasi intent ke komponen form
        Intent mIntent = getIntent();
        String nama = mIntent.getStringExtra("nama_produk");
        harga = mIntent.getStringExtra("harga_produk");
        idProduk = mIntent.getStringExtra("id_produk");
        namaProduk.setText(mIntent.getStringExtra("nama_produk"));
        hargaProduk.setText("Rp "+mIntent.getStringExtra("harga_produk"));
        kategoriProduk.setText(mIntent.getStringExtra("kategori_produk"));
        //Input gambar ke imgview
        Glide.with(TambahSelectMenu.this)
                .load(Config.IMAGES_URL + mIntent.getStringExtra("foto_produk"))
                .apply(new RequestOptions().override(350, 550))
                .apply(RequestOptions.circleCropTransform())
                .into(fotoProduk);

        // Definisi API
        apiInterface = ApiHelper.getClient().create(ApiInterface.class);
        iniSubtotal();
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addcart();
            }
        });
    }
    private void iniSubtotal(){
        subtotal = 0;
        subTotalProduk.setText(subtotal + "");
    }
    private void plusSubtotal(){
        //subtotal++;
        subtotalnaik++;
        hargas = Integer.parseInt(harga);
        subtotal = subtotalnaik * hargas;
        jumlahItem.setText(subtotalnaik+"");
        price = String.valueOf(subtotal);
        subTotalProduk.setText("Rp "+ price);
    }
    private void minusSubtotal(){
        //subtotal--;
        subtotalnaik--;
        hargas = Integer.parseInt(harga);
        subtotal = subtotalnaik * hargas;
        jumlahItem.setText(subtotalnaik+"");
        price = String.valueOf(subtotal);
        subTotalProduk.setText("Rp "+ price);
    }

    private void addcart(){
        Call<PostPutDelTransaksi> postAddcart = apiInterface.addcart(
                idProduk,
                kode_cabang,
                nama_user,
                jumlahItem.getText().toString(),
                price,
                ""
        );

        postAddcart.enqueue(new Callback<PostPutDelTransaksi>() {
            @Override
            public void onResponse(Call<PostPutDelTransaksi> call, Response<PostPutDelTransaksi> response) {
                if(response.isSuccessful()) {
                    Log.d("RETRO", "ON SUCCESS : " + response.message());
                    Toasty.success(getApplicationContext(), "Sukses ditambahkan ke keranjang", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(TambahSelectMenu.this, TampilDataMenu.class);
                    startActivity(intent);
                }
                else {
                    Log.d("RETRO", "ON FAIL : " + response.message());
                    Toasty.error(getApplicationContext(), "Gagal ditambahkan ke keranjang", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(TambahSelectMenu.this, TampilDataMenu.class);
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