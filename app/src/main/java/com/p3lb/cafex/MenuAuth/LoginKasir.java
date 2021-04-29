package com.p3lb.cafex.MenuAuth;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.p3lb.cafex.Config;
import com.p3lb.cafex.MenuProduk.TambahDataProduk;
import com.p3lb.cafex.MenuProduk.TampilDataProduk;
import com.p3lb.cafex.R;
import com.p3lb.cafex.model.auth.LoginRegisterUsers;
import com.p3lb.cafex.model.produk.PostPutDelProducts;
import com.p3lb.cafex.network.ApiHelper;
import com.p3lb.cafex.network.ApiInterface;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginKasir extends AppCompatActivity {
    EditText username_login, password_login, cabang_login, jabatan_login;
    Button btnLogin;

    ApiInterface apiInterface = ApiHelper.getClient().create(ApiInterface.class);
    private static final String Kasir = Config.KASIR;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_kasir);
        username_login = (EditText) findViewById(R.id.username_login);
        password_login = (EditText) findViewById(R.id.password_login);
        cabang_login = (EditText) findViewById(R.id.cabang_login);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // if (username_login.getText().toString().isEmpty() || password_login.getText().toString().isEmpty() || cabang_login.getText().toString().isEmpty()) {
                //    Toast.makeText(LoginKasir.this, "Lengkapi data untuk login", Toast.LENGTH_SHORT).show();
                  //  return;
               // }else{
                    LoginKasir();
              //  }
            }
        });
    }

    //Simpan gambar
    private void LoginKasir(){

            Call<LoginRegisterUsers> postUsersCall = apiInterface.loginUsers(cabang_login.getText().toString(), username_login.getText().toString(), password_login.getText().toString(),3);

            postUsersCall.enqueue(new Callback<LoginRegisterUsers>() {
                @Override
                public void onResponse(Call<LoginRegisterUsers> call, Response<LoginRegisterUsers> response) {
                    if(response.isSuccessful()) {
                        Log.d("RETRO", "ON SUCCESS : " + response.message());
                        Toast.makeText(getApplicationContext(), "Login Berhasil", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginKasir.this, TampilDataProduk.class);
                        startActivity(intent);

                    }
                    else {
                        Log.d("RETRO", "ON FAIL : " + response.message());
                        Toast.makeText(getApplicationContext(), "Login Gagal : Pastikan user telah terdaftar dan sudah dikonfirmasi", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(LoginKasir.this, LoginKasir.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<LoginRegisterUsers> call, Throwable t) {
                    Log.d("RETRO", "ON FAILURE : " + t.getMessage());
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                }
            });
        }
    }





