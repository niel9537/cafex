package com.p3lb.cafex.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.p3lb.cafex.MenuCabang.EditCabang;
import com.p3lb.cafex.R;
import com.p3lb.cafex.model.cabang.Cabang;
import com.p3lb.cafex.model.refund.Refund;

import java.util.List;

public class RefundAdapter extends RecyclerView.Adapter<RefundAdapter.MyViewHolder> {
    List<Refund> refundList;
    public static String idkuu = "";
    public RefundAdapter(List<Refund> refunds) {
        refundList = refunds;
    }


    @Override
    public RefundAdapter.MyViewHolder onCreateViewHolder (ViewGroup parent, int viewType){
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_row_data_refund, parent, false);
        RefundAdapter.MyViewHolder mViewHolder = new RefundAdapter.MyViewHolder(mView);
        return mViewHolder;
    }


    @Override
    public void onBindViewHolder (RefundAdapter.MyViewHolder holder, final int position){
        holder.namaproduk.setText(refundList.get(position).getNamaProduk());
        holder.jumlahitem.setText("x "+refundList.get(position).getJumlahItem());
        //idkuu = cabangList.get(position).getId_diskon();

    }

    @Override
    public int getItemCount() {
        return refundList.size();
    }

    public String getIddelete(int position){
        return refundList.get(position).getIdTransaksi();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView namaproduk, jumlahitem;
        public MyViewHolder(View itemView) {
            super(itemView);
            namaproduk = (TextView) itemView.findViewById(R.id.namaproduk);
            jumlahitem = (TextView) itemView.findViewById(R.id.jumlahitem);
        }
    }
}