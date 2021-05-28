package com.p3lb.cafex.MenuLaporan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.p3lb.cafex.MenuAuth.Dashboard;
import com.p3lb.cafex.MenuAuth.ProfileUser;
import com.p3lb.cafex.R;

public class LaporanActivity extends AppCompatActivity {
    Button btnprofile, riwayattransaksi, laporantahunan, laporanbulanan, btndashboard;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_laporan);
        btnprofile = (Button) findViewById(R.id.btnprofile);
        riwayattransaksi = (Button) findViewById(R.id.riwayattransaksi);
        laporantahunan = (Button) findViewById(R.id.laporantahunan);
        laporanbulanan = (Button) findViewById(R.id.laporanbulanan);
        btndashboard = (Button) findViewById(R.id.btndashboard);

        btnprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LaporanActivity.this, ProfileUser.class);
                startActivity(intent);
            }
        });

        btndashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LaporanActivity.this, Dashboard.class);
                startActivity(intent);
            }
        });

        laporanbulanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LaporanActivity.this, LaporanPendapatanBulanan.class);
                startActivity(intent);
            }
        });

        laporantahunan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LaporanActivity.this, LaporanPendapatanTahunan.class);
                startActivity(intent);
            }
        });

    }
}
