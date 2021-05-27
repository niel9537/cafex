package com.p3lb.cafex.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.p3lb.cafex.MenuInventori.TampilDetailInventori;
import com.p3lb.cafex.R;
import com.p3lb.cafex.model.bahanbaku.Bahanbaku;
import com.p3lb.cafex.model.inventori.Inventori;

import java.util.List;

public class BahanbakuAdapter extends RecyclerView.Adapter<BahanbakuAdapter.MyViewHolder> {
    List<Bahanbaku> bahanbakuList;
    public static String idkuu = "";
    public BahanbakuAdapter(List<Bahanbaku> bahanbakus) {
        bahanbakuList = bahanbakus;
    }


    @Override
    public BahanbakuAdapter.MyViewHolder onCreateViewHolder (ViewGroup parent, int viewType){
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_row_data_bahanbaku, parent, false);
        BahanbakuAdapter.MyViewHolder mViewHolder = new BahanbakuAdapter.MyViewHolder(mView);
        return mViewHolder;
    }


    @Override
    public void onBindViewHolder (BahanbakuAdapter.MyViewHolder holder, final int position){
        holder.jumlahbahanbaku.setText(bahanbakuList.get(position).getJumlahBahanbaku());
        holder.hargabahanbaku.setText(bahanbakuList.get(position).getExpBahanbaku());
        holder.tanggalmasuk.setText(bahanbakuList.get(position).getTanggalMasuk());
        if(bahanbakuList.get(position).getTanggalKeluar().equals("0000-00-00")){
            holder.tanggalkeluar.setText("-");
        }else{
            holder.tanggalkeluar.setText(bahanbakuList.get(position).getTanggalKeluar());
        }
        //idkuu = cabangList.get(position).getId_diskon();

    }

    @Override
    public int getItemCount() {
        return bahanbakuList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView jumlahbahanbaku, hargabahanbaku, tanggalmasuk, tanggalkeluar;
        public MyViewHolder(View itemView) {
            super(itemView);
            jumlahbahanbaku = (TextView) itemView.findViewById(R.id.jumlahbahanbaku);
            hargabahanbaku = (TextView) itemView.findViewById(R.id.hargabahanbaku);
            tanggalmasuk = (TextView) itemView.findViewById(R.id.tanggalmasuk);
            tanggalkeluar = (TextView) itemView.findViewById(R.id.tanggalkeluar);
        }
    }
}