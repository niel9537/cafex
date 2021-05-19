package com.p3lb.cafex.MenuCabang;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.p3lb.cafex.MenuDiskon.TambahDiskon;
import com.p3lb.cafex.MenuDiskon.TampilDiskon;
import com.p3lb.cafex.R;
import com.p3lb.cafex.adapter.CabangAdapter;
import com.p3lb.cafex.adapter.DiskonAdapter;
import com.p3lb.cafex.model.cabang.Cabang;
import com.p3lb.cafex.model.cabang.PostCabang;
import com.p3lb.cafex.model.diskon.Diskon;
import com.p3lb.cafex.model.diskon.PostDiskon;
import com.p3lb.cafex.network.ApiHelper;
import com.p3lb.cafex.network.ApiInterface;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TampilCabang extends AppCompatActivity {
    ApiInterface mApiInterface;
    private FloatingActionButton fltTambahCabang;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_ID = "id";
    public static TampilCabang ik;
    String idcabang = "";
    List<Cabang> cabangList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_datacabang);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_cabang);
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        fltTambahCabang = (FloatingActionButton) findViewById(R.id.btnTambahCabang);
        idcabang = sharedPreferences.getString(KEY_ID,null);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mApiInterface = ApiHelper.getClient().create(ApiInterface.class);
        ik=this;
        refresh();

        fltTambahCabang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TampilCabang.this, TambahCabang.class);
                startActivity(intent);
            }
        });
    }

    public void refresh() {
        ApiInterface mApiInterface = ApiHelper.getClient().create(ApiInterface.class);
        Call<PostCabang> call = mApiInterface.getcabang();
        call.enqueue(new Callback<PostCabang>() {
            @Override
            public void onResponse(Call<PostCabang> call, Response<PostCabang>
                    response) {
                List<Cabang> cabangList = response.body().getCabangList();
                Log.d("Retrofit Get", "Jumlah cabang: " +
                        String.valueOf(cabangList.size()));
                mAdapter = new CabangAdapter(cabangList);
                mRecyclerView.setAdapter(mAdapter);


            }

            @Override
            public void onFailure(Call<PostCabang> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
                Toasty.error(TampilCabang.this, "Gagal memuat cabang  " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
