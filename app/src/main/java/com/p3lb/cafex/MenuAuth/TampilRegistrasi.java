package com.p3lb.cafex.MenuAuth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.p3lb.cafex.MenuProduk.TambahDataProduk;
import com.p3lb.cafex.MenuProduk.TampilDataProduk;
import com.p3lb.cafex.MenuUser.TampilUser;
import com.p3lb.cafex.R;
import com.p3lb.cafex.adapter.ProductsAdapter;
import com.p3lb.cafex.adapter.UsersRegistrasiAdapter;
import com.p3lb.cafex.model.auth.LoginRegisterUsers;
import com.p3lb.cafex.model.auth.Users;
import com.p3lb.cafex.model.produk.GetProducts;
import com.p3lb.cafex.model.produk.Products;
import com.p3lb.cafex.network.ApiHelper;
import com.p3lb.cafex.network.ApiInterface;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TampilRegistrasi extends AppCompatActivity {
    ApiInterface mApiInterface;
    Button backlistpendaftar;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_ID = "id";
    public static TampilRegistrasi mii;
    String idcabang = "";
    List<Users> usersList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_datauser_register);
        backlistpendaftar = (Button) findViewById(R.id.backlistpendaftar);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_userregister);
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        idcabang = sharedPreferences.getString(KEY_ID,null);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mApiInterface = ApiHelper.getClient().create(ApiInterface.class);
        mii=this;

        tampilregistrasi();
        backlistpendaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TampilRegistrasi.this, TampilUser.class);
                startActivity(intent);
            }
        });
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);

    }

    public void tampilregistrasi() {
        ApiInterface mApiInterface = ApiHelper.getClient().create(ApiInterface.class);
        Call<LoginRegisterUsers> call = mApiInterface.tampilregistrasiuser(idcabang);
        call.enqueue(new Callback<LoginRegisterUsers>() {
            @Override
            public void onResponse(Call<LoginRegisterUsers> call, Response<LoginRegisterUsers>
                    response) {
                List<Users> usersList = response.body().getListDataUsers();
                Log.d("Retrofit Get", "Jumlah user: " +
                        String.valueOf(usersList.size()));
                mAdapter = new UsersRegistrasiAdapter(usersList);
                mRecyclerView.setAdapter(mAdapter);


            }

            @Override
            public void onFailure(Call<LoginRegisterUsers> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
                Toasty.error(TampilRegistrasi.this, "Gagal memuat users  " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}