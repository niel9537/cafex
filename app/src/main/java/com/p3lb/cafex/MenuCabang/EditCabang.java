package com.p3lb.cafex.MenuCabang;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.p3lb.cafex.Config;
import com.p3lb.cafex.MenuAuth.Dashboard;
import com.p3lb.cafex.MenuDiskon.EditDiskon;
import com.p3lb.cafex.R;
import com.p3lb.cafex.model.cabang.Cabang;
import com.p3lb.cafex.model.diskon.Diskon;
import com.p3lb.cafex.network.ApiHelper;
import com.p3lb.cafex.network.ApiInterface;

import java.util.Calendar;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditCabang extends AppCompatActivity {
    EditText namaCabang, notelpCabang, alamatCabang;
    Button btnUpdateCabang, backeditcabang;
    String ID;
    ApiInterface mApiInterface;

    private final int ALERT_DIALOG_CLOSE = Config.ALERT_DIALOG_CLOSE;
    private final int ALERT_DIALOG_DELETE = Config.ALERT_DIALOG_DELETE;
    private static final String UPDATE_FLAG = Config.UPDATE_FLAG;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_cabang);

        //Inisialisasi komponen form
        namaCabang = (EditText) findViewById(R.id.namaCabang);
        notelpCabang = (EditText) findViewById(R.id.notelpCabang);
        alamatCabang = (EditText) findViewById(R.id.alamatCabang);
        btnUpdateCabang = (Button) findViewById(R.id.btnUpdateCabang);
        backeditcabang = (Button) findViewById(R.id.backeditcabang);
        //Inisialisasi intent ke komponen form
        Intent mIntent = getIntent();
        ID = mIntent.getStringExtra("id_cabang");
        namaCabang.setText(mIntent.getStringExtra("nama_cabang"));
        notelpCabang.setText(mIntent.getStringExtra("notelp_cabang"));
        alamatCabang.setText(mIntent.getStringExtra("alamat_cabang"));

        // Definisi API
        mApiInterface = ApiHelper.getClient().create(ApiInterface.class);

        backeditcabang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditCabang.this, TampilCabang.class);
                startActivity(intent);
            }
        });
        // Fungsi Tombol Update
        btnUpdateCabang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(namaCabang.getText().toString().isEmpty()){
                    Toasty.normal(EditCabang.this, "Masukan nama cabang terlebih dahulu", Toast.LENGTH_SHORT).show();
                }else{
                    if(notelpCabang.getText().toString().isEmpty()){
                        Toasty.normal(EditCabang.this, "Masukan notelp cabang terlebih dahulu", Toast.LENGTH_SHORT).show();
                    }else{
                        if(alamatCabang.getText().toString().isEmpty()) {
                            Toasty.normal(EditCabang.this, "Masukan alamat cabang terlebih dahulu", Toast.LENGTH_SHORT).show();
                        }else{
                            updatecabang();
                        }
                    }
                }

            }

        });
    }


    private void updatecabang() {
        ApiInterface mApiInterface = ApiHelper.getClient().create(ApiInterface.class);
        Call<Cabang> cabangCall = mApiInterface.updatecabang(
                ID,
                namaCabang.getText().toString(),
                notelpCabang.getText().toString(),
                alamatCabang.getText().toString());

        cabangCall.enqueue(new Callback<Cabang>() {
            @Override
            public void onResponse(Call<Cabang> call, Response<Cabang> response) {
                if(response.isSuccessful()) {
                    Log.d("RETRO", "ON SUCCESS : " + response.message());
                    Toasty.success(getApplicationContext(), "Sukses update cabang", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditCabang.this, Dashboard.class);
                    startActivity(intent);
                }
                else {
                    Log.d("RETRO", "ON FAIL : " + response.message());
                    Toasty.error(getApplicationContext(), "Gagal update cabang", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Cabang> call, Throwable t) {
                Log.d("RETRO", "ON FAILURE : " + t.getMessage());
                Toasty.error(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_form, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_delete:
                showAlertDialog(ALERT_DIALOG_DELETE);
                break;
            case android.R.id.home:
                showAlertDialog(ALERT_DIALOG_CLOSE);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {
        showAlertDialog(ALERT_DIALOG_CLOSE);
    }

    private void showAlertDialog(int type) {
        final boolean isDialogClose = type == ALERT_DIALOG_CLOSE;
        String dialogTitle, dialogMessage;

        if (isDialogClose) {
            dialogTitle = "Batal";
            dialogMessage = "Apakah anda ingin membatalkan perubahan cabang?";
        } else {
            dialogMessage = "Apakah anda yakin ingin menghapus cabang ini?";
            dialogTitle = "Hapus Diskon";
        }

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(dialogTitle);
        alertDialogBuilder
                .setMessage(dialogMessage)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (isDialogClose) {
                            finish();
                        } else {
                            // Kode Hapus
                            if (ID.trim().isEmpty()==false){
                                Call<Cabang> cabangCall = mApiInterface.deletecabang(ID);
                                cabangCall.enqueue(new Callback<Cabang>() {
                                    @Override
                                    public void onResponse(Call<Cabang> call, Response<Cabang> response) {
                                        if(response.isSuccessful()) {
                                            Log.d("RETRO", "ON SUCCESS : " + response.message());
                                            Toasty.success(getApplicationContext(), "Sukses hapus cabang", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(EditCabang.this, Dashboard.class);
                                            startActivity(intent);
                                        }
                                        else {
                                            Log.d("RETRO", "ON FAIL : " + response.message());
                                            Toasty.error(getApplicationContext(), "Gagal hapus cabang", Toast.LENGTH_SHORT).show();

                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Cabang> call, Throwable t) {
                                        Log.d("RETRO", "ON FAILURE : " + t.getMessage());
                                        Toasty.error(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }else{
                                Toasty.error(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
