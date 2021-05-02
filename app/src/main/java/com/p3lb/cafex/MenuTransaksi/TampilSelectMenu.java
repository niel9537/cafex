package com.p3lb.cafex.MenuTransaksi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.p3lb.cafex.Config;
import com.p3lb.cafex.R;
import com.p3lb.cafex.network.ApiHelper;
import com.p3lb.cafex.network.ApiInterface;

public class TampilSelectMenu extends AppCompatActivity {

    TextView namaProduk, hargaProduk, kategoriProduk;
    String idProduk;
    ImageView fotoProduk;
    ApiInterface mApiInterface;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_menu);
        namaProduk = (TextView) findViewById(R.id.namaProduk);
        hargaProduk = (TextView) findViewById(R.id.hargaProduk);
        kategoriProduk = (TextView) findViewById(R.id.kategoriProduk);
        fotoProduk = (ImageView) findViewById(R.id.fotoProduk);

        //Inisialisasi intent ke komponen form
        Intent mIntent = getIntent();
        String nama = mIntent.getStringExtra("nama_produk");
        idProduk = mIntent.getStringExtra("id_produk");
        namaProduk.setText(mIntent.getStringExtra("nama_produk"));
        hargaProduk.setText(mIntent.getStringExtra("harga_produk"));
        kategoriProduk.setText(mIntent.getStringExtra("kategori_produk"));
        //Input gambar ke imgview
        Glide.with(TampilSelectMenu.this)
                .load(Config.IMAGES_URL + mIntent.getStringExtra("foto_produk"))
                .apply(new RequestOptions().override(350, 550))
                .apply(RequestOptions.circleCropTransform())
                .into(fotoProduk);

        // Definisi API
        mApiInterface = ApiHelper.getClient().create(ApiInterface.class);
        Log.d("Retrofit Get", nama);
    }
}