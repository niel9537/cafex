package com.p3lb.cafex.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.p3lb.cafex.R;
import com.p3lb.cafex.model.trxbulanan.Normalbulan;
import com.p3lb.cafex.model.trxdiskonbulanan.Diskonbulan;

import java.util.List;
import java.util.Locale;

public class TranksaksiDiskonAdapter extends RecyclerView.Adapter<TranksaksiDiskonAdapter.MyViewHolder> {
    List<Diskonbulan> diskonbulanList;
    public static String idkuu = "";
    public TranksaksiDiskonAdapter(List<Diskonbulan> diskonbulans) {
        diskonbulanList = diskonbulans;
    }


    @Override
    public TranksaksiDiskonAdapter.MyViewHolder onCreateViewHolder (ViewGroup parent, int viewType){
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_row_data_transaksi, parent, false);
        TranksaksiDiskonAdapter.MyViewHolder mViewHolder = new TranksaksiDiskonAdapter.MyViewHolder(mView);
        return mViewHolder;
    }


    @Override
    public void onBindViewHolder (TranksaksiDiskonAdapter.MyViewHolder holder, final int position){
        holder.idtransaksi.setText(diskonbulanList.get(position).getId_transaksi());
        holder.namapembeli.setText(diskonbulanList.get(position).getNama_pembeli());
        int number = Integer.parseInt(diskonbulanList.get(position).getTotal_bayar());
        String str = String.format(Locale.US, "%,d", number).replace(',', '.');
        holder.totalbayar.setText("Rp "+str);

    }

    @Override
    public int getItemCount() {
        Log.d("sizediskon ",""+diskonbulanList.size());
        return diskonbulanList.size();
    }

    public String getIddelete(int position){
        return diskonbulanList.get(position).getId_diskon();
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
