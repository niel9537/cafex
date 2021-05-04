package com.p3lb.cafex.MenuTransaksi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.p3lb.cafex.R;
import com.p3lb.cafex.adapter.MenusAdapter;
import com.p3lb.cafex.model.produk.GetProducts;
import com.p3lb.cafex.model.produk.Products;
import com.p3lb.cafex.network.ApiHelper;
import com.p3lb.cafex.network.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TampilDataMenu extends AppCompatActivity {
    ApiInterface mApiInterface;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_ID = "id";
    String nama_user = "";
    TextView placeholderUsername;
    public static TampilDataMenu me;
    private FloatingActionButton  fltKeranjangBelanja;
    List<Products> productsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_menu);
        fltKeranjangBelanja = findViewById(R.id.btnKeranjangBelanja);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycleView);
       // mLayoutManager = new LinearLayoutManager(this);
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        nama_user = sharedPreferences.getString(KEY_USERNAME,null);
        placeholderUsername = (TextView) findViewById(R.id.placeholderUsername);
        placeholderUsername.setText("Selamat datang "+nama_user);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mApiInterface = ApiHelper.getClient().create(ApiInterface.class);
        me=this;

        refresh();

        fltKeranjangBelanja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TampilDataMenu.this, TampilCheckoutMenu.class);
                startActivity(intent);
            }
        });

    }

    public void refresh() {
        ApiInterface mApiInterface = ApiHelper.getClient().create(ApiInterface.class);
        Call<GetProducts> call = mApiInterface.getProducts();
        call.enqueue(new Callback<GetProducts>() {
            @Override
            public void onResponse(Call<GetProducts> call, Response<GetProducts>
                    response) {
                List<Products> menuList = response.body().getListDataProducts();
                Log.d("Retrofit Get", "Jumlah data Menu: " +
                        String.valueOf(menuList.size()));
                mAdapter = new MenusAdapter(menuList);
                mRecyclerView.setAdapter(mAdapter);

            }

            @Override
            public void onFailure(Call<GetProducts> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
                Toast.makeText(TampilDataMenu.this, "Gagal memuat menu  " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
