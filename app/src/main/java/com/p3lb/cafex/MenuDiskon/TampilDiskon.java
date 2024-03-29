package com.p3lb.cafex.MenuDiskon;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.p3lb.cafex.MenuAuth.Dashboard;
import com.p3lb.cafex.MenuAuth.TampilRegistrasi;
import com.p3lb.cafex.MenuProduk.TambahDataProduk;
import com.p3lb.cafex.MenuProduk.TampilDataProduk;
import com.p3lb.cafex.R;
import com.p3lb.cafex.adapter.DiskonAdapter;
import com.p3lb.cafex.adapter.UsersRegistrasiAdapter;
import com.p3lb.cafex.model.auth.LoginRegisterUsers;
import com.p3lb.cafex.model.auth.Users;
import com.p3lb.cafex.model.diskon.Diskon;
import com.p3lb.cafex.model.diskon.PostDiskon;
import com.p3lb.cafex.network.ApiHelper;
import com.p3lb.cafex.network.ApiInterface;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TampilDiskon extends AppCompatActivity {
    ApiInterface mApiInterface;
    EditText edtsearchdiskon;
    Button btnsearchdiskon;
    Button backlistdiskon, btnTambahDiskon;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_ID = "id";
    public static TampilDiskon dd;
    String idcabang = "";
    List<Diskon> diskonList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_datadiskon);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_diskon);
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        btnTambahDiskon = (Button) findViewById(R.id.btnTambahDiskon);
        edtsearchdiskon = (EditText) findViewById(R.id.edtsearchdiskon);
        btnsearchdiskon = (Button) findViewById(R.id.btnsearchdiskon);
        backlistdiskon = (Button) findViewById(R.id.backlistdiskon);

        idcabang = sharedPreferences.getString(KEY_ID,null);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mApiInterface = ApiHelper.getClient().create(ApiInterface.class);
        dd=this;
        tampildiskon();
        backlistdiskon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TampilDiskon.this, Dashboard.class);
                startActivity(intent);
            }
        });
        btnsearchdiskon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                searchdiskon();
            }
        });
        btnTambahDiskon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TampilDiskon.this, TambahDiskon.class);
                startActivity(intent);
            }
        });
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
    }

    public void tampildiskon() {
        ApiInterface mApiInterface = ApiHelper.getClient().create(ApiInterface.class);
        Call<PostDiskon> call = mApiInterface.getdiskon();
        call.enqueue(new Callback<PostDiskon>() {
            @Override
            public void onResponse(Call<PostDiskon> call, Response<PostDiskon>
                    response) {
                List<Diskon> diskonList = response.body().getDiskonList();
                mAdapter = new DiskonAdapter(diskonList);
                mRecyclerView.setAdapter(mAdapter);


            }

            @Override
            public void onFailure(Call<PostDiskon> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
                Toasty.error(TampilDiskon.this, "Gagal memuat diskon  " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void searchdiskon() {
        ApiInterface mApiInterface = ApiHelper.getClient().create(ApiInterface.class);
        Call<PostDiskon> call = mApiInterface.searchdiskon(edtsearchdiskon.getText().toString());
        call.enqueue(new Callback<PostDiskon>() {
            @Override
            public void onResponse(Call<PostDiskon> call, Response<PostDiskon>
                    response) {
                List<Diskon> diskonList = response.body().getDiskonList();
                mAdapter = new DiskonAdapter(diskonList);
                mRecyclerView.setAdapter(mAdapter);

            }

            @Override
            public void onFailure(Call<PostDiskon> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
                Toasty.error(TampilDiskon.this, "Gagal memuat diskon  " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
