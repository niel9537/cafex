package com.p3lb.cafex.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.p3lb.cafex.MenuTransaksi.TambahSelectMenu;
import com.p3lb.cafex.MenuTransaksi.TampilRiwayat;
import com.p3lb.cafex.R;
import com.p3lb.cafex.model.trxbulanan.History;
import com.p3lb.cafex.model.trxbulanan.Normalbulan;

import java.util.List;
import java.util.Locale;

public class HistoriTranksaksiAdapter extends RecyclerView.Adapter<HistoriTranksaksiAdapter.MyViewHolder> {
    List<History> historyList;
    public static String idkuu = "";
    public HistoriTranksaksiAdapter(List<History> histories) {
        historyList = histories;
    }


    @Override
    public HistoriTranksaksiAdapter.MyViewHolder onCreateViewHolder (ViewGroup parent, int viewType){
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_row_data_history_transaksi, parent, false);
        HistoriTranksaksiAdapter.MyViewHolder mViewHolder = new HistoriTranksaksiAdapter.MyViewHolder(mView);
        return mViewHolder;
    }


    @Override
    public void onBindViewHolder (HistoriTranksaksiAdapter.MyViewHolder holder, final int position){
        holder.idtransaksi.setText(historyList.get(position).getIdTransaksi());
        holder.namapembeli.setText(historyList.get(position).getNamaPembeli());
        int number = Integer.parseInt(historyList.get(position).getTotalBayar());
        String str = String.format(Locale.US, "%,d", number).replace(',', '.');
        holder.totalbayar.setText("Rp "+str);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(view.getContext(), TampilRiwayat.class);
                mIntent.putExtra("id_transaksi", historyList.get(position).getIdTransaksi());
                view.getContext().startActivity(mIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        Log.d("sizediskon ",""+historyList.size());
        return historyList.size();
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
