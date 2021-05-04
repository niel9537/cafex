package com.p3lb.cafex.MenuAuth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.p3lb.cafex.Config;
import com.p3lb.cafex.MenuProduk.TampilDataProduk;
import com.p3lb.cafex.R;
import com.p3lb.cafex.model.auth.LoginRegisterUsers;
import com.p3lb.cafex.network.ApiHelper;
import com.p3lb.cafex.network.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginAdmin extends AppCompatActivity {
    EditText username_login, password_login, cabang_login, jabatan_login;
    TextView klikRegisterKasir, klikUser;
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
        setContentView(R.layout.activity_login_admin);
        username_login = (EditText) findViewById(R.id.username_login);
        password_login = (EditText) findViewById(R.id.password_login);
        cabang_login = (EditText) findViewById(R.id.cabang_login);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        klikRegisterKasir = (TextView) findViewById(R.id.klikRegisterKasir);
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        klikUser = (TextView) findViewById(R.id.klikUser);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username_login.getText().toString().isEmpty() || password_login.getText().toString().isEmpty() || cabang_login.getText().toString().isEmpty()) {
                    Toast.makeText(LoginAdmin.this, "Lengkapi data untuk login", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    LoginAdmin();
                }
            }
        });

        klikUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginAdmin.this, LoginKasir.class);
                startActivity(intent);
            }
        });

        klikRegisterKasir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginAdmin.this, RegistrasiUser.class);
                startActivity(intent);
            }
        });
    }


    private void LoginAdmin(){

        Call<LoginRegisterUsers> postUsersCall = apiInterface.loginUsers(cabang_login.getText().toString(), username_login.getText().toString(), password_login.getText().toString(),2);

        postUsersCall.enqueue(new Callback<LoginRegisterUsers>() {
            @Override
            public void onResponse(Call<LoginRegisterUsers> call, Response<LoginRegisterUsers> response) {
                if(response.isSuccessful()) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(KEY_USERNAME,username_login.getText().toString());
                    editor.putString(KEY_ID,cabang_login.getText().toString());
                    editor.apply();
                    Log.d("RETRO", "ON SUCCESS : " + response.message());
                    Toast.makeText(getApplicationContext(), "Login Berhasil", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginAdmin.this, TampilDataProduk.class);
                    startActivity(intent);

                }
                else {
                    Log.d("RETRO", "ON FAIL : " + response.message());
                    Toast.makeText(getApplicationContext(), "Login Gagal : Pastikan admin telah terdaftar dan sudah dikonfirmasi", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LoginAdmin.this, LoginAdmin.class);
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
