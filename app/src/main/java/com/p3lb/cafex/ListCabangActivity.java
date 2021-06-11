package com.p3lb.cafex;

import android.content.Intent;
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
import com.p3lb.cafex.MenuTransaksi.TampilCheckoutMenu;
import com.p3lb.cafex.MenuTransaksi.TampilDataMenu;
import com.p3lb.cafex.adapter.ListCabangAdapter;
import com.p3lb.cafex.model.cabang.Cabang;
import com.p3lb.cafex.model.listcabang.GetCabang;
import com.p3lb.cafex.model.listcabang.ListCabang;
import com.p3lb.cafex.network.ApiHelper;
import com.p3lb.cafex.network.ApiInterface;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListCabangActivity extends AppCompatActivity {
    Button backlogin;
    ApiInterface mApiInterface;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public static ListCabangActivity ll;
    List<Cabang> cabangList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_listcabang);
        backlogin = (Button) findViewById(R.id.backlogin);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_listcabang);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mApiInterface = ApiHelper.getClient().create(ApiInterface.class);
        ll=this;
        listcabang();
        backlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListCabangActivity.this, LoginKasir.class);
                startActivity(intent);
            }
        });
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);

    }

    public void listcabang() {
        ApiInterface mApiInterface = ApiHelper.getClient().create(ApiInterface.class);
        Call<GetCabang> call = mApiInterface.listcabang("1");
        call.enqueue(new Callback<GetCabang>() {
            @Override
            public void onResponse(Call<GetCabang> call, Response<GetCabang>
                    response) {
                List<ListCabang> cabangList = response.body().getListCabangList();
                Log.d("Retrofit Get", "Jumlah cabang: " +
                        String.valueOf(cabangList.size()));
                mAdapter = new ListCabangAdapter(cabangList);
                mRecyclerView.setAdapter(mAdapter);

            }

            @Override
            public void onFailure(Call<GetCabang> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
                Toasty.error(ListCabangActivity.this, "Gagal memuat cabang  " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
