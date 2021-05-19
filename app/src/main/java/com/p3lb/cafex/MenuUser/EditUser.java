package com.p3lb.cafex.MenuUser;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.p3lb.cafex.Config;
import com.p3lb.cafex.MenuAuth.Dashboard;
import com.p3lb.cafex.MenuCabang.EditCabang;
import com.p3lb.cafex.R;
import com.p3lb.cafex.model.User.PostUser;
import com.p3lb.cafex.model.cabang.Cabang;
import com.p3lb.cafex.network.ApiHelper;
import com.p3lb.cafex.network.ApiInterface;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditUser extends AppCompatActivity {
    EditText namaUser, nohpUser, noktpUser, emailUser;
    Button btnEditUser;
    String ID;
    ApiInterface mApiInterface;

    private final int ALERT_DIALOG_CLOSE = Config.ALERT_DIALOG_CLOSE;
    private final int ALERT_DIALOG_DELETE = Config.ALERT_DIALOG_DELETE;
    private static final String UPDATE_FLAG = Config.UPDATE_FLAG;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        //Inisialisasi komponen form
        namaUser = (EditText) findViewById(R.id.namaUser);
        nohpUser = (EditText) findViewById(R.id.nohpUser);
        noktpUser = (EditText) findViewById(R.id.noktpUser);
        emailUser = (EditText) findViewById(R.id.emailUser);
        btnEditUser = (Button) findViewById(R.id.btnEditUser) ;

        //Inisialisasi intent ke komponen form
        Intent mIntent = getIntent();
        ID = mIntent.getStringExtra("id_user");
        namaUser.setText(mIntent.getStringExtra("nama_user"));
        nohpUser.setText(mIntent.getStringExtra("nohp_user"));
        noktpUser.setText(mIntent.getStringExtra("noktp_user"));
        emailUser.setText(mIntent.getStringExtra("email_user"));
        // Definisi API
        mApiInterface = ApiHelper.getClient().create(ApiInterface.class);


        // Fungsi Tombol Update
        btnEditUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(namaUser.getText().toString().isEmpty()){
                    Toasty.normal(EditUser.this, "Masukan nama user terlebih dahulu", Toast.LENGTH_SHORT).show();
                }else{
                    if(nohpUser.getText().toString().isEmpty()){
                        Toasty.normal(EditUser.this, "Masukan nohp user terlebih dahulu", Toast.LENGTH_SHORT).show();
                    }else{
                        if(noktpUser.getText().toString().isEmpty()) {
                            Toasty.normal(EditUser.this, "Masukan nomor ktp user terlebih dahulu", Toast.LENGTH_SHORT).show();
                        }else{
                            if(emailUser.getText().toString().isEmpty()) {
                                Toasty.normal(EditUser.this, "Masukan email user terlebih dahulu", Toast.LENGTH_SHORT).show();
                            }else {
                                updateuser();
                            }
                        }
                    }
                }

            }

        });
    }


    private void updateuser() {
        ApiInterface mApiInterface = ApiHelper.getClient().create(ApiInterface.class);
        Call<PostUser> postUserCall = mApiInterface.updateuser(ID, namaUser.getText().toString(), nohpUser.getText().toString(), noktpUser.getText().toString(), emailUser.getText().toString());
        postUserCall.enqueue(new Callback<PostUser>() {
            @Override
            public void onResponse(Call<PostUser> call, Response<PostUser> response) {
                if(response.isSuccessful()) {
                    Log.d("RETRO", "ON SUCCESS : " + response.message());
                    Toasty.success(getApplicationContext(), "Sukses update user", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditUser.this, TampilUser.class);
                    startActivity(intent);
                }
                else {
                    Log.d("RETRO", "ON FAIL : " + response.message());
                    Toasty.error(getApplicationContext(), "Gagal update user", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PostUser> call, Throwable t) {
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
            dialogMessage = "Apakah anda ingin membatalkan perubahan user?";
        } else {
            dialogMessage = "Apakah anda yakin ingin menghapus user ini?";
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
                                Call<PostUser> postUserCall = mApiInterface.deleteuser(ID, namaUser.getText().toString());
                                postUserCall.enqueue(new Callback<PostUser>() {
                                    @Override
                                    public void onResponse(Call<PostUser> call, Response<PostUser> response) {
                                        if(response.isSuccessful()) {
                                            Log.d("RETRO", "ON SUCCESS : " + response.message());
                                            Toasty.success(getApplicationContext(), "Sukses hapus user", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(EditUser.this, TampilUser.class);
                                            startActivity(intent);
                                        }
                                        else {
                                            Log.d("RETRO", "ON FAIL : " + response.message());
                                            Toasty.error(getApplicationContext(), "Gagal hapus user", Toast.LENGTH_SHORT).show();

                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<PostUser> call, Throwable t) {
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
