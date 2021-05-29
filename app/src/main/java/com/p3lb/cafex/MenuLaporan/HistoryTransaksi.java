package com.p3lb.cafex.MenuLaporan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.p3lb.cafex.MenuAuth.LoginKasir;
import com.p3lb.cafex.R;
import com.p3lb.cafex.adapter.HistoriTranksaksiAdapter;
import com.p3lb.cafex.adapter.ListCabangAdapter;
import com.p3lb.cafex.model.cabang.Cabang;
import com.p3lb.cafex.model.listcabang.GetCabang;
import com.p3lb.cafex.model.listcabang.ListCabang;
import com.p3lb.cafex.model.trxbulanan.Getnormalbulan;
import com.p3lb.cafex.model.trxbulanan.History;
import com.p3lb.cafex.model.trxbulanan.Normalbulan;
import com.p3lb.cafex.model.trxbulanan.Postnormalbulan;
import com.p3lb.cafex.network.ApiHelper;
import com.p3lb.cafex.network.ApiInterface;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryTransaksi extends AppCompatActivity {
    Button backhistory;
    ApiInterface mApiInterface;
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static final String KEY_ID = "id";
    String idcabang = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_history_transaksi);
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        backhistory = (Button) findViewById(R.id.backhistory);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_historitransaksi);
        idcabang = sharedPreferences.getString(KEY_ID, null);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mApiInterface = ApiHelper.getClient().create(ApiInterface.class);
        Log.d("coba1", idcabang);
        refresh();
        backhistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HistoryTransaksi.this, LaporanActivity.class);
                startActivity(intent);
            }
        });

    }

    public void refresh() {
        ApiInterface mApiInterface = ApiHelper.getClient().create(ApiInterface.class);
        Call<Getnormalbulan> call = mApiInterface.historitransaksi(idcabang);
        call.enqueue(new Callback<Getnormalbulan>() {
            @Override
            public void onResponse(Call<Getnormalbulan> call, Response<Getnormalbulan>
                    response) {
                List<History> historyList = response.body().getHistoryList();
                Log.d("coba", historyList.get(0).getIdCabang());
                mAdapter = new HistoriTranksaksiAdapter(historyList);
                mRecyclerView.setAdapter(mAdapter);

            }

            @Override
            public void onFailure(Call<Getnormalbulan> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
                Toasty.error(HistoryTransaksi.this, "Gagal memuat transaksi  " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
