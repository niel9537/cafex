package com.p3lb.cafex.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.p3lb.cafex.MenuDiskon.EditDiskon;
import com.p3lb.cafex.R;
import com.p3lb.cafex.model.diskon.Diskon;
import com.p3lb.cafex.model.trxbulanan.Normalbulan;

import java.util.List;
import java.util.Locale;

public class TranksaksiAdapter extends RecyclerView.Adapter<TranksaksiAdapter.MyViewHolder> {
    List<Normalbulan> normalbulanList;
    public static String idkuu = "";
    public TranksaksiAdapter(List<Normalbulan> normalbulans) {
        normalbulanList = normalbulans;
    }


    @Override
    public TranksaksiAdapter.MyViewHolder onCreateViewHolder (ViewGroup parent, int viewType){
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_row_data_transaksi, parent, false);
        TranksaksiAdapter.MyViewHolder mViewHolder = new TranksaksiAdapter.MyViewHolder(mView);
        return mViewHolder;
    }


    @Override
    public void onBindViewHolder (TranksaksiAdapter.MyViewHolder holder, final int position){
        holder.idtransaksi.setText(normalbulanList.get(position).getId_transaksi());
        holder.namapembeli.setText(normalbulanList.get(position).getNama_pembeli());
        int number = Integer.parseInt(normalbulanList.get(position).getTotal_bayar());
        String str = String.format(Locale.US, "%,d", number).replace(',', '.');
        holder.totalbayar.setText("Rp "+str);

    }

    @Override
    public int getItemCount() {
        Log.d("sizediskon ",""+normalbulanList.size());
        return normalbulanList.size();
    }

    public String getIddelete(int position){
        return normalbulanList.get(position).getId_diskon();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView idtransaksi, namapembeli, totalbayar;
        public MyViewHolder(View itemView) {
            super(itemView);
            idtransaksi = (TextView) itemView.findViewById(R.id.idtransaksi);
            namapembeli = (TextView) itemView.findViewById(R.id.namapembeli);
            totalbayar = (TextView) itemView.findViewById(R.id.totalbayar);
        }
    }
}
