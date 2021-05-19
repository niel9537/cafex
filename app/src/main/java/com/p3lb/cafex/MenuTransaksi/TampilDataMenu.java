package com.p3lb.cafex.MenuTransaksi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.p3lb.cafex.ExampleDialog;
import com.p3lb.cafex.MenuAuth.Dashboard;
import com.p3lb.cafex.MenuAuth.LoginAdmin;
import com.p3lb.cafex.MenuAuth.LoginKasir;
import com.p3lb.cafex.MenuRefund.TampilRefund;
import com.p3lb.cafex.R;
import com.p3lb.cafex.adapter.MenusAdapter;
import com.p3lb.cafex.adapter.RefundAdapter;
import com.p3lb.cafex.model.produk.GetProducts;
import com.p3lb.cafex.model.produk.Products;
import com.p3lb.cafex.model.refund.PostRefund;
import com.p3lb.cafex.model.refund.Refund;
import com.p3lb.cafex.network.ApiHelper;
import com.p3lb.cafex.network.ApiInterface;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TampilDataMenu extends AppCompatActivity implements ExampleDialog.ExampleDialogListener {
    ApiInterface mApiInterface;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    ImageView textView;
    private RecyclerView.LayoutManager mLayoutManager;
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_ID = "id";
    private static final String KEY_TRX = "trx";
    String nama_user = "";
    String id_username = "";
    TextView placeholderUsername;
    public static TampilDataMenu me;
    private FloatingActionButton  fltKeranjangBelanja, fltRefund;
    List<Products> productsList;
    String idtransaksi = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_menu);
        textView = (ImageView) findViewById(R.id.textView);
        fltKeranjangBelanja = findViewById(R.id.btnKeranjangBelanja);
        fltRefund = findViewById(R.id.btnRefund);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycleView);
       // mLayoutManager = new LinearLayoutManager(this);
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        nama_user = sharedPreferences.getString(KEY_USERNAME,null);
        id_username = sharedPreferences.getString(KEY_ID,null);
        placeholderUsername = (TextView) findViewById(R.id.placeholderUsername);
        placeholderUsername.setText("Selamat datang "+nama_user+"- Cabang "+id_username);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mApiInterface = ApiHelper.getClient().create(ApiInterface.class);
        me=this;

        refresh();
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences =getSharedPreferences("mypref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                Toasty.success(TampilDataMenu.this, "Berhasil Logout", Toast.LENGTH_SHORT).show();
                finish();
                Intent intent = new Intent(TampilDataMenu.this, LoginKasir.class);
                startActivity(intent);
            }
        });
//        fltRefund.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(TampilDataMenu.this, TampilRefund.class);
//                startActivity(intent);
//            }
//        });
        fltRefund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
        fltKeranjangBelanja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TampilDataMenu.this, TampilCheckoutMenu.class);
                startActivity(intent);
            }
        });

    }
    public void openDialog(){
        ExampleDialog exampleDialog = new ExampleDialog();
        exampleDialog.show(getSupportFragmentManager(), "Example Dialog");
    }
    @Override
    public void applyTexts(String edt_idtransaksi) {
        idtransaksi = edt_idtransaksi;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_TRX,idtransaksi);
        editor.apply();
        if(idtransaksi.isEmpty()){
            Toasty.normal(TampilDataMenu.this, "Gagal", Toast.LENGTH_SHORT).show();
        }else{
            Intent intent = new Intent(TampilDataMenu.this, TampilRefund.class);
            startActivity(intent);
        }

    }
    public void refresh() {
        ApiInterface mApiInterface = ApiHelper.getClient().create(ApiInterface.class);
        Call<GetProducts> call = mApiInterface.getProducts();
        call.enqueue(new Callback<GetProducts>() {
            @Override
            public void onResponse(Call<GetProducts> call, Response<GetProducts>
                    response) {
                List<Products> menuList = response.body().getListDataProducts();
                Log.d("Retrofit Get", "Jumlah data Menu: " +
                        String.valueOf(menuList.size()));
                Toasty.success(TampilDataMenu.this, "Jumlah menu " + (menuList.size()), Toast.LENGTH_SHORT).show();
                mAdapter = new MenusAdapter(menuList);
                mRecyclerView.setAdapter(mAdapter);

            }

            @Override
            public void onFailure(Call<GetProducts> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
                Toasty.error(TampilDataMenu.this, "Gagal memuat menu  " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }



}
