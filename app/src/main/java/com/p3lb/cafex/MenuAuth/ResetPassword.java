package com.p3lb.cafex.MenuAuth;

import android.content.Context;
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

import com.p3lb.cafex.R;
import com.p3lb.cafex.model.auth.LoginRegisterUsers;
import com.p3lb.cafex.network.ApiHelper;
import com.p3lb.cafex.network.ApiInterface;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPassword extends AppCompatActivity {
    EditText passwordbaru;
    Button btnresetpassword;
    String idcabang = "";
    String username = "";
    String pass = "";
    SharedPreferences sharedPreferences;
    ApiInterface apiInterface = ApiHelper.getClient().create(ApiInterface.class);
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_ID = "id";
    private static final String KEY_PASSWORD = "password";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        passwordbaru = (EditText) findViewById(R.id.passwordbaru);
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        idcabang = sharedPreferences.getString(KEY_ID,null);
        username = sharedPreferences.getString(KEY_USERNAME,null);
        pass = sharedPreferences.getString(KEY_PASSWORD,null);
        btnresetpassword = (Button) findViewById(R.id.btnresetpassword);
        btnresetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetpassword();
            }
        });
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
    }

    private void resetpassword(){
        Call<LoginRegisterUsers> postUsersCall = apiInterface.resetpass(username,idcabang,passwordbaru.getText().toString());
        postUsersCall.enqueue(new Callback<LoginRegisterUsers>() {
            @Override
            public void onResponse(Call<LoginRegisterUsers> call, Response<LoginRegisterUsers> response) {
                if(response.body().getStatus().equals("berhasil")) {
                    SharedPreferences preferences =getSharedPreferences("mypref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();
                    editor.apply();
                    Toasty.success(ResetPassword.this, "Berhasil reset, silahkan login dengan password baru", Toast.LENGTH_SHORT).show();
                    finish();
                    Intent intent = new Intent(ResetPassword.this, LoginKasir.class);
                    startActivity(intent);
                }
                else {
                    Log.d("RETRO", "ON FAIL : " + response.message());
                    Toasty.error(getApplicationContext(), "Verifikasi gagal", Toast.LENGTH_SHORT).show();
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
