package com.p3lb.cafex.MenuAuth;

import android.content.Intent;
import android.os.Bundle;
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
        Toasty.success(getApplicationContext(), "Dashboard", Toast.LENGTH_SHORT).show();
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
        cardUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, TampilRegistrasi.class);
                startActivity(intent);
            }
        });

    }
}
