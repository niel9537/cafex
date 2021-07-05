package com.p3lb.cafex.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.p3lb.cafex.R;
import com.p3lb.cafex.model.trxdiskonbulanan.Diskonbulan;
import com.p3lb.cafex.model.trxrefundbulanan.Refundbulan;

import java.util.List;
import java.util.Locale;

public class TranksaksiRefundAdapter extends RecyclerView.Adapter<TranksaksiRefundAdapter.MyViewHolder> {
    List<Refundbulan> refundbulanList;
    public static String idkuu = "";
    public TranksaksiRefundAdapter(List<Refundbulan> refundbulans) {
        refundbulanList = refundbulans;
    }


    @Override
    public TranksaksiRefundAdapter.MyViewHolder onCreateViewHolder (ViewGroup parent, int viewType){
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_row_data_transaksi, parent, false);
        TranksaksiRefundAdapter.MyViewHolder mViewHolder = new TranksaksiRefundAdapter.MyViewHolder(mView);
        return mViewHolder;
    }


    @Override
    public void onBindViewHolder (TranksaksiRefundAdapter.MyViewHolder holder, final int position){
        holder.idtransaksi.setText(refundbulanList.get(position).getId_transaksi());
        holder.namapembeli.setText(refundbulanList.get(position).getNama_pembeli());
        int number = Integer.parseInt(refundbulanList.get(position).getTotal_bayar());
        String str = String.format(Locale.US, "%,d", number).replace(',', '.');
        holder.totalbayar.setText("Rp "+str);

    }

    @Override
    public int getItemCount() {
        Log.d("sizediskon ",""+refundbulanList.size());
        return refundbulanList.size();
    }

    public String getIddelete(int position){
        return refundbulanList.get(position).getId_diskon();
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
