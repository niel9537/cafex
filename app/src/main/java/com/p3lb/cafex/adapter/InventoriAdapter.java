package com.p3lb.cafex.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.p3lb.cafex.MenuInventori.TambahBahanbaku;
import com.p3lb.cafex.MenuInventori.TampilDetailInventori;
import com.p3lb.cafex.R;
import com.p3lb.cafex.model.inventori.Inventori;

import java.util.List;

public class InventoriAdapter extends RecyclerView.Adapter<InventoriAdapter.MyViewHolder> {
    List<Inventori> inventoriList;
    public static String idkuu = "";
    public InventoriAdapter(List<Inventori> inventoris) {
        inventoriList = inventoris;
    }


    @Override
    public InventoriAdapter.MyViewHolder onCreateViewHolder (ViewGroup parent, int viewType){
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_row_data_inventori, parent, false);
        InventoriAdapter.MyViewHolder mViewHolder = new InventoriAdapter.MyViewHolder(mView);
        return mViewHolder;
    }


    @Override
    public void onBindViewHolder (InventoriAdapter.MyViewHolder holder, final int position){
        holder.idinventori.setText(inventoriList.get(position).getId_inventori());
        holder.namabahanbaku.setText(inventoriList.get(position).getNama_bahanbaku());
        holder.tanggalbuat.setText(inventoriList.get(position).getTanggal_buat());
        //idkuu = cabangList.get(position).getId_diskon();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(view.getContext(), TampilDetailInventori.class);
                mIntent.putExtra("id_inventori", inventoriList.get(position).getId_inventori());
                mIntent.putExtra("nama_bahanbaku", inventoriList.get(position).getNama_bahanbaku());
                mIntent.putExtra("tanggal_buat", inventoriList.get(position).getTanggal_buat());
                view.getContext().startActivity(mIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return inventoriList.size();
    }

    public String getIddelete(int position){
        return inventoriList.get(position).getId_inventori();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView idinventori, namabahanbaku, tanggalbuat;
        public MyViewHolder(View itemView) {
            super(itemView);
            idinventori = (TextView) itemView.findViewById(R.id.idinventori);
            namabahanbaku = (TextView) itemView.findViewById(R.id.namabahanbaku);
            tanggalbuat = (TextView) itemView.findViewById(R.id.tanggalbuat);
        }
    }
}