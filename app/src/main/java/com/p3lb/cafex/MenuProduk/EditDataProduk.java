package com.p3lb.cafex.MenuProduk;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.p3lb.cafex.Config;
import com.p3lb.cafex.R;
import com.p3lb.cafex.model.produk.PostPutDelProducts;
import com.p3lb.cafex.network.ApiHelper;
import com.p3lb.cafex.network.ApiInterface;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditDataProduk extends AppCompatActivity {

    EditText nama_produk, harga_produk, biaya_produk;
    Spinner kategori_produk;
    Button btnSimpan, btnGallery;
    ImageView foto_produk;
    String ID;
    private String mediaPath;
    private String postPath;
    ApiInterface mApiInterface;
    private static final int REQUEST_PICK_PHOTO = Config.REQUEST_PICK_PHOTO;
    private static final int REQUEST_WRITE_PERMISSION = Config.REQUEST_WRITE_PERMISSION;

    private final int ALERT_DIALOG_CLOSE = Config.ALERT_DIALOG_CLOSE;
    private final int ALERT_DIALOG_DELETE = Config.ALERT_DIALOG_DELETE;
    private static final String UPDATE_FLAG = Config.UPDATE_FLAG;

    // Akses Izin Ambil Gambar dari Storage

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_WRITE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            updateImageUpload();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_dataproduk);

        //Inisialisasi komponen form
        nama_produk = (EditText) findViewById(R.id.namaProduk);
        harga_produk = (EditText) findViewById(R.id.hargaProduk);
        biaya_produk = (EditText) findViewById(R.id.biayaProduk);
        kategori_produk = (Spinner) findViewById(R.id.spinnerKategoriProduk);
        foto_produk = (ImageView) findViewById(R.id.fotoProduk);
        btnGallery = (Button) findViewById(R.id.btnGallery);
        btnSimpan = (Button) findViewById(R.id.btnSimpan);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.kategori, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        kategori_produk.setAdapter(adapter);
        //Inisialisasi intent ke komponen form
        Intent mIntent = getIntent();
        ID = mIntent.getStringExtra("id_produk");
        nama_produk.setText(mIntent.getStringExtra("nama_produk"));
        harga_produk.setText(mIntent.getStringExtra("harga_produk"));

        //Input gambar ke imgview
        Glide.with(EditDataProduk.this)
                .load(Config.IMAGES_URL + mIntent.getStringExtra("foto_produk"))
                .apply(new RequestOptions().override(350, 550))
                .into(foto_produk);

        // Definisi API
        mApiInterface = ApiHelper.getClient().create(ApiInterface.class);

        // Fungsi Tombol Pilih Galery
        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, REQUEST_PICK_PHOTO);
            }
        });

        // Fungsi Tombol Update
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateImageUpload();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == REQUEST_PICK_PHOTO){
                if(data!=null){
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    assert cursor != null;
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    mediaPath = cursor.getString(columnIndex);
                    foto_produk.setImageURI(data.getData());
                    cursor.close();

                    postPath = mediaPath;
                }
            }
        }
    }

    private void updateImageUpload() {
        final String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        if (mediaPath== null)
        {
            Toasty.error(getApplicationContext(), "Pilih gambar dulu, baru simpan ...!", Toast.LENGTH_LONG).show();
        }
        else {
            File imagefile = new File(mediaPath);
            RequestBody reqBody = RequestBody.create(MediaType.parse("multipart/form-file"), imagefile);
            MultipartBody.Part partImage = MultipartBody.Part.createFormData("foto_produk", imagefile.getName(), reqBody);

            Call<PostPutDelProducts> postHerosCall = mApiInterface.postUpdateProducts(partImage, RequestBody.create(MediaType.parse("text/plain"), ID), RequestBody.create(MediaType.parse("text/plain"), nama_produk.getText().toString()), RequestBody.create(MediaType.parse("text/plain"), harga_produk.getText().toString()), RequestBody.create(MediaType.parse("text/plain"), biaya_produk.getText().toString()), RequestBody.create(MediaType.parse("text/plain"), kategori_produk.getSelectedItem().toString()), RequestBody.create(MediaType.parse("text/plain"), date), RequestBody.create(MediaType.parse("text/plain"), UPDATE_FLAG));
            postHerosCall.enqueue(new Callback<PostPutDelProducts>() {
                @Override
                public void onResponse(Call<PostPutDelProducts> call, Response<PostPutDelProducts> response) {
                    Log.d("RETRO", "ON SUCCESS : " + response.message());
                    TampilDataProduk.ma.refresh();
                    finish();
                }

                @Override
                public void onFailure(Call<PostPutDelProducts> call, Throwable t) {
                    Log.d("RETRO", "ON FAILURE : " + t.getMessage());
                    //Log.d("RETRO", "ON FAILURE : " + t.getCause());
                    Toasty.error(getApplicationContext(), "Error, image", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
    // Cek Versi Android untuk meminta izin
    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
        } else {
            updateImageUpload();
        }
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
            dialogMessage = "Apakah anda ingin membatalkan perubahan produk?";
        } else {
            dialogMessage = "Apakah anda yakin ingin menghapus produk ini?";
            dialogTitle = "Hapus Produk";
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
                                Call<PostPutDelProducts> deleteProducts = mApiInterface.deleteProducts(ID);
                                deleteProducts.enqueue(new Callback<PostPutDelProducts>() {
                                    @Override
                                    public void onResponse(Call<PostPutDelProducts> call, Response<PostPutDelProducts> response) {
                                        TampilDataProduk.ma.refresh();
                                        finish();
                                    }

                                    @Override
                                    public void onFailure(Call<PostPutDelProducts> call, Throwable t) {
                                        Toasty.success(getApplicationContext(), "Sukses", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }else{
                                Toasty.error(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
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
