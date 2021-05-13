package com.p3lb.cafex.MenuAuth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.p3lb.cafex.MenuProduk.TampilDataProduk;
import com.p3lb.cafex.MenuTransaksi.TampilCheckoutMenu;
import com.p3lb.cafex.MenuTransaksi.TampilDataMenu;
import com.p3lb.cafex.R;
import com.p3lb.cafex.network.ApiHelper;
import com.p3lb.cafex.network.ApiInterface;

import es.dmoral.toasty.Toasty;

public class Dashboard extends AppCompatActivity {
    CardView cardUser, cardProduk, cardKasir, cardTransaksi, cardLaporan, cardInventori;
    SharedPreferences sharedPreferences;
    String jabatan = "";
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_JABATAN = "jabatan";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_home);
        cardUser = (CardView) findViewById(R.id.cardUser);
        cardProduk = (CardView) findViewById(R.id.cardProduk);
        cardKasir = (CardView) findViewById(R.id.cardKasir);
        cardTransaksi = (CardView) findViewById(R.id.cardTransaksi);
        cardLaporan = (CardView) findViewById(R.id.cardLaporan);
        cardInventori = (CardView) findViewById(R.id.cardInventori);
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        jabatan = sharedPreferences.getString(KEY_JABATAN,null);
        cardProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, TampilDataProduk.class);
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
                        Intent intent = new Intent(Dashboard.this, TampilRegistrasi.class);
                        startActivity(intent);
                    }else{
                        Toasty.normal(Dashboard.this, "Akses menu hanya untuk owner", Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }
}
