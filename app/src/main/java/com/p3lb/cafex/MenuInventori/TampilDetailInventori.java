package com.p3lb.cafex.MenuInventori;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.p3lb.cafex.R;
import com.p3lb.cafex.adapter.BahanbakuAdapter;
import com.p3lb.cafex.adapter.InventoriAdapter;
import com.p3lb.cafex.model.bahanbaku.Bahanbaku;
import com.p3lb.cafex.model.bahanbaku.PostBahanbaku;
import com.p3lb.cafex.model.inventori.Inventori;
import com.p3lb.cafex.model.inventori.PostInventori;
import com.p3lb.cafex.network.ApiHelper;
import com.p3lb.cafex.network.ApiInterface;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TampilDetailInventori extends AppCompatActivity {
    EditText bahanbaku;
    Button btnbahanbakumasuk, btnbahanbakukeluar, backdetailbahanbaku, deleteinventori;
    ApiInterface mApiInterface;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_ID = "id";
    private static final String KEY_INVENTORI = "inventori";
    private static final String KEY_BAHANBAKU = "bahanbaku";
    public static TampilDetailInventori qa;
    String idcabang = "";
    String idinventori = "";
    String namabahanbaku = "";
    List<Bahanbaku> bahanbakuList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detaillnventori);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_bahanbaku);
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        btnbahanbakumasuk = (Button) findViewById(R.id.btnbahanbakumasuk);
        btnbahanbakukeluar = (Button) findViewById(R.id.btnbahanbakukeluar);
        backdetailbahanbaku = (Button) findViewById(R.id.backdetailbahanbaku);
        deleteinventori = (Button) findViewById(R.id.deleteinventori);
        idcabang = sharedPreferences.getString(KEY_ID,null);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mApiInterface = ApiHelper.getClient().create(ApiInterface.class);
        Intent mIntent = getIntent();
        idinventori = mIntent.getStringExtra("id_inventori");
        namabahanbaku = mIntent.getStringExtra("nama_bahanbaku");
        qa=this;
        refresh();
        deleteinventori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteinventori();
            }
        });
        backdetailbahanbaku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Intent = new Intent(TampilDetailInventori.this, TampilInventori.class);
                startActivity(Intent);
            }
        });
        btnbahanbakumasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(KEY_INVENTORI,idinventori);
                editor.putString(KEY_BAHANBAKU,namabahanbaku);
                editor.apply();
                Intent Intent = new Intent(TampilDetailInventori.this, TambahBahanbaku.class);
                startActivity(Intent);
            }
        });

        btnbahanbakukeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(KEY_INVENTORI,idinventori);
                editor.putString(KEY_BAHANBAKU,namabahanbaku);
                editor.apply();
                Intent Intent = new Intent(TampilDetailInventori.this, KeluarkanBahanbaku.class);
                startActivity(Intent);
            }
        });

    }

    public void refresh() {
        ApiInterface mApiInterface = ApiHelper.getClient().create(ApiInterface.class);
        Call<PostBahanbaku> bahanbakuCall = mApiInterface.getbahanbaku(idinventori,idcabang,namabahanbaku);
        bahanbakuCall.enqueue(new Callback<PostBahanbaku>() {
            @Override
            public void onResponse(Call<PostBahanbaku> call, Response<PostBahanbaku>
                    response) {
                List<Bahanbaku> bahanbakuList = response.body().getBahanbakuList();
                Log.d("Retrofit Get", "Jumlah riwayat bahanbaku: " +
                        String.valueOf(bahanbakuList.size()));
                mAdapter = new BahanbakuAdapter(bahanbakuList);
                mRecyclerView.setAdapter(mAdapter);


            }

            @Override
            public void onFailure(Call<PostBahanbaku> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
                Toasty.error(TampilDetailInventori.this, "Gagal memuat bahanbaku  " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void deleteinventori() {
        ApiInterface mApiInterface = ApiHelper.getClient().create(ApiInterface.class);
        Log.d("Id", ""+idinventori);
        Call<PostInventori> deleteinventori = mApiInterface.deleteinventori(idcabang,idinventori);
        deleteinventori.enqueue(new Callback<PostInventori>() {
            @Override
            public void onResponse(Call<PostInventori> call, Response<PostInventori>
                    response) {
                if (response.isSuccessful()) {
                    Toasty.success(TampilDetailInventori.this, "Sukses hapus bahanbaku  ", Toast.LENGTH_SHORT).show();
                    Intent Intent = new Intent(TampilDetailInventori.this, TampilInventori.class);
                    startActivity(Intent);
                }else{
                    Toasty.success(TampilDetailInventori.this, "Gagal hapus bahanbaku  ", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<PostInventori> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
                Toasty.error(TampilDetailInventori.this, "Gagal hapus bahanbaku  " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}