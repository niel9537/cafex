package com.p3lb.cafex.MenuCabang;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.p3lb.cafex.MenuAuth.Dashboard;
import com.p3lb.cafex.MenuDiskon.TambahDiskon;
import com.p3lb.cafex.R;
import com.p3lb.cafex.model.auth.Users;
import com.p3lb.cafex.model.cabang.Cabang;
import com.p3lb.cafex.model.diskon.Diskon;
import com.p3lb.cafex.network.ApiHelper;
import com.p3lb.cafex.network.ApiInterface;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahCabang extends AppCompatActivity {
    EditText namaCabang, notelpCabang, alamatCabang;
    Button btnTambahCabang, backcreatecabang;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_cabang);
        namaCabang = (EditText) findViewById(R.id.namaCabang);
        notelpCabang = (EditText) findViewById(R.id.notelpCabang);
        alamatCabang = (EditText) findViewById(R.id.alamatCabang);
        btnTambahCabang = (Button) findViewById(R.id.btnTambahCabang);
        backcreatecabang = (Button) findViewById(R.id.backcreatecabang);
        backcreatecabang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TambahCabang.this, TampilCabang.class);
                startActivity(intent);
            }
        });
        btnTambahCabang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(namaCabang.getText().toString().isEmpty()){
                    Toasty.normal(TambahCabang.this, "Masukan nama cabang terlebih dahulu", Toast.LENGTH_SHORT).show();
                }else{
                    if(notelpCabang.getText().toString().isEmpty()){
                        Toasty.normal(TambahCabang.this, "Masukan nomor telepon cabang terlebih dahulu", Toast.LENGTH_SHORT).show();
                    }else{
                        if(alamatCabang.getText().toString().isEmpty()){
                            Toasty.normal(TambahCabang.this, "Masukan alamat cabang terlebih dahulu", Toast.LENGTH_SHORT).show();
                        }else{
                            tambahcabang();
                        }
                    }
                }
            }
        });
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);

    }

    void tambahcabang(){
        ApiInterface mApiInterface = ApiHelper.getClient().create(ApiInterface.class);
        Call<Cabang> cabangCall = mApiInterface.addcabang(
                namaCabang.getText().toString(),
                notelpCabang.getText().toString(),
                alamatCabang.getText().toString());

        cabangCall.enqueue(new Callback<Cabang>() {
            @Override
            public void onResponse(Call<Cabang> call, Response<Cabang> response) {
                if(response.isSuccessful()) {
                    Log.d("RETRO", "ON SUCCESS : " + response.message());
                    Toasty.success(getApplicationContext(), "Sukses menambahkan cabang", Toast.LENGTH_SHORT).show();
                    registerowner();

                }
                else {
                    Log.d("RETRO", "ON FAIL : " + response.message());
                    Toasty.error(getApplicationContext(), "Gagal menambahkan cabang", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Cabang> call, Throwable t) {
                Log.d("RETRO", "ON FAILURE : " + t.getMessage());
                Toasty.error(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            }
        });
    }

    void registerowner(){
        ApiInterface mApiInterface = ApiHelper.getClient().create(ApiInterface.class);
        Call<Users> usersCall = mApiInterface.registerownercabang("owner");

        usersCall.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                if(response.isSuccessful()) {

                    Log.d("RETRO", "ON SUCCESS : " + response.message());
                    Toasty.success(getApplicationContext(), "Sukses menambahkan owner cabang baru", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(TambahCabang.this, TampilCabang.class);
                    startActivity(intent);
                }
                else {
                    Log.d("RETRO", "ON FAIL : " + response.message());
                    Toasty.error(getApplicationContext(), "Gagal menambahkan owner cabang baru", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                Log.d("RETRO", "ON FAILURE : " + t.getMessage());
                Toasty.error(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            }
        });
    }



}
