package com.p3lb.cafex.MenuLaporan;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.p3lb.cafex.R;
import com.p3lb.cafex.adapter.NettAdapter;
import com.p3lb.cafex.adapter.TranksaksiAdapter;
import com.p3lb.cafex.adapter.TranksaksiDiskonAdapter;
import com.p3lb.cafex.adapter.TranksaksiRefundAdapter;
import com.p3lb.cafex.model.trxbulanan.Normalbulan;
import com.p3lb.cafex.model.trxbulanan.Postnormalbulan;
import com.p3lb.cafex.model.trxdiskonbulan.Posttrxdiskonbulan;
import com.p3lb.cafex.model.trxdiskonbulan.Trxdiskonbulan;
import com.p3lb.cafex.model.trxdiskonbulanan.Diskonbulan;
import com.p3lb.cafex.model.trxdiskonbulanan.Postdiskonlbulan;
import com.p3lb.cafex.model.trxdiskontahun.Posttrxdiskontahun;
import com.p3lb.cafex.model.trxdiskontahun.Trxdiskontahun;
import com.p3lb.cafex.model.trxhbpbulan.Posttrxhbpbulan;
import com.p3lb.cafex.model.trxhbpbulan.Trxhbpbulan;
import com.p3lb.cafex.model.trxhbptahun.Posttrxhbptahun;
import com.p3lb.cafex.model.trxhbptahun.Trxhbptahun;
import com.p3lb.cafex.model.trxnormalbulan.Posttrxnormalbulan;
import com.p3lb.cafex.model.trxnormalbulan.Trxnormalbulan;
import com.p3lb.cafex.model.trxnormaltahun.Posttrxnormaltahun;
import com.p3lb.cafex.model.trxnormaltahun.Trxnormaltahun;
import com.p3lb.cafex.model.trxrefundbulan.Posttrxrefundbulan;
import com.p3lb.cafex.model.trxrefundbulan.Trxrefundbulan;
import com.p3lb.cafex.model.trxrefundbulanan.Postrefundbulan;
import com.p3lb.cafex.model.trxrefundbulanan.Refundbulan;
import com.p3lb.cafex.model.trxrefundtahun.Posttrxrefundtahun;
import com.p3lb.cafex.model.trxrefundtahun.Trxrefundtahun;
import com.p3lb.cafex.model.trxtahunan.Gettrxtahunan;
import com.p3lb.cafex.model.trxtahunan.Report;
import com.p3lb.cafex.model.trxtahunan.Result;
import com.p3lb.cafex.network.ApiHelper;
import com.p3lb.cafex.network.ApiInterface;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.util.Calendar;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LaporanPendapatanTahunan extends AppCompatActivity {
    TextView txtTanggal,totalTransaksi, jumlahTransaksi, totalHBP, jumlahDiskon, totalRefund, jumlahRefund, totalGross, totalNett,dekskripsilaporan;
    Button btnTanggal, tampilBulanan, backlaporantahunan;
    SharedPreferences sharedPreferences;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_ID = "id";
    String idcabang = "";
    Calendar c;
    DatePickerDialog dpd;
    String totaltrx = "0";
    String jumlahtrx = "0";
    String totaldiskon = "0";
    String jumlahdiskon = "0";
    String totalrefund = "0";
    String jumlahrefund = "0";
    String totalhbp = "0";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_laporantahunan);
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
        tampilBulanan = (Button) findViewById(R.id.tampilBulanan);
        backlaporantahunan = (Button) findViewById(R.id.backlaporantahunan);
        idcabang = sharedPreferences.getString(KEY_ID, null);
        btnTanggal = (Button) findViewById(R.id.btnTanggal);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        backlaporantahunan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LaporanPendapatanTahunan.this, LaporanActivity.class);
                startActivity(intent);
            }
        });
        btnTanggal.setOnClickListener(new View.OnClickListener() {
            //@RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                final Calendar today = Calendar.getInstance();
                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(LaporanPendapatanTahunan.this,
                        new MonthPickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(int selectedMonth, int selectedYear) {
                                int year = selectedYear;
                                txtTanggal.setText(year+"-"+"01"+"-"+"01");
                                initdate();
                                gettransaksinormal();
                                gettransaksidiskon();
                                gettransaksirefund();
                                gethbptahun();
                                tampilnettbulanan();
                            }
                        }, today.get(Calendar.YEAR), today.get(Calendar.MONTH));
                builder.setActivatedMonth(Calendar.JANUARY)
                        .setMinYear(2020)
                        .setMaxYear(2040)
                        .showYearOnly()
                        .setTitle("Pilih Tahun")
                        .build().show();
//                c=Calendar.getInstance();
//                int day = c.get(Calendar.DAY_OF_MONTH);
//                int month = c.get(Calendar.MONTH);
//                int year = c.get(Calendar.YEAR);
//
//                dpd = new DatePickerDialog(LaporanPendapatanTahunan.this, new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker datePicker, int myear, int mmonth, int mday) {
//                        mmonth = mmonth+1;
//                        txtTanggal.setText(myear+"-"+mmonth+"-"+mday);
//                        gettransaksinormal();
//                        gettransaksidiskon();
//                        gettransaksirefund();
//                        gethbptahun();
//                        tampilnettbulanan();
//
//                    }
//                }, day, month, year);
//
//                dpd.show();
            }
        });



    }
    public void initdate(){
         totaltrx = "0";
         jumlahtrx = "0";
         totaldiskon = "0";
         jumlahdiskon = "0";
         totalrefund = "0";
         jumlahrefund = "0";
         totalhbp = "0";
         totalHBP.setText("0");
         totalTransaksi.setText("Rp 0");
         totalGross.setText("0");
         totalNett.setText("0");
    }
    public void gettransaksinormal() {
        ApiInterface mApiInterface = ApiHelper.getClient().create(ApiInterface.class);
        Call<Posttrxnormaltahun> call = mApiInterface.gettransaksitahun(idcabang, txtTanggal.getText().toString());
        call.enqueue(new Callback<Posttrxnormaltahun>() {
            @Override
            public void onResponse(Call<Posttrxnormaltahun> call, Response<Posttrxnormaltahun>
                    response) {
                List<Trxnormaltahun> trxnormaltahunList = response.body().getTrxnormaltahunList();
                totaltrx = trxnormaltahunList.get(0).getTotal_transaksi();
                jumlahtrx = trxnormaltahunList.get(0).getJumlah_transaksi();
                if(totaltrx == null){
                    totalTransaksi.setText("0");
                }

                totalTransaksi.setText("Rp "+totaltrx);
                jumlahTransaksi.setText(jumlahtrx);
                totalGross.setText("Rp "+totaltrx);
            }

            @Override
            public void onFailure(Call<Posttrxnormaltahun> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
                Toasty.error(LaporanPendapatanTahunan.this, "Gagal memuat total transaksi  " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void gettransaksidiskon() {
        ApiInterface mApiInterface = ApiHelper.getClient().create(ApiInterface.class);
        Call<Posttrxdiskontahun> call = mApiInterface.gettransaksidiskontahun(idcabang, txtTanggal.getText().toString());
        call.enqueue(new Callback<Posttrxdiskontahun>() {
            @Override
            public void onResponse(Call<Posttrxdiskontahun> call, Response<Posttrxdiskontahun>
                    response) {
                List<Trxdiskontahun> trxdiskontahunList = response.body().getTrxdiskontahunList();
                totaldiskon = trxdiskontahunList.get(0).getTotal_diskon();
                jumlahdiskon = trxdiskontahunList.get(0).getJumlah_transaksidiskon();
                //totalDiskon.setText("Rp "+totaldiskon);
                jumlahDiskon.setText(jumlahdiskon);
            }

            @Override
            public void onFailure(Call<Posttrxdiskontahun> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
                Toasty.error(LaporanPendapatanTahunan.this, "Gagal memuat total diskon  " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void gettransaksirefund() {
        ApiInterface mApiInterface = ApiHelper.getClient().create(ApiInterface.class);
        Call<Posttrxrefundtahun> call = mApiInterface.gettransaksirefundtahun(idcabang, txtTanggal.getText().toString());
        call.enqueue(new Callback<Posttrxrefundtahun>() {
            @Override
            public void onResponse(Call<Posttrxrefundtahun> call, Response<Posttrxrefundtahun>
                    response) {
                List<Trxrefundtahun> trxrefundtahunList = response.body().getTrxrefundtahunList();
                totalrefund = trxrefundtahunList.get(0).getTotal_refund();
                if(totalrefund == null){
                    totalRefund.setText("0");
                }
                jumlahrefund = trxrefundtahunList.get(0).getJumlah_transaksirefund();
                totalRefund.setText("Rp "+totalrefund);
                jumlahRefund.setText(jumlahrefund);
            }

            @Override
            public void onFailure(Call<Posttrxrefundtahun> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
                Toasty.error(LaporanPendapatanTahunan.this, "Gagal memuat total refund  " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void gethbptahun() {
        ApiInterface mApiInterface = ApiHelper.getClient().create(ApiInterface.class);
        Call<Posttrxhbptahun> call = mApiInterface.gettransaksihbptahun(idcabang, txtTanggal.getText().toString());
        call.enqueue(new Callback<Posttrxhbptahun>() {
            @Override
            public void onResponse(Call<Posttrxhbptahun> call, Response<Posttrxhbptahun>
                    response) {
                if(response.isSuccessful()){
                    try {
                        List<Trxhbptahun> trxhbptahunList = response.body().getTrxhbptahunList();
                        totalhbp = trxhbptahunList.get(0).getTotal_biayaproduk();
                        int hbp = 0;
                        if(totalhbp.equals("null")){
                            hbp = 0;
                        }else{
                            hbp = Integer.parseInt(totalhbp);
                        }
                        totalHBP.setText("Rp " + totalhbp);
                        int total = 0;
                        String s = (totalTransaksi.getText().toString().substring(3));
                        total = Integer.parseInt(s);
                        int net = total-hbp;
                        totalNett.setText("Rp " + net);
                        Log.d("totaltrx", "total " + total);
                    }catch (NullPointerException e){
                        e.printStackTrace();
                        Toasty.error(LaporanPendapatanTahunan.this, "Tidak ditemukan  ", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toasty.error(LaporanPendapatanTahunan.this, "Gagal  ", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Posttrxhbptahun> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
                Toasty.error(LaporanPendapatanTahunan.this, "Gagal  " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void tampilnettbulanan() {
        ApiInterface mApiInterface = ApiHelper.getClient().create(ApiInterface.class);
        Call<Gettrxtahunan> call = mApiInterface.gettransaksiperbulan(idcabang, txtTanggal.getText().toString());
        call.enqueue(new Callback<Gettrxtahunan>() {
            @Override
            public void onResponse(Call<Gettrxtahunan> call, Response<Gettrxtahunan>
                    response) {
                if(response.isSuccessful()){
                    List<Result> results = response.body().getResultList();
                    List<Report> reports = response.body().getReportList();
                    mAdapter = new NettAdapter(results,reports);
                    mRecyclerView.setAdapter(mAdapter);
                }else{
                    Toasty.error(LaporanPendapatanTahunan.this, "Gagal  ", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Gettrxtahunan> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
                Toasty.error(LaporanPendapatanTahunan.this, "Gagal  " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
