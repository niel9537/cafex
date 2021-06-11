package com.p3lb.cafex.MenuAuth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.p3lb.cafex.Config;
import com.p3lb.cafex.R;
import com.p3lb.cafex.model.auth.LoginRegisterUsers;
import com.p3lb.cafex.network.ApiHelper;
import com.p3lb.cafex.network.ApiInterface;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LupaPassword extends AppCompatActivity {
    EditText forgetusername, forgetidcabang;
    Button btnkirimemail, backloginforgetpassword;

    SharedPreferences sharedPreferences;
    ApiInterface apiInterface = ApiHelper.getClient().create(ApiInterface.class);
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_ID = "id";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        forgetusername = (EditText) findViewById(R.id.forgetusername);
        forgetidcabang = (EditText) findViewById(R.id.forgetidcabang);
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        btnkirimemail = (Button) findViewById(R.id.btnkirimemail);
        backloginforgetpassword = (Button) findViewById(R.id.backloginforgetpassword);
        backloginforgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LupaPassword.this, LoginKasir.class);
                startActivity(intent);
            }
        });
        btnkirimemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lupapassword();
            }
        });
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
    }

    private void lupapassword(){
        Call<LoginRegisterUsers> postUsersCall = apiInterface.forgotpass(forgetusername.getText().toString(), forgetidcabang.getText().toString());
        postUsersCall.enqueue(new Callback<LoginRegisterUsers>() {
            @Override
            public void onResponse(Call<LoginRegisterUsers> call, Response<LoginRegisterUsers> response) {
                Log.d("response", " "+response.body().getStatus());
                if(response.body().getStatus().equals("berhasil")) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(KEY_USERNAME,forgetusername.getText().toString());
                    editor.putString(KEY_ID,forgetidcabang.getText().toString());
                    editor.apply();
                    Log.d("RETRO", "ON SUCCESS : " + response.message());
                    Toasty.success(LupaPassword.this, "Email verifikasi berhasil dikirim", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LupaPassword.this, VerifikasiPassword.class);
                    startActivity(intent);
                }
                else {
                    Log.d("RETRO", "ON FAIL : " + response.message());
                    Toasty.error(getApplicationContext(), "Email verifikasi gagal dikirim", Toast.LENGTH_SHORT).show();
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
