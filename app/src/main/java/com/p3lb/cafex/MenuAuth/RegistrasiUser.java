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
import com.p3lb.cafex.MenuTransaksi.TampilCheckoutMenu;
import com.p3lb.cafex.R;
import com.p3lb.cafex.model.auth.LoginRegisterUsers;
import com.p3lb.cafex.model.cabang.Cabang;
import com.p3lb.cafex.model.cabang.PostCabang;
import com.p3lb.cafex.model.diskon.Diskon;
import com.p3lb.cafex.model.diskon.PostDiskon;
import com.p3lb.cafex.network.ApiHelper;
import com.p3lb.cafex.network.ApiInterface;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrasiUser extends AppCompatActivity {
    EditText nama_user, noktp_user, password_user, id_cabang, nohp_user, email_user;
    Spinner spinnerJabatanUser, spinnerCabang;
    Button btnRegister, backregistrasilogin;
    ApiInterface apiInterface = ApiHelper.getClient().create(ApiInterface.class);
    List<Cabang> cabangList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);
        nama_user = (EditText) findViewById(R.id.nama_user);
        email_user = (EditText) findViewById(R.id.email_user);
        nohp_user = (EditText) findViewById(R.id.nohp_user);
        noktp_user = (EditText) findViewById(R.id.noktp_user);
        password_user = (EditText) findViewById(R.id.password_user);
        spinnerCabang = (Spinner) findViewById(R.id.spinnerCabang);
        spinnerJabatanUser = (Spinner) findViewById(R.id.spinnerJabatanUser);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        backregistrasilogin = (Button) findViewById(R.id.backregistrasilogin);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.jabatan, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerJabatanUser.setAdapter(adapter);
        listcabang();

        String email = email_user.getText().toString().trim();

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        // onClick of button perform this simplest code.

        backregistrasilogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrasiUser.this, LoginKasir.class);
                startActivity(intent);
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nama_user.getText().toString().isEmpty() ||
                        password_user.getText().toString().isEmpty() ||
                        spinnerJabatanUser.getSelectedItem().toString().isEmpty() ||
                        email_user.getText().toString().isEmpty() ||
                        nohp_user.getText().toString().isEmpty() ||
                        noktp_user.getText().toString().isEmpty() ||
                        spinnerJabatanUser.getSelectedItem().toString().isEmpty())
                {
                        Toasty.error(RegistrasiUser.this, "Lengkapi data untuk registrasi", Toast.LENGTH_SHORT).show();
                        return;
                }else{
                    if(email_user.getText().toString().trim().matches(emailPattern))
                    {
                        if(spinnerJabatanUser.getSelectedItem().toString().equals("Kasir")) {
                            // RegistrasiUserKasir();
                            getusernamekasir();
                        }else if(spinnerJabatanUser.getSelectedItem().toString().equals("Admin")){
                            // RegistrasiUserAdmin();
                            getusernameadmin();
                        }
                    }
                    else
                    {
                        Toasty.normal(getApplicationContext(),"Email tidak valid", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
    }

    void getusernameadmin(){
        Call<LoginRegisterUsers> postUsersCall = apiInterface.getusername(
                nama_user.getText().toString());

        postUsersCall.enqueue(new Callback<LoginRegisterUsers>() {
            @Override
            public void onResponse(Call<LoginRegisterUsers> call, Response<LoginRegisterUsers> response) {
                if(response.isSuccessful()) {
                    Log.d("RETRO", "ON SUCCESS : " + response.message());
                    //ambilidcabangadmin();
                    RegistrasiUserAdmin();
                }
                else {
                    Log.d("RETRO", "ON FAIL : " + response.message());
                    Toasty.error(getApplicationContext(), "Username sudah ada", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginRegisterUsers> call, Throwable t) {
                Log.d("RETRO", "ON FAILURE : " + t.getMessage());
                Toasty.error(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void getusernamekasir(){
        Call<LoginRegisterUsers> postUsersCall = apiInterface.getusername(
                nama_user.getText().toString());

        postUsersCall.enqueue(new Callback<LoginRegisterUsers>() {
            @Override
            public void onResponse(Call<LoginRegisterUsers> call, Response<LoginRegisterUsers> response) {
                if(response.isSuccessful()) {
                    Log.d("RETRO", "ON SUCCESS : " + response.message());
                    RegistrasiUserKasir();
                    //ambilidcabangkasir();
                }
                else {
                    Log.d("RETRO", "ON FAIL : " + response.message());
                    Toasty.error(getApplicationContext(), "Username sudah ada", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginRegisterUsers> call, Throwable t) {
                Log.d("RETRO", "ON FAILURE : " + t.getMessage());
                Toasty.error(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
    void ambilidcabangkasir(){
        Call<LoginRegisterUsers> postUsersCall = apiInterface.cekidcabang(
                id_cabang.getText().toString());

        postUsersCall.enqueue(new Callback<LoginRegisterUsers>() {
            @Override
            public void onResponse(Call<LoginRegisterUsers> call, Response<LoginRegisterUsers> response) {
                if(response.isSuccessful()) {
                    Log.d("RETRO", "ON SUCCESS : " + response.message());
                    Toasty.success(getApplicationContext(), "ID ditemukan", Toast.LENGTH_SHORT).show();
                    RegistrasiUserKasir();
                }
                else {
                    Log.d("RETRO", "ON FAIL : " + response.message());
                    Toasty.error(getApplicationContext(), "ID cabang tidak ditemukan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginRegisterUsers> call, Throwable t) {
                Log.d("RETRO", "ON FAILURE : " + t.getMessage());
                Toasty.error(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
    void ambilidcabangadmin(){
        Call<LoginRegisterUsers> postUsersCall = apiInterface.cekidcabang(
                id_cabang.getText().toString());

        postUsersCall.enqueue(new Callback<LoginRegisterUsers>() {
            @Override
            public void onResponse(Call<LoginRegisterUsers> call, Response<LoginRegisterUsers> response) {
                if(response.isSuccessful()) {
                    Log.d("RETRO", "ON SUCCESS : " + response.message());
                    Toasty.success(getApplicationContext(), "ID ditemukan", Toast.LENGTH_SHORT).show();
                    RegistrasiUserAdmin();
                }
                else {
                    Log.d("RETRO", "ON FAIL : " + response.message());
                    Toasty.error(getApplicationContext(), "ID cabang tidak ditemukan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginRegisterUsers> call, Throwable t) {
                Log.d("RETRO", "ON FAILURE : " + t.getMessage());
                Toasty.error(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
    void RegistrasiUserKasir(){
        Call<LoginRegisterUsers> postUsersCall = apiInterface.regisUser(
                spinnerCabang.getSelectedItem().toString(),
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
                    Toasty.success(getApplicationContext(), "Registrasi Kasir Berhasil", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegistrasiUser.this, LoginKasir.class);
                    startActivity(intent);
                }
                else {
                    Log.d("RETRO", "ON FAIL : " + response.message());
                    Toasty.error(getApplicationContext(), "Registrasi Gagal : Pastikan mengisi data diri dengan benar", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(RegistrasiUser.this, RegistrasiUser.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<LoginRegisterUsers> call, Throwable t) {
                Log.d("RETRO", "ON FAILURE : " + t.getMessage());
                Toasty.error(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            }
        });
    }
    void RegistrasiUserAdmin(){
        Call<LoginRegisterUsers> postUsersCall = apiInterface.regisUser(
                spinnerCabang.getSelectedItem().toString(),
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
                    Toasty.success(getApplicationContext(), "Registrasi Admin Berhasil", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegistrasiUser.this, LoginKasir.class);
                    startActivity(intent);

                }
                else {
                    Log.d("RETRO", "ON FAIL : " + response.message());
                    Toasty.error(getApplicationContext(), "Registrasi Gagal : Pastikan mengisi data diri dengan benar", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(RegistrasiUser.this, RegistrasiUser.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<LoginRegisterUsers> call, Throwable t) {
                Log.d("RETRO", "ON FAILURE : " + t.getMessage());
                Toasty.error(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            }
        });
    }
    public void listcabang() {
        ApiInterface mApiInterface = ApiHelper.getClient().create(ApiInterface.class);
        Call<PostCabang> call = mApiInterface.daftarcabang("1");
        call.enqueue(new Callback<PostCabang>() {
            @Override
            public void onResponse(Call<PostCabang> call, Response<PostCabang>
                    response) {
                //Storing data di list
                cabangList = response.body().getCabangList();
                Log.d("XCABANG", ""+cabangList);
                //Panggil method list
                showListinSpinner();
            }

            @Override
            public void onFailure(Call<PostCabang> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
                Toasty.error(RegistrasiUser.this, "Gagal memuat cabang  " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void showListinSpinner(){
        //String array to store all the book names
        String[] items = new String[cabangList.size()];

        //Traversing through the whole list to get all the names
        for(int i=0; i<cabangList.size(); i++){
            //Storing names to string array
            items[i] = cabangList.get(i).getNama_cabang();
        }
        //Spinner spinner = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        //setting adapter to spinner
        spinnerCabang.setAdapter(adapter);
        //Creating an array adapter for list view
    }
}
