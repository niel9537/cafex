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
    Button btnkeluarbahanbaku, btnTanggalbahanbaku;
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_ID = "id";
    private static final String KEY_INVENTORI = "inventori";
    private static final String KEY_BAHANBAKU = "bahanbaku";
    private static final String KEY_JUMLAHINPUTAN = "inputan";
    String idinventori = "";
    String namabahanbaku = "";
    String idcabang = "";
    String inputan = "";
    int inp = 0;
    int x = 0;
    int jumlahkeluaran = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keluarkan_bahanbaku);
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        jumlahBahanbaku = (EditText) findViewById(R.id.jumlahBahanbaku);
        btnkeluarbahanbaku = (Button) findViewById(R.id.btnkeluarkanbahanbakumasuk);
        idcabang = sharedPreferences.getString(KEY_ID, null);
        idinventori = sharedPreferences.getString(KEY_INVENTORI,null);
        namabahanbaku = sharedPreferences.getString(KEY_BAHANBAKU, null);
        inputan = sharedPreferences.getString(KEY_JUMLAHINPUTAN, null);
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
        Call<PostBahanbaku> bahanbakuCall = mApiInterface.ambilbahanbaku(idinventori, idcabang, namabahanbaku);
        bahanbakuCall.enqueue(new Callback<PostBahanbaku>() {
            @Override
            public void onResponse(Call<PostBahanbaku> call, Response<PostBahanbaku> response) {
                if (response.isSuccessful()) {
                    List<Bahanbaku> bahanbakuList = response.body().getBahanbakuList();
                    String iddetailinventori = bahanbakuList.get(0).getIdDetailinventori();
                    String jumlahbahanbaku = bahanbakuList.get(0).getJumlahBahanbaku();
                    jumlahkeluaran = Integer.parseInt(jumlahBahanbaku.getText().toString());
                    int jumlahtersedia = Integer.parseInt(jumlahbahanbaku);
                    if (jumlahtersedia > jumlahkeluaran) {
                        //update tapi status masih 1
                        int result = jumlahtersedia - jumlahkeluaran;
                        updatebahanbaku(iddetailinventori, result, "1");
                    } else {
                        jumlahkeluaran = jumlahkeluaran - jumlahtersedia;
                        updatebahanbaku(iddetailinventori, 0, "0");
                        if(jumlahkeluaran>0){
                            keluarkanbahanbaku();
                        }else{
                            Intent intent = new Intent(KeluarkanBahanbaku.this, TampilInventori.class);
                            startActivity(intent);
                        }
                    }

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


    public void updatebahanbaku(String iddetailinventori, int result, String status){
        ApiInterface mApiInterface = ApiHelper.getClient().create(ApiInterface.class);
        Call<PostBahanbaku> bahanbakuCall = mApiInterface.updatebahanbaku(
                iddetailinventori,
                idcabang,
                String.valueOf(result),
                status);
        bahanbakuCall.enqueue(new Callback<PostBahanbaku>() {
            @Override
            public void onResponse(Call<PostBahanbaku> call, Response<PostBahanbaku> response) {
                if(response.isSuccessful()) {
                    Log.d("RETRO", "ON SUCCESS : " + response.message());
                    Toasty.success(getApplicationContext(), "Sukses keluarkan bahanbaku", Toast.LENGTH_SHORT).show();
                    //SharedPreferences.Editor editor = sharedPreferences.edit();
                    //editor.remove("KEY_INVENTORI"); // hapus key inventori
                    //editor.remove("KEY_BAHANBAKU"); // hapus key bahanbaku
                    //editor.commit(); // commit changes

                }
                else {
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

    public void keluarkanbahanbakukecil(){
        ApiInterface mApiInterface = ApiHelper.getClient().create(ApiInterface.class);
        Call<PostBahanbaku> bahanbakuCall = mApiInterface.ambilbahanbaku(
                idinventori,
                idcabang,
                namabahanbaku);
        bahanbakuCall.enqueue(new Callback<PostBahanbaku>() {
            @Override
            public void onResponse(Call<PostBahanbaku> call, Response<PostBahanbaku> response) {
                if(response.isSuccessful()) {

                    List<Bahanbaku> bahanbakuList = response.body().getBahanbakuList();
                    String iddetailinventori = bahanbakuList.get(0).getIdDetailinventori();
                    String jumlahbahanbaku = bahanbakuList.get(0).getJumlahBahanbaku();
                    int jumlahtersedia = Integer.parseInt(jumlahbahanbaku);
                    int jumlahinputan = Integer.parseInt(inputan);
                    int jumlahkeluaran = jumlahinputan - jumlahtersedia;
                    updatebahanbaku(iddetailinventori, 0, "0");
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(KEY_JUMLAHINPUTAN,String.valueOf(jumlahkeluaran));
                    editor.apply();
//                    Log.d("RETRO", "ON SUCCESS : " + response.message());
//                    Toasty.success(getApplicationContext(), "Sukses memasukkan bahanbaku", Toast.LENGTH_SHORT).show();
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.remove("KEY_INVENTORI"); // will delete key name
//                    editor.remove("KEY_BAHANBAKU"); // will delete key email
//                    editor.commit(); // commit changes
//                    Intent intent = new Intent(KeluarkanBahanbaku.this, TampilInventori.class);
//                    startActivity(intent);
                }
                else {
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