package com.p3lb.cafex.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.p3lb.cafex.MenuCabang.EditCabang;
import com.p3lb.cafex.MenuDiskon.EditDiskon;
import com.p3lb.cafex.R;
import com.p3lb.cafex.model.cabang.Cabang;
import com.p3lb.cafex.model.diskon.Diskon;

import java.util.List;

public class CabangAdapter extends RecyclerView.Adapter<CabangAdapter.MyViewHolder> {
    List<Cabang> cabangList;
    public static String idkuu = "";
    public CabangAdapter(List<Cabang> cabangs) {
        cabangList = cabangs;
    }


    @Override
    public CabangAdapter.MyViewHolder onCreateViewHolder (ViewGroup parent, int viewType){
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_row_data_cabang, parent, false);
        CabangAdapter.MyViewHolder mViewHolder = new CabangAdapter.MyViewHolder(mView);
        return mViewHolder;
    }


    @Override
    public void onBindViewHolder (CabangAdapter.MyViewHolder holder, final int position){
        holder.idcabang.setText(cabangList.get(position).getId_cabang());
        holder.namacabang.setText(cabangList.get(position).getNama_cabang());
        holder.notelpcabang.setText(cabangList.get(position).getNotelp_cabang());
        holder.alamatcabang.setText(cabangList.get(position).getAlamat_cabang());
        holder.tanggalcabang.setText(cabangList.get(position).getTanggal_cabang());
        holder.updatecabang.setText(cabangList.get(position).getUpdate_cabang());
        //idkuu = cabangList.get(position).getId_diskon();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(view.getContext(), EditCabang.class);
                mIntent.putExtra("id_cabang", cabangList.get(position).getId_cabang());
                mIntent.putExtra("nama_cabang", cabangList.get(position).getNama_cabang());
                mIntent.putExtra("notelp_cabang", cabangList.get(position).getNotelp_cabang());
                mIntent.putExtra("alamat_cabang", cabangList.get(position).getAlamat_cabang());
                mIntent.putExtra("tanggal_cabang", cabangList.get(position).getTanggal_cabang());
                view.getContext().startActivity(mIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return cabangList.size();
    }

    public String getIddelete(int position){
        return cabangList.get(position).getId_cabang();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView idcabang, namacabang, notelpcabang, alamatcabang, tanggalcabang, updatecabang;
        public MyViewHolder(View itemView) {
            super(itemView);
            idcabang = (TextView) itemView.findViewById(R.id.idcabang);
            namacabang = (TextView) itemView.findViewById(R.id.namacabang);
            notelpcabang = (TextView) itemView.findViewById(R.id.notelpcabang);
            alamatcabang = (TextView) itemView.findViewById(R.id.alamatcabang);
            tanggalcabang = (TextView) itemView.findViewById(R.id.tanggalcabang);
            updatecabang = (TextView) itemView.findViewById(R.id.updatecabang);
        }
    }
}