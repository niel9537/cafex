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

import com.p3lb.cafex.R;
import com.p3lb.cafex.model.auth.LoginRegisterUsers;
import com.p3lb.cafex.network.ApiHelper;
import com.p3lb.cafex.network.ApiInterface;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifikasiPassword extends AppCompatActivity {
    EditText forgetkode;
    Button btnkonfirmasiforget;
    String idcabang = "";
    String username = "";
    SharedPreferences sharedPreferences;
    ApiInterface apiInterface = ApiHelper.getClient().create(ApiInterface.class);
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_ID = "id";
    private static final String KEY_PASSWORD = "password";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifikasi_kode);
        forgetkode = (EditText) findViewById(R.id.forgetkode);
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        idcabang = sharedPreferences.getString(KEY_ID,null);
        username = sharedPreferences.getString(KEY_USERNAME,null);
        btnkonfirmasiforget = (Button) findViewById(R.id.btnkonfirmasiforget);
        btnkonfirmasiforget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifikasikode();
            }
        });
    }

    private void verifikasikode(){
        Call<LoginRegisterUsers> postUsersCall = apiInterface.verifikasikode(username,idcabang,forgetkode.getText().toString());
        postUsersCall.enqueue(new Callback<LoginRegisterUsers>() {
            @Override
            public void onResponse(Call<LoginRegisterUsers> call, Response<LoginRegisterUsers> response) {
                if(response.body().getStatus().equals("berhasil")) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(KEY_PASSWORD,forgetkode.getText().toString());
                    editor.apply();
                    Log.d("RETRO", "ON SUCCESS : " + response.message());
                    Toasty.success(getApplicationContext(), "Verifikasi berhasil", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(VerifikasiPassword.this, ResetPassword.class);
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
