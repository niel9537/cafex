package com.p3lb.cafex.MenuInventori;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.p3lb.cafex.MenuDiskon.TambahDiskon;
import com.p3lb.cafex.R;
import com.p3lb.cafex.model.bahanbaku.PostBahanbaku;
import com.p3lb.cafex.model.diskon.Diskon;
import com.p3lb.cafex.network.ApiHelper;
import com.p3lb.cafex.network.ApiInterface;

import java.util.Calendar;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahBahanbaku extends AppCompatActivity {
    EditText jumlahBahanbaku, hargaBahanbaku;
    TextView expBahanbaku;
    Button btntambahbahanbakumasuk, btnTanggalbahanbaku, backmasukanbahanbaku;
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_ID = "id";
    private static final String KEY_INVENTORI = "inventori";
    private static final String KEY_BAHANBAKU = "bahanbaku";
    Calendar c;
    DatePickerDialog dpd;
    String idinventori = "";
    String namabahanbaku = "";
    String idcabang = "";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masukan_bahanbaku);
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        jumlahBahanbaku = (EditText) findViewById(R.id.jumlahBahanbaku);
        hargaBahanbaku = (EditText) findViewById(R.id.hargaBahanbaku);
        expBahanbaku = (TextView) findViewById(R.id.expBahanbaku);
        btntambahbahanbakumasuk = (Button) findViewById(R.id.btntambahbahanbakumasuk);
        btnTanggalbahanbaku = (Button) findViewById(R.id.btnTanggalbahanbaku);
        backmasukanbahanbaku = (Button) findViewById(R.id.backmasukanbahanbaku);
        idcabang = sharedPreferences.getString(KEY_ID, null);
        idinventori = sharedPreferences.getString(KEY_INVENTORI,null);
        namabahanbaku = sharedPreferences.getString(KEY_BAHANBAKU, null);
        backmasukanbahanbaku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TambahBahanbaku.this, TampilInventori.class);
                startActivity(intent);
            }
        });
        //api hp android untuk datepicker haruslah 24+
        btnTanggalbahanbaku.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);

                dpd = new DatePickerDialog(TambahBahanbaku.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int myear, int mmonth, int mday) {
                        mmonth = mmonth + 1;
                        expBahanbaku.setText(myear + "-" + mmonth + "-" + mday);
                    }
                }, day, month, year);
                dpd.show();
            }
        });
        btntambahbahanbakumasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (jumlahBahanbaku.getText().toString().equals("")) {
                    Toasty.normal(TambahBahanbaku.this, "Masukkan jumlah bahanbaku terlebih dahulu", Toast.LENGTH_SHORT).show();
                } else {
                    if (hargaBahanbaku.getText().toString().isEmpty()) {
                        Toasty.normal(TambahBahanbaku.this, "Masukkan harga bahanbaku terlebih dahulu", Toast.LENGTH_SHORT).show();
                    } else {
                        if (expBahanbaku.getText().toString().isEmpty()) {
                            Toasty.normal(TambahBahanbaku.this, "Masukkan tanggal expire bahanbaku terlebih dahulu", Toast.LENGTH_SHORT).show();
                        } else {
                            masukanbahanbaku();
                        }

                    }
                }
            }
        });
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
    }

    void masukanbahanbaku(){
        ApiInterface mApiInterface = ApiHelper.getClient().create(ApiInterface.class);
        Call<PostBahanbaku> bahanbakuCall = mApiInterface.tambahbahanbaku(
                idinventori,
                idcabang,
                namabahanbaku,
                jumlahBahanbaku.getText().toString(),
                hargaBahanbaku.getText().toString(),
                expBahanbaku.getText().toString(),
                "0000-00-00");


            bahanbakuCall.enqueue(new Callback<PostBahanbaku>() {
            @Override
            public void onResponse(Call<PostBahanbaku> call, Response<PostBahanbaku> response) {
                if(response.isSuccessful()) {
                    Log.d("RETRO", "ON SUCCESS : " + response.message());
                    Toasty.success(getApplicationContext(), "Sukses memasukkan bahanbaku", Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove("KEY_INVENTORI"); // will delete key name
                    editor.remove("KEY_BAHANBAKU"); // will delete key email
                    editor.commit(); // commit changes
                    Intent intent = new Intent(TambahBahanbaku.this, TampilInventori.class);
                    startActivity(intent);
                }
                else {
                    Log.d("RETRO", "ON FAIL : " + response.message());
                    Toasty.error(getApplicationContext(), "Gagal memasukan bahanbaku", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PostBahanbaku> call, Throwable t) {
                Log.d("RETRO", "ON FAILURE : " + t.getMessage());
                Toasty.error(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            }
        });
    }


}
