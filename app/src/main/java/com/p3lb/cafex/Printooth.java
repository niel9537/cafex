//package com.p3lb.cafex;
//
//import android.bluetooth.BluetoothAdapter;
//import android.bluetooth.BluetoothDevice;
//import android.bluetooth.BluetoothSocket;
//import android.os.Bundle;
//import android.os.Handler;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.util.Set;
//import java.util.UUID;
//
////bluetooth
//
//
//public class Printooth extends AppCompatActivity {
//    BluetoothAdapter bluetoothAdapter;
//    BluetoothSocket bluetoothSocket;
//    BluetoothDevice bluetoothDevice;
//    InputStream inputStream;
//    OutputStream outputStream;
//    Thread thread;
//    EditText textBox;
//    TextView lbl, lblPrintName;
//    Button btnConnect, btnDisconnect, btnPrint;
//
//    byte[] readBuffer;
//    int readBufferPosition;
//    volatile boolean stopWorker;
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        //setContentView(R.layout.activity_bluetooth_printer);
//        textBox = (EditText) findViewById(R.id.txtText);
//        lbl = (TextView) findViewById(R.id.lbl);
//        lblPrintName = (TextView) findViewById(R.id.lblPrintName);
//        btnConnect = (Button) findViewById(R.id.btnConnect);
//        btnDisconnect = (Button) findViewById(R.id.btnDisconnect);
//        btnPrint = (Button) findViewById(R.id.btnPrint);
//
//
//        btnConnect.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                try{
//                    FindBluetoothDevice();
//                    openBluetoothPrinter();
//
//                }catch (Exception ex){
//                    ex.printStackTrace();
//                }
//            }
//        });
//        btnDisconnect.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                try{
//                    disconnectBT();
//                }catch (Exception ex){
//                    ex.printStackTrace();
//                }
//            }
//        });
//        btnPrint.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                try{
//                    printData();
//                }catch (Exception ex){
//                    ex.printStackTrace();
//                }
//            }
//        });
//
//    }
//
//    void FindBluetoothDevice(){
//       try {
//            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//            if(bluetoothAdapter==null){
//                lbl.setText("Tidak menemukan printooth");
//            }
////            if(bluetoothAdapter.isEnabled()){
////                Intent enableBT = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
////                startActivityForResult(enableBT,0);
////            }
//            Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
//            if (pairedDevices.size()>0){
//                Log.d("device", ""+pairedDevices);
//                for(BluetoothDevice pairedDev : pairedDevices){
//                    if(pairedDev.getName().equals("EP580AI")){
//                        bluetoothDevice=pairedDev;
//                        lblPrintName.setText("Printooth sudah terkoneksi dengan "+pairedDev.getName());
//                        break;
//                    }
//                }
//            }
//          lbl.setText("Printooth telah terkoneksi");
//       }catch (Exception ex){
//            ex.printStackTrace();
//       }
//    }
//
//    //Open BluetoothDevices
//    void openBluetoothPrinter() throws IOException{
//      try{
//        UUID uuidSting = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
//          bluetoothSocket=bluetoothDevice.createRfcommSocketToServiceRecord(uuidSting);
//          bluetoothSocket.connect();
//          outputStream=bluetoothSocket.getOutputStream();
//          inputStream=bluetoothSocket.getInputStream();
//
//          beginListenData();
//      }catch (Exception ex){
//
//      }
//    }
//
//    void beginListenData(){
//        try{
//
//            final Handler handler =new Handler();
//            final byte delimiter=10;
//            stopWorker =false;
//            readBufferPosition=0;
//            readBuffer = new byte[1024];
//
//            thread=new Thread(new Runnable() {
//                @Override
//                public void run() {
//
//                    while (!Thread.currentThread().isInterrupted() && !stopWorker){
//                        try{
//                            int byteAvailable = inputStream.available();
//                            if(byteAvailable>0){
//                                byte[] packetByte = new byte[byteAvailable];
//                                inputStream.read(packetByte);
//
//                                for(int i=0; i<byteAvailable; i++){
//                                    byte b = packetByte[i];
//                                    if(b==delimiter){
//                                        byte[] encodedByte = new byte[readBufferPosition];
//                                        System.arraycopy(
//                                                readBuffer,0,
//                                                encodedByte,0,
//                                                encodedByte.length
//                                        );
//                                        final String data = new String(encodedByte,"US-ASCII");
//                                        readBufferPosition=0;
//                                        handler.post(new Runnable() {
//                                            @Override
//                                            public void run() {
//                                                lblPrintName.setText(data);
//                                            }
//                                        });
//                                    }else{
//                                        readBuffer[readBufferPosition++]=b;
//                                    }
//                                }
//                            }
//                        }catch(Exception ex){
//                            stopWorker=true;
//                        }
//                    }
//
//                }
//            });
//
//            thread.start();
//        }catch (Exception ex){
//            ex.printStackTrace();
//        }
//    }
//    //Print data
//    void printData() throws IOException{
//        try{
//            String msg = "Bisa";
//            msg+="\n";
//            outputStream.write(msg.getBytes());
//            Log.d("calllback", ""+msg);
//            lblPrintName.setText("Printing Text...");
//        }catch (Exception ex){
//            ex.printStackTrace();
//        }
//    }
//
//    // Disconnect Printer //
//    void disconnectBT() throws IOException{
//        try {
//            stopWorker=true;
//            outputStream.close();
//            inputStream.close();
//            bluetoothSocket.close();
//            lblPrintName.setText("Printer Disconnected.");
//        }catch (Exception ex){
//            ex.printStackTrace();
//        }
//    }
//}
