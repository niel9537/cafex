package com.p3lb.cafex.CRUDProduk;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.p3lb.cafex.R;
import com.p3lb.cafex.adapter.ProdukAdapter;
import com.p3lb.cafex.model.ProdukDAO;
import com.p3lb.cafex.network.ApiHelper;
import com.p3lb.cafex.network.ApiInterface;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TampilDataProduk extends AppCompatActivity {
    private RecyclerView recyclerView;
    ProdukAdapter produkAdapter;
    List<ProdukDAO> ProdukDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_dataproduk);
        ProdukDAO = new ArrayList<>();
        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getData();


    }

    public void getData() {
        ApiInterface apiInterface = ApiHelper.getClient().create(ApiInterface.class);
        Call<List<ProdukDAO>> call = apiInterface.getProdukDAO();

        call.enqueue(new Callback<List<ProdukDAO>>() {
            @Override
            public void onResponse(Call<List<ProdukDAO>> call, Response<List<ProdukDAO>> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    ProdukDAO.addAll(response.body());
                    produkAdapter = new ProdukAdapter(TampilDataProduk.this, ProdukDAO);
                    recyclerView.setAdapter(produkAdapter);
                    Toast.makeText(TampilDataProduk.this, "Semua data telah dimuat", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(TampilDataProduk.this, "Gagal memuat produk " + response.message(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<ProdukDAO>> call, Throwable t) {
                Toast.makeText(TampilDataProduk.this,"Gagal memuat karena " + t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }
}
