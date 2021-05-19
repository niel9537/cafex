package com.p3lb.cafex.MenuRefund;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.p3lb.cafex.MenuAuth.Dashboard;
import com.p3lb.cafex.MenuCabang.TambahCabang;
import com.p3lb.cafex.MenuCabang.TampilCabang;
import com.p3lb.cafex.MenuDiskon.EditDiskon;
import com.p3lb.cafex.MenuTransaksi.TampilDataMenu;
import com.p3lb.cafex.R;
import com.p3lb.cafex.adapter.CabangAdapter;
import com.p3lb.cafex.adapter.DiskonAdapter;
import com.p3lb.cafex.adapter.RefundAdapter;
import com.p3lb.cafex.model.cabang.Cabang;
import com.p3lb.cafex.model.cabang.PostCabang;
import com.p3lb.cafex.model.diskon.Diskon;
import com.p3lb.cafex.model.refund.PostRefund;
import com.p3lb.cafex.model.refund.Refund;
import com.p3lb.cafex.network.ApiHelper;
import com.p3lb.cafex.network.ApiInterface;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TampilRefund extends AppCompatActivity {
    ApiInterface mApiInterface;
    TextView id_transaksi, nama_pembeli, tanggal_beli, grand_total;
    Button btnrefund;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_ID = "id";
    private static final String KEY_TRX = "trx";
    public static TampilRefund is;
    String trxid = "";
    String idcabang = "";
    List<Cabang> cabangList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_refund);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_refund);
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        id_transaksi = (TextView) findViewById(R.id.id_transaksi);
        nama_pembeli = (TextView) findViewById(R.id.nama_pembeli);
        tanggal_beli = (TextView) findViewById(R.id.tanggal_beli);
        grand_total = (TextView) findViewById(R.id.grand_total);
        btnrefund = (Button) findViewById(R.id.btnrefund);
        idcabang = sharedPreferences.getString(KEY_ID, null);
        trxid = sharedPreferences.getString(KEY_TRX, null);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mApiInterface = ApiHelper.getClient().create(ApiInterface.class);
        is = this;
        refresh();

        btnrefund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refund();
            }
        });
    }

    public void refresh() {
        ApiInterface mApiInterface = ApiHelper.getClient().create(ApiInterface.class);
        Call<PostRefund> call = mApiInterface.getrefund(idcabang, trxid);
        call.enqueue(new Callback<PostRefund>() {
            @Override
            public void onResponse(Call<PostRefund> call, Response<PostRefund>
                    response) {
                if(response.isSuccessful()) {
                    List<Refund> refundList = response.body().getRefundList();
                    id_transaksi.setText(refundList.get(0).getIdTransaksi());
                    nama_pembeli.setText(refundList.get(0).getNamaPembeli());
                    tanggal_beli.setText(refundList.get(0).getTanggal());
                    grand_total.setText("Rp "+refundList.get(0).getTotalBayar());
                    Log.d("Retrofit Get", "Jumlah user: " +
                            String.valueOf(refundList.size()));
                    mAdapter = new RefundAdapter(refundList);
                    mRecyclerView.setAdapter(mAdapter);

                }
                else {
                    Log.d("RETRO", "ON FAIL : " + response.message());
                    Toasty.error(getApplicationContext(), "Gagal mendapatkan refund", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(TampilRefund.this, TampilDataMenu.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<PostRefund> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
                Toasty.error(TampilRefund.this, "Gagal memuat data  " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void refund() {
        ApiInterface mApiInterface = ApiHelper.getClient().create(ApiInterface.class);
        Call<PostRefund> refundCall = mApiInterface.updaterefund(idcabang, trxid);
        refundCall.enqueue(new Callback<PostRefund>() {
            @Override
            public void onResponse(Call<PostRefund> call, Response<PostRefund>
                    response) {
                if(response.isSuccessful()) {
                    Toasty.success(getApplicationContext(), "Sukses melakukan refund", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(TampilRefund.this, TampilDataMenu.class);
                    startActivity(intent);
                }
                else {
                    Log.d("RETRO", "ON FAIL : " + response.message());
                    Toasty.error(getApplicationContext(), "Gagal mendapatkan refund ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PostRefund> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
                Toasty.error(TampilRefund.this, "Gagal memuat data  " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}