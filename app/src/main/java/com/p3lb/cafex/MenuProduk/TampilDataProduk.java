package com.p3lb.cafex.MenuProduk;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.p3lb.cafex.MenuTransaksi.TampilCheckoutMenu;
import com.p3lb.cafex.R;
import com.p3lb.cafex.adapter.ProductsAdapter;
import com.p3lb.cafex.model.produk.GetProducts;
import com.p3lb.cafex.model.produk.Products;
import com.p3lb.cafex.network.ApiHelper;
import com.p3lb.cafex.network.ApiInterface;


import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TampilDataProduk extends AppCompatActivity {
    ApiInterface mApiInterface;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public static TampilDataProduk ma;
    private FloatingActionButton fltTambahProduk;
    List<Products> productsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_dataproduk);
        fltTambahProduk = findViewById(R.id.btnTambahProduk);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_produk);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mApiInterface = ApiHelper.getClient().create(ApiInterface.class);
        ma=this;

        refresh();

        fltTambahProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TampilDataProduk.this, TambahDataProduk.class);
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
                        List<Products> productsList = response.body().getListDataProducts();
                        Log.d("Retrofit Get", "Jumlah data Produk: " +
                                String.valueOf(productsList.size()));
                        mAdapter = new ProductsAdapter(productsList);
                        mRecyclerView.setAdapter(mAdapter);


            }

            @Override
            public void onFailure(Call<GetProducts> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
                Toasty.error(TampilDataProduk.this, "Gagal memuat produk  " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
