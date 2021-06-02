package com.p3lb.cafex;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;

import com.mazenrashed.printooth.Printooth;
import com.mazenrashed.printooth.data.printable.Printable;
import com.mazenrashed.printooth.data.printable.RawPrintable;
import com.mazenrashed.printooth.data.printable.TextPrintable;
import com.mazenrashed.printooth.data.printer.DefaultPrinter;
import com.mazenrashed.printooth.ui.ScanningActivity;
import com.mazenrashed.printooth.utilities.Printing;
import com.mazenrashed.printooth.utilities.PrintingCallback;
import com.p3lb.cafex.MenuInventori.TampilInventori;
import com.p3lb.cafex.MenuTransaksi.TampilCheckoutMenu;
import com.p3lb.cafex.MenuTransaksi.TampilDataMenu;
import com.p3lb.cafex.adapter.CartsAdapter;
import com.p3lb.cafex.adapter.InventoriAdapter;
import com.p3lb.cafex.model.Struk.PostStruk;
import com.p3lb.cafex.model.Struk.Struk;
import com.p3lb.cafex.model.inventori.Inventori;
import com.p3lb.cafex.model.inventori.PostInventori;
import com.p3lb.cafex.model.transaksi.Cart;
import com.p3lb.cafex.model.transaksi.PostPutDelCart;
import com.p3lb.cafex.network.ApiHelper;
import com.p3lb.cafex.network.ApiInterface;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrintoothActivity extends AppCompatActivity implements PrintingCallback {
    Printing printing;
    Button btn_unpair_pair, btn_print, btnbackprintooth;
    String currentDate;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_ID = "id";
    private static final String KEY_TOTALBAYAR = "totalbayardiskon";
    private static final String KEY_BAYAR = "totalbayar";
    private static final String KEY_PESANAN = "pesanan";
    private static final String KEY_DISKON = "diskon";
    String namadiskon = "";
    String idcabang = "";
    String total = "";
    String totaldiskon ="";
    String username = "";
    String pesanan = "";
    String idtransaksi ="";
    String subtotal ="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Calendar calendar = Calendar.getInstance();
        currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        Printooth.INSTANCE.init(this);
        setContentView(R.layout.activity_bluetooth_printer);
        initView();
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        idcabang = sharedPreferences.getString(KEY_ID,null);
        username = sharedPreferences.getString(KEY_USERNAME,null);
        refresh();
        pesanan = sharedPreferences.getString(KEY_PESANAN, null);

    }
    private void initView(){
        btn_print = (Button) findViewById(R.id.btn_print);
        btn_unpair_pair = (Button) findViewById(R.id.btn_unpair_pair);
        btnbackprintooth = (Button) findViewById(R.id.btnbackprintooth);
        btnbackprintooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PrintoothActivity.this, TampilDataMenu.class);
                startActivity(intent);
            }
        });
        if(printing!=null)
            printing.setPrintingCallback(this);

        btn_unpair_pair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Printooth.INSTANCE.hasPairedPrinter()){
                    Printooth.INSTANCE.removeCurrentPrinter();
                }else{
                    startActivityForResult(new Intent(PrintoothActivity.this, ScanningActivity.class), ScanningActivity.SCANNING_FOR_PRINTER);
                    changePairAndUnpair();
                }
            }
        });

        btn_print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Printooth.INSTANCE.hasPairedPrinter()){
                    startActivityForResult(new Intent(PrintoothActivity.this, ScanningActivity.class), ScanningActivity.SCANNING_FOR_PRINTER);
                }else{
                    printText();
//                    sharedPreferences = getPreferences(Context.MODE_PRIVATE);
//                    editor = sharedPreferences.edit();
//                    editor.remove(KEY_TOTALBAYAR);
//                    editor.remove(KEY_DISKON);
//                    editor.remove(KEY_BAYAR);
//                    editor.commit();
//                    editor.apply();
                    Intent intent = new Intent(PrintoothActivity.this, TampilDataMenu.class);
                    startActivity(intent);

                }
            }
        });

        changePairAndUnpair();

    }

    public void refresh() {
        ApiInterface mApiInterface = ApiHelper.getClient().create(ApiInterface.class);
        Call<PostStruk> call = mApiInterface.getstruk(idcabang, username);
        call.enqueue(new Callback<PostStruk>() {
            @Override
            public void onResponse(Call<PostStruk> call, Response<PostStruk>
                    response) {
                List<Struk> strukList = response.body().getStrukList();
                idtransaksi = strukList.get(0).getId_transaksi();
                total = strukList.get(0).getTotal_bayar();
                namadiskon = strukList.get(0).getNama_diskon();
                subtotal = strukList.get(0).getSubtotal();

            }

            @Override
            public void onFailure(Call<PostStruk> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
                Toasty.error(PrintoothActivity.this, "Gagal memuat struk  " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void changePairAndUnpair() {
        if(Printooth.INSTANCE.hasPairedPrinter()){
            btn_unpair_pair.setText(new StringBuilder("Unpair ").append(Printooth.INSTANCE.getPairedPrinter().getName()).toString());
        }else{
            btn_unpair_pair.setText("Pair");
        }
    }

    private void printText(){
        ArrayList<Printable> printables = new ArrayList<>();
        printables.add(new RawPrintable.Builder(new byte[]{27,100,4}).build());
        printables.add(new TextPrintable.Builder()
                .setText("Tugas Akhir Cafe X by Niel \n Ec Cafe & Coworking Space\n Jl Pakuningratan no 29\n Instagram @ec.cafe ")
                .setLineSpacing(DefaultPrinter.Companion.getLINE_SPACING_30())
                .setAlignment(DefaultPrinter.Companion.getALIGNMENT_CENTER())
                .setEmphasizedMode(DefaultPrinter.Companion.getEMPHASIZED_MODE_BOLD())
                .setUnderlined(DefaultPrinter.Companion.getUNDERLINED_MODE_ON())
                .setNewLinesAfter(1)
                .build());
        //AddText
        printables.add(new TextPrintable.Builder()
                .setText(" Kasir : "+username+"\n POS : POS EC Cafe")
                .setLineSpacing(DefaultPrinter.Companion.getLINE_SPACING_30())
                .setCharacterCode(DefaultPrinter.Companion.getCHARCODE_PC1252())
                .setNewLinesAfter(1)
                .build());
        printables.add(new TextPrintable.Builder()
                .setText("---------------------"+"\n")
                .setLineSpacing(DefaultPrinter.Companion.getLINE_SPACING_30())
                .setAlignment(DefaultPrinter.Companion.getALIGNMENT_CENTER())
                .setEmphasizedMode(DefaultPrinter.Companion.getEMPHASIZED_MODE_BOLD())
                .setUnderlined(DefaultPrinter.Companion.getUNDERLINED_MODE_ON())
                .setNewLinesAfter(1)
                .build());
        printables.add(new TextPrintable.Builder()
                .setText(""+pesanan+"\n")
                .setLineSpacing(DefaultPrinter.Companion.getLINE_SPACING_30())
                .setCharacterCode(DefaultPrinter.Companion.getCHARCODE_PC1252())
                .setNewLinesAfter(1)
                .build());
        printables.add(new TextPrintable.Builder()
                .setText("---------------------"+"\n")
                .setLineSpacing(DefaultPrinter.Companion.getLINE_SPACING_30())
                .setAlignment(DefaultPrinter.Companion.getALIGNMENT_CENTER())
                .setEmphasizedMode(DefaultPrinter.Companion.getEMPHASIZED_MODE_BOLD())
                .setUnderlined(DefaultPrinter.Companion.getUNDERLINED_MODE_ON())
                .setNewLinesAfter(1)
                .build());
        if(namadiskon!=null){
            int totals = Integer.parseInt(total);
            int subtotals = Integer.parseInt(subtotal);
            int discount = subtotals - totals;
            printables.add(new TextPrintable.Builder()
                    .setText(" DISKON :"+namadiskon)
                    .setLineSpacing(DefaultPrinter.Companion.getLINE_SPACING_30())
                    .setEmphasizedMode(DefaultPrinter.Companion.getEMPHASIZED_MODE_BOLD())
                    .setCharacterCode(DefaultPrinter.Companion.getCHARCODE_PC1252())
                    .build());

            printables.add(new TextPrintable.Builder()
                    .setText(" -"+discount+"\n")
                    .setAlignment(DefaultPrinter.Companion.getALIGNMENT_LEFT())
                    .setLineSpacing(DefaultPrinter.Companion.getLINE_SPACING_30())
                    .setEmphasizedMode(DefaultPrinter.Companion.getEMPHASIZED_MODE_BOLD())
                    .setCharacterCode(DefaultPrinter.Companion.getCHARCODE_PC1252())
                    .setNewLinesAfter(1)
                    .build());
            printables.add(new TextPrintable.Builder()
                    .setText(" TOTAL Rp"+total+"\n")
                    .setLineSpacing(DefaultPrinter.Companion.getLINE_SPACING_30())
                    .setEmphasizedMode(DefaultPrinter.Companion.getEMPHASIZED_MODE_BOLD())
                    .setCharacterCode(DefaultPrinter.Companion.getCHARCODE_PC1252())
                    .setNewLinesAfter(1)
                    .build());
        }else {
            printables.add(new TextPrintable.Builder()
                    .setText(" TOTAL Rp" + total + "\n")
                    .setLineSpacing(DefaultPrinter.Companion.getLINE_SPACING_30())
                    .setEmphasizedMode(DefaultPrinter.Companion.getEMPHASIZED_MODE_BOLD())
                    .setCharacterCode(DefaultPrinter.Companion.getCHARCODE_PC1252())
                    .setNewLinesAfter(1)
                    .build());
        }
//        printables.add(new TextPrintable.Builder()
//                .setText(""+pesanan)
//                .setCharacterCode(DefaultPrinter.Companion.getCHARCODE_PC1252())
//                .setNewLinesAfter(1)
//                .build());
        printables.add(new TextPrintable.Builder()
                .setText("WiFI: EC_Cafe Pass: eccafe29 \n Thanks for Coming ! \n "+currentDate+"\n"+"#"+idcabang+"-"+idtransaksi)
                .setLineSpacing(DefaultPrinter.Companion.getLINE_SPACING_30())
                .setAlignment(DefaultPrinter.Companion.getALIGNMENT_CENTER())
                .setEmphasizedMode(DefaultPrinter.Companion.getEMPHASIZED_MODE_BOLD())
                .setUnderlined(DefaultPrinter.Companion.getUNDERLINED_MODE_ON())
                .setNewLinesAfter(1)
                .build());
        Log.d("printables", ""+printables);
        Printooth.INSTANCE.printer().print(printables);
    }
    @Override
    public void connectingWithPrinter(){
        Toasty.success(this, "Connecting to printer", Toast.LENGTH_SHORT).show();

    }
    @Override
    public void connectionFailed(String s){
        Toasty.normal(this, "Failed "+s, Toast.LENGTH_SHORT).show();

    }
    @Override
    public void onError(String s){
        Toasty.normal(this, "Error "+s, Toast.LENGTH_SHORT).show();

    }
    @Override
    public void onMessage(String s){
        Toasty.normal(this, s, Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ScanningActivity.SCANNING_FOR_PRINTER &&
        resultCode == Activity.RESULT_OK){
            initPrinting();
            changePairAndUnpair();
        }

    }
    private void initPrinting(){
        if(!Printooth.INSTANCE.hasPairedPrinter())
            printing = Printooth.INSTANCE.printer();
        if(printing != null){
            printing.setPrintingCallback(this);
        }
    }

    @Override
    public void printingOrderSentSuccessfully() {
        Toasty.normal(this, "Order sent to printer", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
