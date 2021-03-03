package com.p3lb.cafex.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.p3lb.cafex.MainActivity;
import com.p3lb.cafex.R;
import com.p3lb.cafex.holder.ProdukDAOView;
import com.p3lb.cafex.model.ProdukDAO;

import java.util.List;

public class ProdukAdapter extends RecyclerView.Adapter <ProdukDAOView>{
    private Context context;
    List<ProdukDAO> list;

    public ProdukAdapter(Context context, List<ProdukDAO> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ProdukDAOView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_data_produk, viewGroup, false);
        return new ProdukDAOView(view);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ProdukDAOView produkDAOView, int i) {
        final ProdukDAO currentData = list.get(i);
        produkDAOView.nama_produk.setText("Nama Produk  : " + currentData.getNama_produk());
        produkDAOView.jumlah_produk.setText("Tersedia  : " + currentData.getJumlah_produk());
        produkDAOView.harga_produk.setText("Harga Produk : " + currentData.getHarga_produk());

        produkDAOView.produk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("id_produk", currentData.getId_produk());
                intent.putExtra("nama_produk", currentData.getNama_produk());
                intent.putExtra("jumlah_produk", currentData.getJumlah_produk());
                intent.putExtra("harga_produk", currentData.getHarga_produk());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
