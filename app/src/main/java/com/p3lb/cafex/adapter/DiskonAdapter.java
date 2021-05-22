package com.p3lb.cafex.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.p3lb.cafex.Config;
import com.p3lb.cafex.MenuDiskon.EditDiskon;
import com.p3lb.cafex.MenuProduk.EditDataProduk;
import com.p3lb.cafex.R;
import com.p3lb.cafex.model.diskon.Diskon;
import com.p3lb.cafex.model.transaksi.Cart;

import java.util.List;

public class DiskonAdapter extends RecyclerView.Adapter<DiskonAdapter.MyViewHolder> {
    List<Diskon> diskonList;
    public static String idkuu = "";
    public DiskonAdapter(List<Diskon> diskons) {
        diskonList = diskons;
    }


    @Override
    public DiskonAdapter.MyViewHolder onCreateViewHolder (ViewGroup parent, int viewType){
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_row_data_diskon, parent, false);
        DiskonAdapter.MyViewHolder mViewHolder = new DiskonAdapter.MyViewHolder(mView);
        return mViewHolder;
    }


    @Override
    public void onBindViewHolder (DiskonAdapter.MyViewHolder holder, final int position){
        holder.namadiskon.setText(diskonList.get(position).getNama_diskon());
        holder.minbayar.setText(diskonList.get(position).getMin_bayar());
        if(diskonList.get(position).getPersen_diskon().equals("0")){
            holder.persendiskon.setText(diskonList.get(position).getHarga_diskon());
        }else{
            double persen = Double.parseDouble(diskonList.get(position).getPersen_diskon());
            int hasil = (int) (persen*100);
            holder.persendiskon.setText(hasil+"%");
        }
        holder.maxdiskon.setText(diskonList.get(position).getMax_diskon());
        holder.expdiskon.setText(diskonList.get(position).getExp_diskon());
        idkuu = diskonList.get(position).getId_diskon();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(view.getContext(), EditDiskon.class);
                mIntent.putExtra("id_diskon", diskonList.get(position).getId_diskon());
                mIntent.putExtra("nama_diskon", diskonList.get(position).getNama_diskon());
                mIntent.putExtra("min_bayar", diskonList.get(position).getMin_bayar());
                mIntent.putExtra("persen_diskon", diskonList.get(position).getPersen_diskon());
                mIntent.putExtra("harga_diskon", diskonList.get(position).getHarga_diskon());
                mIntent.putExtra("max_diskon", diskonList.get(position).getMax_diskon());
                mIntent.putExtra("exp_diskon", diskonList.get(position).getExp_diskon());
                view.getContext().startActivity(mIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        Log.d("sizediskon ",""+diskonList.size());
        return diskonList.size();
    }

    public String getIddelete(int position){
        return diskonList.get(position).getId_diskon();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView namadiskon, minbayar, persendiskon, hargadiskon, maxdiskon, expdiskon;
        public MyViewHolder(View itemView) {
            super(itemView);
            namadiskon = (TextView) itemView.findViewById(R.id.namadiskon);
            minbayar = (TextView) itemView.findViewById(R.id.minbayar);
            persendiskon = (TextView) itemView.findViewById(R.id.persendiskon);
            maxdiskon = (TextView) itemView.findViewById(R.id.maxdiskon);
            expdiskon = (TextView) itemView.findViewById(R.id.expdiskon);
        }
    }
}
