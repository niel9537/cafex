package com.p3lb.cafex.CRUDProduk;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.p3lb.cafex.Config;
import com.p3lb.cafex.R;
import com.p3lb.cafex.model.ImageModel;
import com.p3lb.cafex.model.ProdukDAO;
import com.p3lb.cafex.network.ApiHelper;
import com.p3lb.cafex.network.ApiInterface;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;

public class TambahDataProduk extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner kategori;
    EditText nama_produk, harga_produk, jumlah_produk;
    ImageView foto_produk;
    Button simpan, upload;
    private int requestCode;
    private int resultCode;
    private Intent data;
    private String mediaPath;
    private String postPath;
    private static final int REQUEST_PICK_PHOTO = Config.REQUEST_PICK_PHOTO;
    private static final int REQUEST_WRITE_PERMISSION = Config.REQUEST_WRITE_PERMISSION;
    ImageModel imageModel;
    //Akses izin ambil gambar dari storage
    public Uri selectedImage;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_WRITE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            //saveImageUpload();
        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        final ImageModel imageModel = new ImageModel(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_dataproduk);
        nama_produk = (EditText) findViewById(R.id.nama_produk);
        harga_produk = (EditText) findViewById(R.id.harga_produk);
        jumlah_produk = (EditText) findViewById(R.id.jumlah_produk);
        foto_produk = (ImageView) findViewById(R.id.foto_produk);
        simpan = (Button) findViewById(R.id.btnSimpan);
        upload = (Button) findViewById(R.id.btnUploadImage);
        kategori = (Spinner) findViewById(R.id.spinKategoriProduk);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.kategori, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        kategori.setAdapter(adapter);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent galleryIntent =  new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
               startActivityForResult(galleryIntent, REQUEST_PICK_PHOTO);
            }
        });

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //requestPermission();
                createToko(imageModel);
            }
        });

    }

    //Akses ambil gambar dari gallery
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == REQUEST_PICK_PHOTO){
                if(data != null){
                    //Ambil Image dari gallery foto
                    selectedImage = data.getData();
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

    void createToko(ImageModel imageModel){

        RequestBody nama = imageModel.requestBody(nama_produk.getText().toString());
        RequestBody kategori_produk = imageModel.requestBody(kategori.getSelectedItem().toString());
        RequestBody jumlah = imageModel.requestBody(jumlah_produk.getText().toString());
        RequestBody harga = imageModel.requestBody(harga_produk.getText().toString());
        MultipartBody.Part file = imageModel.multipartBody(selectedImage);
        ApiInterface apiInterface = ApiHelper.getClient().create(ApiInterface.class);
        Call<ResponseBody> call = apiInterface.addProdukDAO(
                nama,
                kategori_produk,
                jumlah,
                harga,
                file
        );
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    Log.d("sam", String.valueOf(response.body()));

                }
                Log.d("sam", String.valueOf(response.body()));

            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("sam", t.getMessage());
                Toast.makeText(TambahDataProduk.this, "gagal : " + t.getMessage(),Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }
    //Simpan gambar
    /*private void saveImageUpload(){
        final String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        if(mediaPath == null){
            Toast.makeText(getApplicationContext(), "Pilih gambar terlebih dahulu ", Toast.LENGTH_LONG).show();

        }else{
            File imagefile = new File(mediaPath);
            RequestBody reqBody = RequestBody.create(MediaType.parse("multipart/form-file"), imagefile);
            MultipartBody.Part partImage = MultipartBody.Part.createFormData("foto_produk", imagefile.getName(), reqBody);
            ApiInterface apiInterface = ApiHelper.getClient().create(ApiInterface.class);

           *//* Call<ProdukDAO> produkDAOCall = apiInterface.addProdukDAO(
                    RequestBody.create(MediaType.parse("text/plain"), nama_produk.getText().toString()),
                    RequestBody.create(MediaType.parse("text/plain"), kategori.getSelectedItem().toString()),
                    RequestBody.create(MediaType.parse("text/plain"), jumlah_produk.getText().toString()),
                    RequestBody.create(MediaType.parse("text/plain"), harga_produk.getText().toString()),
                    partImage);*//*
            produkDAOCall.enqueue(new Callback<ProdukDAO>() {
                @Override
                public void onResponse(Call<ProdukDAO> call, Response<ProdukDAO> response) {
                    Log.d("sam", String.valueOf(response.body()));
                    Toast.makeText(TambahDataProduk.this,"Berhasil " + response.message(),Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onFailure(Call<ProdukDAO> call, Throwable t) {
                    Log.d("sam", "fail");
                    Toast.makeText(TambahDataProduk.this,"FAILED" + t.getMessage(),Toast.LENGTH_SHORT).show();

                }
            });
        }
    }*/

    //Cek meminta izin
    private void requestPermission(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
        }else{
            Toast.makeText(TambahDataProduk.this,"REQ BERHASIL",Toast.LENGTH_SHORT).show();
            //saveImageUpload();
        }
    }

    //Menu kembali ke home

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    void createData(){
    /*    ApiInterface apiInterface = ApiHelper.getClient().create(ApiInterface.class);
        Call<ProdukDAO> call = apiInterface.addProdukDAO();
*/
    }

}
