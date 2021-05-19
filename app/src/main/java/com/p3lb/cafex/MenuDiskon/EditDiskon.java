package com.p3lb.cafex.MenuDiskon;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.p3lb.cafex.Config;
import com.p3lb.cafex.MenuAuth.Dashboard;
import com.p3lb.cafex.MenuProduk.EditDataProduk;
import com.p3lb.cafex.MenuProduk.TampilDataProduk;
import com.p3lb.cafex.R;
import com.p3lb.cafex.model.diskon.Diskon;
import com.p3lb.cafex.model.produk.PostPutDelProducts;
import com.p3lb.cafex.network.ApiHelper;
import com.p3lb.cafex.network.ApiInterface;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditDiskon extends AppCompatActivity {

    EditText namaDiskon, minimalBayar, maksimalDiskon, hargaDiskon;
    RadioButton radioPersen, radioHarga, radioButton;
    RadioGroup radioGroup;
    TextView expDate;
    Button btnTambahDiskon, btnTanggal;
    Calendar c;
    DatePickerDialog dpd;
    String ID;
    ApiInterface mApiInterface;

    private final int ALERT_DIALOG_CLOSE = Config.ALERT_DIALOG_CLOSE;
    private final int ALERT_DIALOG_DELETE = Config.ALERT_DIALOG_DELETE;
    private static final String UPDATE_FLAG = Config.UPDATE_FLAG;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_diskon);

        //Inisialisasi komponen form
        namaDiskon = (EditText) findViewById(R.id.namaDiskon);
        minimalBayar = (EditText) findViewById(R.id.minimalBayar);
        maksimalDiskon = (EditText) findViewById(R.id.maksimalDiskon);
        expDate = (TextView) findViewById(R.id.expDate);
        hargaDiskon = (EditText) findViewById(R.id.hargaDiskon);
        radioPersen = (RadioButton) findViewById(R.id.radioPersen);
        btnTambahDiskon = (Button) findViewById(R.id.btnTambahDiskon) ;
        btnTanggal = (Button) findViewById(R.id.btnTanggal);
        radioGroup = (RadioGroup) findViewById(R.id.radioGrup);
        //Inisialisasi intent ke komponen form
        Intent mIntent = getIntent();
        ID = mIntent.getStringExtra("id_diskon");
        namaDiskon.setText(mIntent.getStringExtra("nama_diskon"));
        minimalBayar.setText(mIntent.getStringExtra("min_bayar"));
        maksimalDiskon.setText(mIntent.getStringExtra("max_diskon"));
        expDate.setText(mIntent.getStringExtra("exp_diskon"));
        // Definisi API
        mApiInterface = ApiHelper.getClient().create(ApiInterface.class);

        //api hp android untuk datepicker haruslah 24+
        btnTanggal.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                c=Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);

                dpd = new DatePickerDialog(EditDiskon.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int myear, int mmonth, int mday) {
                        mmonth = mmonth+1;
                        expDate.setText(myear+"-"+mmonth+"-"+mday);
                    }
                }, day, month, year);
                dpd.show();
            }
        });
        // Fungsi Tombol Update
        btnTambahDiskon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radioId = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(radioId);
                if(radioGroup.getCheckedRadioButtonId() == -1){
                    Toasty.normal(EditDiskon.this, "Pilih kategori diskon", Toast.LENGTH_SHORT).show();
                }else {
                    String value = radioButton.getText().toString();
                    if (value.equals("$")) {
                        if (expDate.getText().toString().equals("")) {
                            Toasty.normal(EditDiskon.this, "Pilih tanggal expired terlebih dahulu", Toast.LENGTH_SHORT).show();
                        } else {
                            if(namaDiskon.getText().toString().isEmpty()){
                                Toasty.normal(EditDiskon.this, "Masukkan nama diskon terlebih dahulu", Toast.LENGTH_SHORT).show();
                            }else {
                                if (minimalBayar.getText().toString().isEmpty()) {
                                    Toasty.normal(EditDiskon.this, "Masukkan minimal bayar terlebih dahulu", Toast.LENGTH_SHORT).show();
                                } else {
                                    if (maksimalDiskon.getText().toString().isEmpty()) {
                                        Toasty.normal(EditDiskon.this, "Masukkan maksimal diskon terlebih dahulu", Toast.LENGTH_SHORT).show();
                                    } else {
                                        updateDiskonNominal();
                                    }

                                }

                            }
                        }

                    } else {
                        if (expDate.getText().toString().equals("")) {
                            Toasty.normal(EditDiskon.this, "Pilih tanggal expired terlebih dahulu", Toast.LENGTH_SHORT).show();
                        } else {
                            int result = Integer.parseInt(hargaDiskon.getText().toString());
                            if (result >= 100) {
                                Toasty.normal(EditDiskon.this, "Diskon tidak bisa >= 100 %", Toast.LENGTH_SHORT).show();
                            } else {
                                if(namaDiskon.getText().toString().isEmpty()){
                                    Toasty.normal(EditDiskon.this, "Masukkan nama diskon terlebih dahulu", Toast.LENGTH_SHORT).show();
                                }else {
                                    if (minimalBayar.getText().toString().isEmpty()) {
                                        Toasty.normal(EditDiskon.this, "Masukkan minimal bayar terlebih dahulu", Toast.LENGTH_SHORT).show();
                                    } else {
                                        if (maksimalDiskon.getText().toString().isEmpty()) {
                                            Toasty.normal(EditDiskon.this, "Masukkan maksimal diskon terlebih dahulu", Toast.LENGTH_SHORT).show();
                                        } else {
                                            updateDiskonPersen();
                                        }
                                    }
                                }
                            }

                        }
                    }
                }

            }
        });
    }


    private void updateDiskonPersen() {
        double hasil = Double.valueOf(hargaDiskon.getText().toString());
        double diskon = (double) (hasil/100);
        ApiInterface mApiInterface = ApiHelper.getClient().create(ApiInterface.class);
        Call<Diskon> calldiskon = mApiInterface.updatediskon(
                ID,
                namaDiskon.getText().toString(),
                minimalBayar.getText().toString(),
                String.valueOf(diskon),
                "0",
                maksimalDiskon.getText().toString(),
                expDate.getText().toString());

        calldiskon.enqueue(new Callback<Diskon>() {
            @Override
            public void onResponse(Call<Diskon> call, Response<Diskon> response) {
                if(response.isSuccessful()) {
                    Log.d("RETRO", "ON SUCCESS : " + response.message());
                    Toasty.success(getApplicationContext(), "Sukses update diskon", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditDiskon.this, Dashboard.class);
                    startActivity(intent);
                }
                else {
                    Log.d("RETRO", "ON FAIL : " + response.message());
                    Toasty.error(getApplicationContext(), "Gagal update diskon", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Diskon> call, Throwable t) {
                Log.d("RETRO", "ON FAILURE : " + t.getMessage());
                Toasty.error(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void updateDiskonNominal(){
        ApiInterface mApiInterface = ApiHelper.getClient().create(ApiInterface.class);
        Call<Diskon> calldiskon = mApiInterface.updatediskon(
                ID,
                namaDiskon.getText().toString(),
                minimalBayar.getText().toString(),
                "0",
                hargaDiskon.getText().toString(),
                maksimalDiskon.getText().toString(),
                expDate.getText().toString());

        calldiskon.enqueue(new Callback<Diskon>() {
            @Override
            public void onResponse(Call<Diskon> call, Response<Diskon> response) {
                if(response.isSuccessful()) {
                    Log.d("RETRO", "ON SUCCESS : " + response.message());
                    Toasty.success(getApplicationContext(), "Sukses update diskon", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditDiskon.this, Dashboard.class);
                    startActivity(intent);
                }
                else {
                    Log.d("RETRO", "ON FAIL : " + response.message());
                    Toasty.error(getApplicationContext(), "Gagal update diskon", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Diskon> call, Throwable t) {
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
            dialogMessage = "Apakah anda ingin membatalkan perubahan diskon?";
        } else {
            dialogMessage = "Apakah anda yakin ingin menghapus diskon ini?";
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
                                Call<Diskon> deletediskon = mApiInterface.deletediskon(ID);
                                deletediskon.enqueue(new Callback<Diskon>() {
                                    @Override
                                    public void onResponse(Call<Diskon> call, Response<Diskon> response) {
                                        if(response.isSuccessful()) {
                                            Log.d("RETRO", "ON SUCCESS : " + response.message());
                                            Toasty.success(getApplicationContext(), "Sukses hapus diskon", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(EditDiskon.this, Dashboard.class);
                                            startActivity(intent);
                                        }
                                        else {
                                            Log.d("RETRO", "ON FAIL : " + response.message());
                                            Toasty.error(getApplicationContext(), "Gagal hapus diskon", Toast.LENGTH_SHORT).show();

                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Diskon> call, Throwable t) {
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
