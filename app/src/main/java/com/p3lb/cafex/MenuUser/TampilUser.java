package com.p3lb.cafex.MenuUser;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.p3lb.cafex.MenuAuth.Dashboard;
import com.p3lb.cafex.MenuAuth.TampilRegistrasi;
import com.p3lb.cafex.MenuInventori.TampilInventori;
import com.p3lb.cafex.R;
import com.p3lb.cafex.adapter.UserAdapter;
import com.p3lb.cafex.model.auth.GetUsers;
import com.p3lb.cafex.model.auth.Users;
import com.p3lb.cafex.model.user.PostUser;
import com.p3lb.cafex.model.user.User;
import com.p3lb.cafex.network.ApiHelper;
import com.p3lb.cafex.network.ApiInterface;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TampilUser extends AppCompatActivity {
    ApiInterface mApiInterface;
    EditText edtsearchuser;
    Button btnsearchuser;
    Button backlistuser, btnTampilUserRegister;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_ID = "id";
    public static TampilUser ii;
    String idcabang = "";
    List<User> userList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_datauser);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_user);
        btnTampilUserRegister = (Button) findViewById(R.id.btnTampilUserRegister);
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        edtsearchuser = (EditText) findViewById(R.id.edtsearchuser);
        btnsearchuser = (Button) findViewById(R.id.btnsearchuser);
        backlistuser = (Button) findViewById(R.id.backlistuser);
        idcabang = sharedPreferences.getString(KEY_ID,null);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mApiInterface = ApiHelper.getClient().create(ApiInterface.class);
        ii=this;
        refresh();
        backlistuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TampilUser.this, Dashboard.class);
                startActivity(intent);
            }
        });
        btnsearchuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchuser();
            }
        });
        btnTampilUserRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TampilUser.this, TampilRegistrasi.class);
                startActivity(intent);
            }
        });

    }

    public void refresh() {
        ApiInterface mApiInterface = ApiHelper.getClient().create(ApiInterface.class);
        Call<PostUser> call = mApiInterface.showuser(idcabang);
        call.enqueue(new Callback<PostUser>() {
            @Override
            public void onResponse(Call<PostUser> call, Response<PostUser>
                    response) {
                List<User> userList = response.body().getUserList();
                Log.d("Retrofit Get", "Jumlah user: " +
                        String.valueOf(userList.size()));
                mAdapter = new UserAdapter(userList);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<PostUser> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
                Toasty.error(TampilUser.this, "Gagal memuat user  " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void searchuser() {
        ApiInterface mApiInterface = ApiHelper.getClient().create(ApiInterface.class);
        Call<PostUser> call = mApiInterface.searchuser(edtsearchuser.getText().toString(),idcabang);
        call.enqueue(new Callback<PostUser>() {
            @Override
            public void onResponse(Call<PostUser> call, Response<PostUser>
                    response) {
                List<User> userList = response.body().getUserList();
                mAdapter = new UserAdapter(userList);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<PostUser> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
                Toasty.error(TampilUser.this, "Gagal memuat user  " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
