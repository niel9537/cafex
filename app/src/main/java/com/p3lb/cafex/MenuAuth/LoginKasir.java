package com.p3lb.cafex.MenuAuth;

import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.p3lb.cafex.Config;
import com.p3lb.cafex.MenuProduk.TambahDataProduk;
import com.p3lb.cafex.MenuProduk.TampilDataProduk;
import com.p3lb.cafex.MenuTransaksi.TampilDataMenu;
import com.p3lb.cafex.R;
import com.p3lb.cafex.model.auth.LoginRegisterUsers;
import com.p3lb.cafex.model.produk.PostPutDelProducts;
import com.p3lb.cafex.network.ApiHelper;
import com.p3lb.cafex.network.ApiInterface;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginKasir extends AppCompatActivity {
    EditText username_login, password_login, cabang_login, jabatan_login;
    TextView klikRegisterKasir, klikAdmin;
    Button btnLogin;
    SharedPreferences sharedPreferences;
    ApiInterface apiInterface = ApiHelper.getClient().create(ApiInterface.class);
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_ID = "id";
    private static final String Kasir = Config.KASIR;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_kasir);
        username_login = (EditText) findViewById(R.id.username_login);
        password_login = (EditText) findViewById(R.id.password_login);
        cabang_login = (EditText) findViewById(R.id.cabang_login);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        klikRegisterKasir = (TextView) findViewById(R.id.klikRegisterKasir);
        klikAdmin = (TextView) findViewById(R.id.klikAdmin);
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username_login.getText().toString().isEmpty() || password_login.getText().toString().isEmpty() || cabang_login.getText().toString().isEmpty()) {
                    Toasty.error(LoginKasir.this, "Lengkapi data untuk login", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    LoginKasir();
                }
            }
        });

        klikAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginKasir.this, LoginAdmin.class);
                startActivity(intent);
            }
        });

        klikRegisterKasir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginKasir.this, RegistrasiUser.class);
                startActivity(intent);
            }
        });
    }


    private void LoginKasir(){

            Call<LoginRegisterUsers> postUsersCall = apiInterface.loginUsers(cabang_login.getText().toString(), username_login.getText().toString(), password_login.getText().toString(),3);

            postUsersCall.enqueue(new Callback<LoginRegisterUsers>() {
                @Override
                public void onResponse(Call<LoginRegisterUsers> call, Response<LoginRegisterUsers> response) {
                    if(response.isSuccessful()) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(KEY_USERNAME,username_login.getText().toString());
                        editor.putString(KEY_ID,cabang_login.getText().toString());
                        editor.apply();
                        Log.d("RETRO", "ON SUCCESS : " + response.message());
                        Toasty.success(getApplicationContext(), "Login Berhasil", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginKasir.this, TampilDataMenu.class);
                        startActivity(intent);

                    }
                    else {
                        Log.d("RETRO", "ON FAIL : " + response.message());
                        Toasty.error(getApplicationContext(), "Login Gagal : Pastikan user telah terdaftar dan sudah dikonfirmasi", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginKasir.this, LoginKasir.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<LoginRegisterUsers> call, Throwable t) {
                    Log.d("RETRO", "ON FAILURE : " + t.getMessage());
                    Toasty.error(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }





