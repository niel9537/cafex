package com.p3lb.cafex.MenuAuth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.p3lb.cafex.MenuCabang.TambahCabang;
import com.p3lb.cafex.MenuCabang.TampilCabang;
import com.p3lb.cafex.MenuLaporan.LaporanActivity;
import com.p3lb.cafex.MenuTransaksi.TransaksiHistory;
import com.p3lb.cafex.R;
import com.p3lb.cafex.adapter.CabangAdapter;
import com.p3lb.cafex.model.auth.LoginRegisterUsers;
import com.p3lb.cafex.model.auth.Users;
import com.p3lb.cafex.model.cabang.Cabang;
import com.p3lb.cafex.model.cabang.PostCabang;
import com.p3lb.cafex.model.user.PostUser;
import com.p3lb.cafex.model.user.User;
import com.p3lb.cafex.network.ApiHelper;
import com.p3lb.cafex.network.ApiInterface;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileUser extends AppCompatActivity {
    ApiInterface mApiInterface;
    EditText updatenamauser, updatenohpuser,updatenoktpuser, updateemailuser;
    TextView updatejabatanuser, btnupdateprofileuser;
    Button btnhistory, btndashboard, btnprofile;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_ID = "id";
    private static final String KEY_IDUSER = "iduser";
    public static ProfileUser zzz;
    String idcabang = "";
    String namauser = "";
    String usernameku = "";
    String jabatanku = "";
    List<Users> usersList;
    String iduserku = "";
    String iduser = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_user);
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        updatenamauser = (EditText) findViewById(R.id.updatenamauser);
        updatenohpuser = (EditText) findViewById(R.id.updatenohpuser);
        updateemailuser = (EditText) findViewById(R.id.updateemailuser);
        updatenoktpuser = (EditText) findViewById(R.id.updatenoktpuser);
        updatejabatanuser = (TextView) findViewById(R.id.updatejabatanuser);
        btnupdateprofileuser = (TextView) findViewById(R.id.btnupdateprofileuser);
        btnhistory = (Button) findViewById(R.id.btnhistory);
        btndashboard = (Button) findViewById(R.id.btndashboard);
        btnprofile = (Button) findViewById(R.id.btnprofile);
        idcabang = sharedPreferences.getString(KEY_ID,null);
        namauser = sharedPreferences.getString(KEY_USERNAME,null);
        Log.d("idcabang",""+idcabang);
        Log.d("namauser",""+namauser);
        iduser = sharedPreferences.getString(KEY_IDUSER,null);
        mApiInterface = ApiHelper.getClient().create(ApiInterface.class);
        //zzz=this;

        refresh();
        btnprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
            }
        });
        btnupdateprofileuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(updatenamauser.getText().toString().isEmpty()){
                    Toasty.normal(ProfileUser.this, "Nama user harus diisi terlebih dahulu", Toast.LENGTH_SHORT).show();
                }else{
                    if(updateemailuser.getText().toString().isEmpty()){
                        Toasty.normal(ProfileUser.this, "Email user harus diisi terlebih dahulu", Toast.LENGTH_SHORT).show();
                    }else{
                        if(updatenohpuser.getText().toString().isEmpty()){
                            Toasty.normal(ProfileUser.this, "Nomor handphone user harus diisi terlebih dahulu", Toast.LENGTH_SHORT).show();
                        }else{
                            if(updatenoktpuser.getText().toString().isEmpty()){
                                Toasty.normal(ProfileUser.this, "Nomor ktp user harus diisi terlebih dahulu", Toast.LENGTH_SHORT).show();
                            }else{
                                updateprofile();
                            }
                        }
                    }
                }

            }
        });
        btnhistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileUser.this, LaporanActivity.class);
                startActivity(intent);
            }
        });
        btndashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileUser.this, Dashboard.class);
                startActivity(intent);
            }
        });
    }

    public void refresh() {
        ApiInterface mApiInterface = ApiHelper.getClient().create(ApiInterface.class);
        Call<PostUser> call = mApiInterface.profileuser(idcabang,namauser);
        call.enqueue(new Callback<PostUser>() {
            @Override
            public void onResponse(Call<PostUser> call, Response<PostUser>
                    response) {
                if(response.isSuccessful()){
                    Toasty.success(ProfileUser.this, "Berhasil memuat profile user  ", Toast.LENGTH_SHORT).show();
                    List<User> userList = response.body().getUserList();
                    iduserku = userList.get(0).getIdUser();
                    Log.e("ID", ""+iduserku);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(KEY_IDUSER,iduserku);
                    editor.apply();
                    usernameku = userList.get(0).getNamaUser();
                    jabatanku = userList.get(0).getJabatanUser();
                    updatenamauser.setText(userList.get(0).getNamaUser());
                    updateemailuser.setText(userList.get(0).getEmailUser());
                    updatenohpuser.setText(userList.get(0).getNohpUser());
                    updatenoktpuser.setText(userList.get(0).getNoktpUser());
                    if(userList.get(0).getJabatanUser().equals("3")){
                        updatejabatanuser.setText("Kasir");
                    }else if(userList.get(0).getJabatanUser().equals("2")){
                        updatejabatanuser.setText("Admin");
                    }else{
                        updatejabatanuser.setText("Owner / SuperAdmin");
                    }
                }else{
                    Toasty.error(ProfileUser.this, "Gagal memuat profile user  ", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<PostUser> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
                Toasty.error(ProfileUser.this, "Gagal memuat cabang  " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void updateprofile() {
        Log.e("ID", ""+iduserku);
        ApiInterface mApiInterface = ApiHelper.getClient().create(ApiInterface.class);
        Call<LoginRegisterUsers> call = mApiInterface.updateprofile(
                iduserku,
                idcabang,
                updatenamauser.getText().toString(),
                updatenohpuser.getText().toString(),
                updatenoktpuser.getText().toString(),
                updateemailuser.getText().toString(),
                jabatanku);
        call.enqueue(new Callback<LoginRegisterUsers>() {
            @Override
            public void onResponse(Call<LoginRegisterUsers> call, Response<LoginRegisterUsers>
                    response) {
                if(response.isSuccessful()){
                    Toasty.success(ProfileUser.this, "Berhasil update profile user  ", Toast.LENGTH_SHORT).show();
                    SharedPreferences preferences =getSharedPreferences("mypref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();
                    editor.apply();
                    finish();
                    Toasty.success(ProfileUser.this, "Silahkan login kembali", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ProfileUser.this, LoginAdmin.class);
                    startActivity(intent);
                }else{
                    Toasty.error(ProfileUser.this, "Gagal update profile user  ", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<LoginRegisterUsers> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
                Toasty.error(ProfileUser.this, "Gagal memuat cabang  " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
