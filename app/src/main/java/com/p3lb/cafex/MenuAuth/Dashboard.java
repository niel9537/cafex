package com.p3lb.cafex.MenuAuth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.p3lb.cafex.MenuCabang.TambahCabang;
import com.p3lb.cafex.MenuCabang.TampilCabang;
import com.p3lb.cafex.MenuDiskon.TambahDiskon;
import com.p3lb.cafex.MenuDiskon.TampilDiskon;
import com.p3lb.cafex.MenuInventori.TampilInventori;
import com.p3lb.cafex.MenuLaporan.LaporanActivity;
import com.p3lb.cafex.MenuLaporan.LaporanPendapatanBulanan;
import com.p3lb.cafex.MenuProduk.TampilDataProduk;
import com.p3lb.cafex.MenuRefund.TampilRefund;
import com.p3lb.cafex.MenuTransaksi.TampilCheckoutMenu;
import com.p3lb.cafex.MenuTransaksi.TampilDataMenu;
import com.p3lb.cafex.MenuTransaksi.TransaksiHistory;
import com.p3lb.cafex.MenuUser.TampilUser;
import com.p3lb.cafex.R;
import com.p3lb.cafex.network.ApiHelper;
import com.p3lb.cafex.network.ApiInterface;

import es.dmoral.toasty.Toasty;

public class Dashboard extends AppCompatActivity {
    CardView cardUser, cardProduk, cardKasir, cardDiskon, cardLaporan, cardCabang;
    SharedPreferences sharedPreferences;
    Button btnhistory, btnprofile;
    TextView logout;
    String jabatan = "";
    String idcabang = "";
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_JABATAN = "jabatan";
    private static final String KEY_ID = "id";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_home);
        btnhistory = (Button) findViewById(R.id.btnhistory);
        btnprofile = (Button) findViewById(R.id.btnprofile);
        cardUser = (CardView) findViewById(R.id.cardUser);
        cardProduk = (CardView) findViewById(R.id.cardProduk);
        cardKasir = (CardView) findViewById(R.id.cardKasir);
        cardDiskon = (CardView) findViewById(R.id.cardDiskon);
        cardLaporan = (CardView) findViewById(R.id.cardLaporan);
        cardCabang = (CardView) findViewById(R.id.cardCabang);
        logout = (TextView) findViewById(R.id.logout);
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        jabatan = sharedPreferences.getString(KEY_JABATAN,null);
        idcabang = sharedPreferences.getString(KEY_ID,null);
        Log.d("IDCABANG ", idcabang);
        btnhistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, LaporanActivity.class);
                startActivity(intent);
            }
        });

        btnprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, ProfileUser.class);
                startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences =getSharedPreferences("mypref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                Toasty.success(Dashboard.this, "Berhasil Logout", Toast.LENGTH_SHORT).show();
                finish();
                Intent intent = new Intent(Dashboard.this, LoginAdmin.class);
                startActivity(intent);
                Log.d("Session", ""+jabatan);
            }
        });
        cardProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, TampilDataProduk.class);
                startActivity(intent);
            }
        });
        cardLaporan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, TampilInventori.class);
                startActivity(intent);
            }
        });

        cardKasir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, TampilDataMenu.class);
                startActivity(intent);
            }
        });
        Log.d("dash", ""+jabatan);
        cardUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(jabatan.equals("1")) {
                        Intent intent = new Intent(Dashboard.this, TampilUser.class);
                        startActivity(intent);
                    }else{
                        Toasty.normal(Dashboard.this, "Akses menu hanya untuk owner", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        cardDiskon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(jabatan.equals("1")) {
                    Intent intent = new Intent(Dashboard.this, TampilDiskon.class);
                    startActivity(intent);
                }else{
                    Toasty.normal(Dashboard.this, "Akses menu hanya untuk owner", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cardCabang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(jabatan.equals("1")) {
                    Intent intent = new Intent(Dashboard.this, TampilCabang.class);
                    startActivity(intent);
                }else{
                    Toasty.normal(Dashboard.this, "Akses menu hanya untuk owner", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
