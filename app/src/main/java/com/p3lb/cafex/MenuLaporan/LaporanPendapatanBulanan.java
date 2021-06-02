package com.p3lb.cafex.MenuLaporan;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import java.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.p3lb.cafex.MenuAuth.ProfileUser;
import com.p3lb.cafex.MenuDiskon.TambahDiskon;
import com.p3lb.cafex.MenuDiskon.TampilDiskon;
import com.p3lb.cafex.MenuTransaksi.TampilCheckoutMenu;
import com.p3lb.cafex.MenuTransaksi.TampilDataMenu;
import com.p3lb.cafex.R;
import com.p3lb.cafex.adapter.DiskonAdapter;
import com.p3lb.cafex.adapter.InventoriAdapter;
import com.p3lb.cafex.adapter.TranksaksiAdapter;
import com.p3lb.cafex.adapter.TranksaksiDiskonAdapter;
import com.p3lb.cafex.adapter.TranksaksiRefundAdapter;
import com.p3lb.cafex.model.diskon.Diskon;
import com.p3lb.cafex.model.diskon.PostDiskon;
import com.p3lb.cafex.model.inventori.Inventori;
import com.p3lb.cafex.model.trxbulanan.Normalbulan;
import com.p3lb.cafex.model.trxbulanan.Postnormalbulan;
import com.p3lb.cafex.model.trxdiskonbulan.Posttrxdiskonbulan;
import com.p3lb.cafex.model.trxdiskonbulan.Trxdiskonbulan;
import com.p3lb.cafex.model.trxdiskonbulanan.Diskonbulan;
import com.p3lb.cafex.model.trxdiskonbulanan.Postdiskonlbulan;
import com.p3lb.cafex.model.trxhbpbulan.Posttrxhbpbulan;
import com.p3lb.cafex.model.trxhbpbulan.Trxhbpbulan;
import com.p3lb.cafex.model.trxnormalbulan.Posttrxnormalbulan;
import com.p3lb.cafex.model.trxnormalbulan.Trxnormalbulan;
import com.p3lb.cafex.model.trxrefundbulan.Posttrxrefundbulan;
import com.p3lb.cafex.model.trxrefundbulan.Trxrefundbulan;
import com.p3lb.cafex.model.trxrefundbulanan.Postrefundbulan;
import com.p3lb.cafex.model.trxrefundbulanan.Refundbulan;
import com.p3lb.cafex.network.ApiHelper;
import com.p3lb.cafex.network.ApiInterface;
import com.whiteelephant.monthpicker.MonthPickerDialog;


import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LaporanPendapatanBulanan extends AppCompatActivity {
    TextView txtTanggal,totalTransaksi, jumlahTransaksi, totalHBP, jumlahDiskon, totalRefund, jumlahRefund, totalGross, totalNett,dekskripsilaporan;
    Button btnTanggal, tampilBulanan, backlaporanbulanan;
    Spinner spinnerLaporanBulanan;
    SharedPreferences sharedPreferences;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_ID = "id";
    String idcabang = "";
    Calendar c;
    DatePickerDialog dpd;
    String totaltrx = "";
    String jumlahtrx = "";
    String totaldiskon = "";
    String jumlahdiskon = "";
    String totalrefund = "";
    String jumlahrefund = "";
    String totalhbp = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_laporanbulanan);
        mRecyclerView = (RecyclerView) findViewById(R.id.rvlaporanbulanan);
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        txtTanggal = (TextView) findViewById(R.id.txtTanggal);
        totalTransaksi = (TextView) findViewById(R.id.totalTransaksi);
        jumlahTransaksi = (TextView) findViewById(R.id.jumlahTransaksi);
        totalHBP = (TextView) findViewById(R.id.totalHBP);
        jumlahDiskon = (TextView) findViewById(R.id.jumlahDiskon);
        totalRefund = (TextView) findViewById(R.id.totalRefund);
        jumlahRefund = (TextView) findViewById(R.id.jumlahRefund);
        totalGross = (TextView) findViewById(R.id.totalGross);
        totalNett = (TextView) findViewById(R.id.totalNett);
        dekskripsilaporan = (TextView) findViewById(R.id.dekskripsilaporan);
        spinnerLaporanBulanan = (Spinner) findViewById(R.id.spinnerLaporanBulanan);
        tampilBulanan = (Button) findViewById(R.id.tampilBulanan);
        backlaporanbulanan = (Button) findViewById(R.id.backlaporanbulanan);
        idcabang = sharedPreferences.getString(KEY_ID, null);
        btnTanggal = (Button) findViewById(R.id.btnTanggal);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.laporan, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLaporanBulanan.setAdapter(adapter);

        backlaporanbulanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LaporanPendapatanBulanan.this, LaporanActivity.class);
                startActivity(intent);
            }
        });
        btnTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar today = Calendar.getInstance();
                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(LaporanPendapatanBulanan.this,
                        new MonthPickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(int selectedMonth, int selectedYear) {
                                int month = selectedMonth + 1;
                                txtTanggal.setText(selectedYear+"-"+month+"-"+"1");
                                gettransaksinormal();
                                gettransaksidiskon();
                                gettransaksirefund();
                                gethbpbulan();
                            }
                        }, today.get(Calendar.YEAR), today.get(Calendar.MONTH));
                builder.setActivatedMonth(Calendar.JANUARY)
                        .setMinYear(2021)
                        .setActivatedYear(today.get(Calendar.YEAR))
                        .setMaxYear(2040)
                        .setMinMonth(Calendar.JANUARY)
                        .setTitle("Pilih Bulan")
                        .build().show();
            }
        });
//        btnTanggal.setOnClickListener(new View.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.N)
//            @Override
//            public void onClick(View v) {
//                c=Calendar.getInstance();
//                int day = c.get(Calendar.DAY_OF_MONTH);
//                int month = c.get(Calendar.MONTH);
//                int year = c.get(Calendar.YEAR);
//                dpd = new DatePickerDialog(LaporanPendapatanBulanan.this, new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker datePicker, int myear, int mmonth, int mday) {
//                        mmonth = mmonth+1;
//                        txtTanggal.setText(myear+"-"+mmonth+"-"+mday);
//                        gettransaksinormal();
//                        gettransaksidiskon();
//                        gettransaksirefund();
//                        gethbpbulan();
//
//                    }
//                }, day, month, year);
//
//                dpd.show();
//
//            }
//        });
        tampilBulanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(spinnerLaporanBulanan.getSelectedItem().toString().isEmpty()){
                    Toasty.normal(LaporanPendapatanBulanan.this, "Pilih kategori terlebih dahulu ", Toast.LENGTH_SHORT).show();
                }else{
                    if(spinnerLaporanBulanan.getSelectedItem().toString().equals("Transaksi Normal")){
                        tampiltrxnormal();
                        dekskripsilaporan.setText("*Sudah termasuk transaksi dengan diskon");
                    }else if(spinnerLaporanBulanan.getSelectedItem().toString().equals("Transaksi Refund")){
                        tampiltrxrefund();
                        dekskripsilaporan.setText("*Hanya transaksi refund");
                    }else if(spinnerLaporanBulanan.getSelectedItem().toString().equals("Transaksi Diskon")){
                        tampiltrxdiskon();
                        dekskripsilaporan.setText("*Hanya transaksi dengan diskon");
                    }
                }
            }
        });


    }

    public void gettransaksinormal() {
        ApiInterface mApiInterface = ApiHelper.getClient().create(ApiInterface.class);
        Call<Posttrxnormalbulan> call = mApiInterface.gettransaksibulan(idcabang, txtTanggal.getText().toString());
        call.enqueue(new Callback<Posttrxnormalbulan>() {
            @Override
            public void onResponse(Call<Posttrxnormalbulan> call, Response<Posttrxnormalbulan>
                    response) {
                List<Trxnormalbulan> trxnormalbulanList = response.body().getTrxnormalbulanList();
                totaltrx = trxnormalbulanList.get(0).getTotal_transaksi();
                jumlahtrx = trxnormalbulanList.get(0).getJumlah_transaksi();
                totalTransaksi.setText("Rp "+totaltrx);
                jumlahTransaksi.setText(jumlahtrx);
                totalGross.setText("Rp "+totaltrx);
            }

            @Override
            public void onFailure(Call<Posttrxnormalbulan> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
                Toasty.error(LaporanPendapatanBulanan.this, "Gagal memuat total transaksi  " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void gettransaksidiskon() {
        ApiInterface mApiInterface = ApiHelper.getClient().create(ApiInterface.class);
        Call<Posttrxdiskonbulan> call = mApiInterface.gettransaksidiskonbulan(idcabang, txtTanggal.getText().toString());
        call.enqueue(new Callback<Posttrxdiskonbulan>() {
            @Override
            public void onResponse(Call<Posttrxdiskonbulan> call, Response<Posttrxdiskonbulan>
                    response) {
                List<Trxdiskonbulan> trxdiskonbulanList = response.body().getTrxdiskonbulanList();
                totaldiskon = trxdiskonbulanList.get(0).getTotal_diskon();
                jumlahdiskon = trxdiskonbulanList.get(0).getJumlah_transaksidiskon();
                //totalDiskon.setText("Rp "+totaldiskon);
                jumlahDiskon.setText(jumlahdiskon);
            }

            @Override
            public void onFailure(Call<Posttrxdiskonbulan> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
                Toasty.error(LaporanPendapatanBulanan.this, "Gagal memuat total diskon  " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void gettransaksirefund() {
        ApiInterface mApiInterface = ApiHelper.getClient().create(ApiInterface.class);
        Call<Posttrxrefundbulan> call = mApiInterface.gettransaksirefundbulan(idcabang, txtTanggal.getText().toString());
        call.enqueue(new Callback<Posttrxrefundbulan>() {
            @Override
            public void onResponse(Call<Posttrxrefundbulan> call, Response<Posttrxrefundbulan>
                    response) {
                List<Trxrefundbulan> trxrefundbulanList = response.body().getTrxrefundbulanList();
                totalrefund = trxrefundbulanList.get(0).getTotal_refund();
                jumlahrefund = trxrefundbulanList.get(0).getJumlah_transaksirefund();
                if(totalrefund == null){
                    totalrefund = "0";
                }
                if(jumlahrefund == null){
                    jumlahrefund = "0";
                }
                totalRefund.setText("Rp "+totalrefund);
                jumlahRefund.setText(jumlahrefund);
            }

            @Override
            public void onFailure(Call<Posttrxrefundbulan> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
                Toasty.error(LaporanPendapatanBulanan.this, "Gagal memuat total refund  " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void gethbpbulan() {
        ApiInterface mApiInterface = ApiHelper.getClient().create(ApiInterface.class);
        Call<Posttrxhbpbulan> call = mApiInterface.gettransaksihbpbulan(idcabang, txtTanggal.getText().toString());
        call.enqueue(new Callback<Posttrxhbpbulan>() {
            @Override
            public void onResponse(Call<Posttrxhbpbulan> call, Response<Posttrxhbpbulan>
                    response) {
                if(response.isSuccessful()){
                    try {
                        List<Trxhbpbulan> trxhbpbulanList = response.body().getTrxhbpbulanList();
                        totalhbp = trxhbpbulanList.get(0).getTotal_biayaproduk();
                        int hbp = 0;
                        if(totalhbp.isEmpty()){
                            hbp = 0;
                        }else{
                            hbp = Integer.parseInt(totalhbp);
                        }
                        totalHBP.setText("Rp " + totalhbp);

                        String s = (totalTransaksi.getText().toString().substring(3));

                        int total = Integer.parseInt(s);
                        //int total = Integer.parseInt(totaltrx);
                        int net = total - hbp;
                        totalNett.setText("Rp " + net);
                        Log.d("totaltrx", "total " + total);
                    }catch (NullPointerException e){
                        e.printStackTrace();
                        Toasty.error(LaporanPendapatanBulanan.this, "Tidak ditemukan  ", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toasty.error(LaporanPendapatanBulanan.this, "Gagal  ", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Posttrxhbpbulan> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
                Toasty.error(LaporanPendapatanBulanan.this, "Gagal  " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void tampiltrxnormal() {
        ApiInterface mApiInterface = ApiHelper.getClient().create(ApiInterface.class);
        Call<Postnormalbulan> call = mApiInterface.gettrxbulanan(idcabang, txtTanggal.getText().toString());
        call.enqueue(new Callback<Postnormalbulan>() {
            @Override
            public void onResponse(Call<Postnormalbulan> call, Response<Postnormalbulan>
                    response) {
                if(response.isSuccessful()){
                    List<Normalbulan> normalbulanList = response.body().getNormalbulanList();
                    mAdapter = new TranksaksiAdapter(normalbulanList);
                    mRecyclerView.setAdapter(mAdapter);
                }else{
                    Toasty.error(LaporanPendapatanBulanan.this, "Gagal  ", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Postnormalbulan> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
                Toasty.error(LaporanPendapatanBulanan.this, "Gagal  " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void tampiltrxdiskon() {
        ApiInterface mApiInterface = ApiHelper.getClient().create(ApiInterface.class);
        Call<Postdiskonlbulan> call = mApiInterface.gettrxdiskonbulanan(idcabang, txtTanggal.getText().toString());
        call.enqueue(new Callback<Postdiskonlbulan>() {
            @Override
            public void onResponse(Call<Postdiskonlbulan> call, Response<Postdiskonlbulan>
                    response) {
                if(response.isSuccessful()){
                    List<Diskonbulan> diskonbulans = response.body().getDiskonbulanList();
                    mAdapter = new TranksaksiDiskonAdapter(diskonbulans);
                    mRecyclerView.setAdapter(mAdapter);
                }else{
                    Toasty.error(LaporanPendapatanBulanan.this, "Gagal  ", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Postdiskonlbulan> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
                Toasty.error(LaporanPendapatanBulanan.this, "Gagal  " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void tampiltrxrefund() {
        ApiInterface mApiInterface = ApiHelper.getClient().create(ApiInterface.class);
        Call<Postrefundbulan> call = mApiInterface.gettrxrefundbulanan(idcabang, txtTanggal.getText().toString());
        call.enqueue(new Callback<Postrefundbulan>() {
            @Override
            public void onResponse(Call<Postrefundbulan> call, Response<Postrefundbulan>
                    response) {
                if(response.isSuccessful()){
                    List<Refundbulan> refundbulans = response.body().getRefundbulanList();
                    mAdapter = new TranksaksiRefundAdapter(refundbulans);
                    mRecyclerView.setAdapter(mAdapter);
                }else{
                    Toasty.error(LaporanPendapatanBulanan.this, "Gagal  ", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Postrefundbulan> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
                Toasty.error(LaporanPendapatanBulanan.this, "Gagal  " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
