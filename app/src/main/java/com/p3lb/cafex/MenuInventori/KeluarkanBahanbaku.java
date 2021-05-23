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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.p3lb.cafex.R;
import com.p3lb.cafex.model.bahanbaku.Bahanbaku;
import com.p3lb.cafex.model.bahanbaku.PostBahanbaku;
import com.p3lb.cafex.network.ApiHelper;
import com.p3lb.cafex.network.ApiInterface;

import java.util.Calendar;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KeluarkanBahanbaku extends AppCompatActivity {
    EditText jumlahBahanbaku;
    Button btnkeluarbahanbaku, btnTanggalbahanbaku, backkeluarkanbahanbaku;
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_ID = "id";
    private static final String KEY_INVENTORI = "inventori";
    private static final String KEY_BAHANBAKU = "bahanbaku";
    private static final String KEY_JUMLAHINPUTAN = "inputan";
    String idinventori = "";
    String namabahanbaku = "";
    String iddetailinventoriku = "";
    String idcabang = "";
    String inputan = "";
    String stat = "";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keluarkan_bahanbaku);
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        jumlahBahanbaku = (EditText) findViewById(R.id.jumlahBahanbaku);
        btnkeluarbahanbaku = (Button) findViewById(R.id.btnkeluarkanbahanbakumasuk);
        backkeluarkanbahanbaku = (Button) findViewById(R.id.backkeluarkanbahanbaku);
        idcabang = sharedPreferences.getString(KEY_ID, null);
        idinventori = sharedPreferences.getString(KEY_INVENTORI,null);
        namabahanbaku = sharedPreferences.getString(KEY_BAHANBAKU, null);
        inputan = sharedPreferences.getString(KEY_JUMLAHINPUTAN, null);
        backkeluarkanbahanbaku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Intent = new Intent(KeluarkanBahanbaku.this, TampilInventori.class);
                startActivity(Intent);
            }
        });
        btnkeluarbahanbaku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (jumlahBahanbaku.getText().toString().equals("")) {
                    Toasty.normal(KeluarkanBahanbaku.this, "Masukkan jumlah bahanbaku terlebih dahulu", Toast.LENGTH_SHORT).show();
                } else {
                    keluarkanbahanbaku();
                }
            }
        });


    }


    public void keluarkanbahanbaku(){
        ApiInterface mApiInterface = ApiHelper.getClient().create(ApiInterface.class);
        Call<PostBahanbaku> bahanbakuCall = mApiInterface.updatebahanbaku(idcabang, idinventori, namabahanbaku, jumlahBahanbaku.getText().toString());
        bahanbakuCall.enqueue(new Callback<PostBahanbaku>() {
            @Override
            public void onResponse(Call<PostBahanbaku> call, Response<PostBahanbaku> response) {
                if (response.isSuccessful()) {
                    Toasty.success(getApplicationContext(), "Berhasil keluarkan bahanbaku", Toast.LENGTH_SHORT).show();
                    Intent Intent = new Intent(KeluarkanBahanbaku.this, TampilInventori.class);
                    startActivity(Intent);
                } else {
                    Log.d("RETRO", "ON FAIL : " + response.message());
                    Toasty.error(getApplicationContext(), "Gagal keluarkan bahanbaku", Toast.LENGTH_SHORT).show();
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