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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.p3lb.cafex.MenuTransaksi.TambahSelectMenu;
import com.p3lb.cafex.MenuTransaksi.TampilCheckoutMenu;
import com.p3lb.cafex.MenuTransaksi.TampilDataMenu;
import com.p3lb.cafex.R;
import com.p3lb.cafex.adapter.CartsAdapter;
import com.p3lb.cafex.model.auth.ConfirmDeleteUsers;
import com.p3lb.cafex.model.auth.LoginRegisterUsers;
import com.p3lb.cafex.model.auth.Users;
import com.p3lb.cafex.model.transaksi.Cart;
import com.p3lb.cafex.model.transaksi.PostPutDelCart;
import com.p3lb.cafex.model.transaksi.PostPutDelTransaksi;
import com.p3lb.cafex.network.ApiHelper;
import com.p3lb.cafex.network.ApiInterface;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailRegistrasi extends AppCompatActivity {
    TextView txtNamauser, txtNoktpuser, txtJabatanuser, txtNohpuser, txtEmailuser;
    Button btnKonfirmasi, btnHapus, backuserdetail;
    String idcabang = "";
    String username = "";
    String jabatan = "";
    String iduser = "";
    ApiInterface mApiInterface;
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_ID = "id";
    public static DetailRegistrasi di;
    List<Users> usersList ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_konfirmasipegawai);
        txtNamauser = (TextView) findViewById(R.id.txtNama);
        txtNoktpuser = (TextView) findViewById(R.id.txtNoktp);
        txtJabatanuser = (TextView) findViewById(R.id.txtJabatanuser);
        txtNohpuser = (TextView) findViewById(R.id.txtNohpuser);
        txtEmailuser = (TextView) findViewById(R.id.txtEmailuser);
        btnKonfirmasi = (Button) findViewById(R.id.btnKonfirm);
        btnHapus = (Button) findViewById(R.id.btnHapus);
        backuserdetail = (Button) findViewById(R.id.backuserdetail);
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        idcabang = sharedPreferences.getString(KEY_ID,null);
        username = sharedPreferences.getString(KEY_USERNAME,null);
        Intent mIntent = getIntent();
       // Log.d("XX", ""+mIntent.getStringExtra("nama_user")+mIntent.getStringExtra("noktp_user"));
        iduser = mIntent.getStringExtra("id_user");
        txtNamauser.setText(mIntent.getStringExtra("nama_user"));
        txtNoktpuser.setText(mIntent.getStringExtra("noktp_user"));
        jabatan = mIntent.getStringExtra("jabatan_user");
        if(jabatan.equals("2")){
            txtJabatanuser.setText("Admin");
        }
        if(jabatan.equals("3")){
            txtJabatanuser.setText("Kasir");
        }
        txtEmailuser.setText(mIntent.getStringExtra("email_user"));
        txtNohpuser.setText(mIntent.getStringExtra("nohp_user"));
        di=this;
        backuserdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailRegistrasi.this, TampilRegistrasi.class);
                startActivity(intent);
            }
        });
        btnKonfirmasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                konfirmasiuser();
            }
        });
        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                batalkankonfirmasi();
            }
        });
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
    }

    void konfirmasiuser(){
        ApiInterface mApiInterface = ApiHelper.getClient().create(ApiInterface.class);
        Call<ConfirmDeleteUsers> postkonfirmuser = mApiInterface.konfirmasiuser(iduser,idcabang,txtNamauser.getText().toString());
        postkonfirmuser.enqueue(new Callback<ConfirmDeleteUsers>() {
            @Override
            public void onResponse(Call<ConfirmDeleteUsers> call, Response<ConfirmDeleteUsers> response) {
                if(response.isSuccessful()) {
                    Log.d("RETRO", "ON SUCCESS : " + response.message());
                    Toasty.success(getApplicationContext(), "Sukses dikonfirmasi", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DetailRegistrasi.this, TampilRegistrasi.class);
                    startActivity(intent);
                }
                else {
                    Log.d("RETRO", "ON FAIL : " + response.message());
                    Toasty.error(getApplicationContext(), "Gagal dikonfirmasi", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ConfirmDeleteUsers> call, Throwable t) {
                Log.d("RETRO", "ON FAILURE : " + t.getMessage());
                Toasty.error(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            }
        });
    }

    void batalkankonfirmasi(){
        ApiInterface mApiInterface = ApiHelper.getClient().create(ApiInterface.class);
        Call<ConfirmDeleteUsers> postkonfirmuser = mApiInterface.deletekonfirmasiuser(iduser,idcabang,txtNamauser.getText().toString());
        postkonfirmuser.enqueue(new Callback<ConfirmDeleteUsers>() {
            @Override
            public void onResponse(Call<ConfirmDeleteUsers> call, Response<ConfirmDeleteUsers> response) {
                if(response.isSuccessful()) {
                    Log.d("RETRO", "ON SUCCESS : " + response.message());
                    Toasty.success(getApplicationContext(), "Sukses batalkan", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DetailRegistrasi.this, TampilRegistrasi.class);
                    startActivity(intent);
                }
                else {
                    Log.d("RETRO", "ON FAIL : " + response.message());
                    Toasty.error(getApplicationContext(), "Gagal batalkan", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ConfirmDeleteUsers> call, Throwable t) {
                Log.d("RETRO", "ON FAILURE : " + t.getMessage());
                Toasty.error(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            }
        });
    }
}
