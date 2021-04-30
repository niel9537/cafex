package com.p3lb.cafex.MenuAuth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class RegistrasiUser extends AppCompatActivity {
    EditText nama_user, noktp_user, password_user, id_cabang, nohp_user, email_user;
    Spinner spinnerJabatanUser;
    Button btnRegister;
    ApiInterface apiInterface = ApiHelper.getClient().create(ApiInterface.class);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);
        nama_user = (EditText) findViewById(R.id.nama_user);
        email_user = (EditText) findViewById(R.id.email_user);
        nohp_user = (EditText) findViewById(R.id.nohp_user);
        noktp_user = (EditText) findViewById(R.id.noktp_user);
        password_user = (EditText) findViewById(R.id.password_user);

        id_cabang = (EditText) findViewById(R.id.id_cabang);
        spinnerJabatanUser = (Spinner) findViewById(R.id.spinnerJabatanUser);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.jabatan, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerJabatanUser.setAdapter(adapter);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nama_user.getText().toString().isEmpty() ||
                        password_user.getText().toString().isEmpty() ||
                        id_cabang.getText().toString().isEmpty() ||
                        email_user.getText().toString().isEmpty() ||
                        nohp_user.getText().toString().isEmpty() ||
                        noktp_user.getText().toString().isEmpty() ||
                        spinnerJabatanUser.getSelectedItem().toString().isEmpty())
                {
                        Toast.makeText(RegistrasiUser.this, "Lengkapi data untuk registrasi", Toast.LENGTH_SHORT).show();
                        return;
                }else{
                    if(spinnerJabatanUser.getSelectedItem().toString().equals("Kasir")) {
                        RegistrasiUserKasir();
                    }else if(spinnerJabatanUser.getSelectedItem().toString().equals("Admin")){
                        RegistrasiUserAdmin();
                    }
                }
            }
        });

    }

    void RegistrasiUserKasir(){
        Call<LoginRegisterUsers> postUsersCall = apiInterface.regisUser(
                id_cabang.getText().toString(),
                nama_user.getText().toString(),
                nohp_user.getText().toString(),
                noktp_user.getText().toString(),
                email_user.getText().toString(),
                password_user.getText().toString(),
                3);

        postUsersCall.enqueue(new Callback<LoginRegisterUsers>() {
            @Override
            public void onResponse(Call<LoginRegisterUsers> call, Response<LoginRegisterUsers> response) {
                if(response.isSuccessful()) {
                    Log.d("RETRO", "ON SUCCESS : " + response.message());
                    Toast.makeText(getApplicationContext(), "Registrasi Kasir Berhasil", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegistrasiUser.this, LoginKasir.class);
                    startActivity(intent);

                }
                else {
                    Log.d("RETRO", "ON FAIL : " + response.message());
                    Toast.makeText(getApplicationContext(), "Registrasi Gagal : Pastikan mengisi data diri dengan benar", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(RegistrasiUser.this, RegistrasiUser.class);
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
    void RegistrasiUserAdmin(){
        Call<LoginRegisterUsers> postUsersCall = apiInterface.regisUser(
                id_cabang.getText().toString(),
                nama_user.getText().toString(),
                nohp_user.getText().toString(),
                noktp_user.getText().toString(),
                email_user.getText().toString(),
                password_user.getText().toString(),
                2);

        postUsersCall.enqueue(new Callback<LoginRegisterUsers>() {
            @Override
            public void onResponse(Call<LoginRegisterUsers> call, Response<LoginRegisterUsers> response) {
                if(response.isSuccessful()) {
                    Log.d("RETRO", "ON SUCCESS : " + response.message());
                    Toast.makeText(getApplicationContext(), "Registrasi Admin Berhasil", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegistrasiUser.this, LoginKasir.class);
                    startActivity(intent);

                }
                else {
                    Log.d("RETRO", "ON FAIL : " + response.message());
                    Toast.makeText(getApplicationContext(), "Registrasi Gagal : Pastikan mengisi data diri dengan benar", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(RegistrasiUser.this, RegistrasiUser.class);
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
