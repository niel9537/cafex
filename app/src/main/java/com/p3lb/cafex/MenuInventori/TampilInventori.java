package com.p3lb.cafex.MenuInventori;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.p3lb.cafex.MenuAuth.Dashboard;
import com.p3lb.cafex.MenuCabang.TampilCabang;
import com.p3lb.cafex.MenuDiskon.TambahDiskon;
import com.p3lb.cafex.MenuDiskon.TampilDiskon;
import com.p3lb.cafex.R;
import com.p3lb.cafex.adapter.DiskonAdapter;
import com.p3lb.cafex.adapter.InventoriAdapter;
import com.p3lb.cafex.model.diskon.Diskon;
import com.p3lb.cafex.model.diskon.PostDiskon;
import com.p3lb.cafex.model.inventori.Inventori;
import com.p3lb.cafex.model.inventori.PostInventori;
import com.p3lb.cafex.network.ApiHelper;
import com.p3lb.cafex.network.ApiInterface;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TampilInventori extends AppCompatActivity {
    EditText bahanbaku;
    Button btntambahbahanbaku, backlistinventori;
    ApiInterface mApiInterface;
    //private FloatingActionButton fltTambahDiskon;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_ID = "id";
    public static TampilInventori kk;
    String idcabang = "";
    List<Inventori> inventoriList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_datainventori);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_inventori);
        bahanbaku = (EditText) findViewById(R.id.bahanbaku);
        btntambahbahanbaku = (Button) findViewById(R.id.btntambahbahanbaku);
        backlistinventori = (Button) findViewById(R.id.backlistinventori);
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        //fltTambahDiskon = (FloatingActionButton) findViewById(R.id.btnTambahInventori);
        idcabang = sharedPreferences.getString(KEY_ID,null);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mApiInterface = ApiHelper.getClient().create(ApiInterface.class);
        kk=this;
        tampilbahanbaku();

        backlistinventori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TampilInventori.this, Dashboard.class);
                startActivity(intent);
            }
        });
        btntambahbahanbaku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bahanbaku.getText().toString().isEmpty()){
                    Toasty.error(TampilInventori.this, "Isi nama bahanbaku terlebih dahulu", Toast.LENGTH_LONG).show();
                }else{
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    tambahbahanbaku();
                }

            }
        });
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
    }

    public void tampilbahanbaku() {
        ApiInterface mApiInterface = ApiHelper.getClient().create(ApiInterface.class);
        Call<PostInventori> call = mApiInterface.getInventori(idcabang);
        call.enqueue(new Callback<PostInventori>() {
            @Override
            public void onResponse(Call<PostInventori> call, Response<PostInventori>
                    response) {
                List<Inventori> inventoriList = response.body().getInventoriList();
                mAdapter = new InventoriAdapter(inventoriList);
                mRecyclerView.setAdapter(mAdapter);


            }

            @Override
            public void onFailure(Call<PostInventori> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
                Toasty.error(TampilInventori.this, "Gagal memuat bahanbaku  " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void tambahbahanbaku() {
        ApiInterface mApiInterface = ApiHelper.getClient().create(ApiInterface.class);
        Call<PostInventori> call = mApiInterface.addbahanbaku(idcabang, bahanbaku.getText().toString());
        call.enqueue(new Callback<PostInventori>() {
            @Override
            public void onResponse(Call<PostInventori> call, Response<PostInventori>
                    response) {
                if(response.isSuccessful()) {
                    Log.d("RETRO", "ON SUCCESS : " + response.message());
                    Toasty.success(getApplicationContext(), "Sukses menambahkan bahanbaku", Toast.LENGTH_SHORT).show();
                    tampilbahanbaku();
                }
                else {
                    Log.d("RETRO", "ON FAIL : " + response.message());
                    Toasty.error(getApplicationContext(), "Gagal menambahkan bahanbaku", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<PostInventori> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
                Toasty.error(TampilInventori.this, "Gagal memuat bahanbaku  " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
