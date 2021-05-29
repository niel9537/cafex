package com.p3lb.cafex.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.p3lb.cafex.model.listcabang.ListCabang;
import com.p3lb.cafex.MenuCabang.EditCabang;
import com.p3lb.cafex.R;
import com.p3lb.cafex.model.cabang.Cabang;

import java.util.List;

public class ListCabangAdapter extends RecyclerView.Adapter<ListCabangAdapter.MyViewHolder> {
    List<ListCabang> listCabangs;
    public static String idkuu = "";
    public ListCabangAdapter(List<ListCabang> cabangs) {
        listCabangs = cabangs;
    }


    @Override
    public ListCabangAdapter.MyViewHolder onCreateViewHolder (ViewGroup parent, int viewType){
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_row_data_listcabang, parent, false);
        ListCabangAdapter.MyViewHolder mViewHolder = new ListCabangAdapter.MyViewHolder(mView);
        return mViewHolder;
    }


    @Override
    public void onBindViewHolder (ListCabangAdapter.MyViewHolder holder, final int position){
        holder.listidcabang.setText(listCabangs.get(position).getNama_cabang());
        holder.listnamacabang.setText(listCabangs.get(position).getKode_cabang());

    }

    @Override
    public int getItemCount() {
        return listCabangs.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView listidcabang, listnamacabang;
        public MyViewHolder(View itemView) {
            super(itemView);
            listidcabang = (TextView) itemView.findViewById(R.id.listidcabang);
            listnamacabang = (TextView) itemView.findViewById(R.id.listnamacabang);

        }
    }
}