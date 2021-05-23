package com.p3lb.cafex.MenuDiskon;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.p3lb.cafex.MenuAuth.Dashboard;
import com.p3lb.cafex.MenuTransaksi.TambahSelectMenu;
import com.p3lb.cafex.MenuTransaksi.TampilDataMenu;
import com.p3lb.cafex.R;
import com.p3lb.cafex.model.diskon.Diskon;
import com.p3lb.cafex.model.transaksi.PostPutDelTransaksi;
import com.p3lb.cafex.network.ApiHelper;
import com.p3lb.cafex.network.ApiInterface;

import java.util.Calendar;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahDiskon extends AppCompatActivity {
    EditText namaDiskon, minimalBayar, maksimalDiskon, hargaDiskon;
    RadioButton radioPersen, radioHarga, radioButton;
    Button backcreatediskon;
    RadioGroup radioGroup;
    TextView expDate;
    Button btnTambahDiskon, btnTanggal;
    Calendar c;
    DatePickerDialog dpd;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_diskon);
        namaDiskon = (EditText) findViewById(R.id.namaDiskon);
        minimalBayar = (EditText) findViewById(R.id.minimalBayar);
        maksimalDiskon = (EditText) findViewById(R.id.maksimalDiskon);
        hargaDiskon = (EditText) findViewById(R.id.hargaDiskon);
        radioPersen = (RadioButton) findViewById(R.id.radioPersen);
        radioHarga = (RadioButton) findViewById(R.id.radioHarga);
        expDate = (TextView) findViewById(R.id.expDate);
        btnTambahDiskon = (Button) findViewById(R.id.btnTambahDiskon);
        backcreatediskon = (Button) findViewById(R.id.backcreatediskon);
        btnTanggal = (Button) findViewById(R.id.btnTanggal);
        radioGroup = (RadioGroup) findViewById(R.id.radioGrup);
        backcreatediskon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TambahDiskon.this, TampilDiskon.class);
                startActivity(intent);
            }
        });
        //api hp android untuk datepicker haruslah 24+
        btnTanggal.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                c=Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);

                dpd = new DatePickerDialog(TambahDiskon.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int myear, int mmonth, int mday) {
                        mmonth = mmonth+1;
                        expDate.setText(myear+"-"+mmonth+"-"+mday);
                    }
                }, day, month, year);
                dpd.show();
            }
        });
        btnTambahDiskon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radioId = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(radioId);
                if(radioGroup.getCheckedRadioButtonId() == -1){
                    Toasty.normal(TambahDiskon.this, "Pilih kategori diskon", Toast.LENGTH_SHORT).show();
                }else {
                    String value = radioButton.getText().toString();
                    if (value.equals("$")) {
                        if (expDate.getText().toString().equals("")) {
                            Toasty.normal(TambahDiskon.this, "Pilih tanggal expired terlebih dahulu", Toast.LENGTH_SHORT).show();
                        } else {
                            if(namaDiskon.getText().toString().isEmpty()){
                                Toasty.normal(TambahDiskon.this, "Masukkan nama diskon terlebih dahulu", Toast.LENGTH_SHORT).show();
                            }else {
                                if (minimalBayar.getText().toString().isEmpty()) {
                                    Toasty.normal(TambahDiskon.this, "Masukkan minimal bayar terlebih dahulu", Toast.LENGTH_SHORT).show();
                                } else {
                                    if (maksimalDiskon.getText().toString().isEmpty()) {
                                        Toasty.normal(TambahDiskon.this, "Masukkan maksimal diskon terlebih dahulu", Toast.LENGTH_SHORT).show();
                                    } else {
                                        tambahdiskonnominal();
                                    }

                                }

                            }
                        }

                    } else {
                        if (expDate.getText().toString().equals("")) {
                            Toasty.normal(TambahDiskon.this, "Pilih tanggal expired terlebih dahulu", Toast.LENGTH_SHORT).show();
                        } else {
                            int result = Integer.parseInt(hargaDiskon.getText().toString());
                            if (result >= 100) {
                                Toasty.normal(TambahDiskon.this, "Diskon tidak bisa >= 100 %", Toast.LENGTH_SHORT).show();
                            } else {
                                if(namaDiskon.getText().toString().isEmpty()){
                                    Toasty.normal(TambahDiskon.this, "Masukkan nama diskon terlebih dahulu", Toast.LENGTH_SHORT).show();
                                }else {
                                    if (minimalBayar.getText().toString().isEmpty()) {
                                        Toasty.normal(TambahDiskon.this, "Masukkan minimal bayar terlebih dahulu", Toast.LENGTH_SHORT).show();
                                    } else {
                                        if (maksimalDiskon.getText().toString().isEmpty()) {
                                            Toasty.normal(TambahDiskon.this, "Masukkan maksimal diskon terlebih dahulu", Toast.LENGTH_SHORT).show();
                                        } else {
                                            tambahdiskonpersen();
                                        }
                                    }
                                }
                            }

                        }
                    }
                }
            }
        });
    }

    void tambahdiskonpersen(){
        double hasil = Double.valueOf(hargaDiskon.getText().toString());
        double diskon = (double) (hasil/100);
        ApiInterface mApiInterface = ApiHelper.getClient().create(ApiInterface.class);
        Call<Diskon> calldiskon = mApiInterface.adddiskon(
                namaDiskon.getText().toString(),
                minimalBayar.getText().toString(),
                String.valueOf(diskon),
                "0",
                maksimalDiskon.getText().toString(),
                expDate.getText().toString());

            calldiskon.enqueue(new Callback<Diskon>() {
                @Override
                public void onResponse(Call<Diskon> call, Response<Diskon> response) {
                    if(response.isSuccessful()) {
                        Log.d("RETRO", "ON SUCCESS : " + response.message());
                        Toasty.success(getApplicationContext(), "Sukses menambahkan diskon", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(TambahDiskon.this, Dashboard.class);
                        startActivity(intent);
                    }
                    else {
                        Log.d("RETRO", "ON FAIL : " + response.message());
                        Toasty.error(getApplicationContext(), "Gagal menambahkan diskon", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Diskon> call, Throwable t) {
                    Log.d("RETRO", "ON FAILURE : " + t.getMessage());
                    Toasty.error(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                }
            });

    }

    void tambahdiskonnominal(){
        ApiInterface mApiInterface = ApiHelper.getClient().create(ApiInterface.class);
        Call<Diskon> calldiskon = mApiInterface.adddiskon(
                namaDiskon.getText().toString(),
                minimalBayar.getText().toString(),
                "0",
                hargaDiskon.getText().toString(),
                maksimalDiskon.getText().toString(),
                expDate.getText().toString());

        calldiskon.enqueue(new Callback<Diskon>() {
            @Override
            public void onResponse(Call<Diskon> call, Response<Diskon> response) {
                if(response.isSuccessful()) {
                    Log.d("RETRO", "ON SUCCESS : " + response.message());
                    Toasty.success(getApplicationContext(), "Sukses menambahkan diskon", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(TambahDiskon.this, Dashboard.class);
                    startActivity(intent);
                }
                else {
                    Log.d("RETRO", "ON FAIL : " + response.message());
                    Toasty.error(getApplicationContext(), "Gagal menambahkan diskon", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Diskon> call, Throwable t) {
                Log.d("RETRO", "ON FAILURE : " + t.getMessage());
                Toasty.error(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            }
        });

    }
}
