package com.p3lb.cafex.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.p3lb.cafex.R;

public class ProdukDAOView extends RecyclerView.ViewHolder {
    public TextView nama_produk, jumlah_produk, harga_produk;
    public ImageView foto_produk;
    public LinearLayout produk;
    public ProdukDAOView(@NonNull View itemView){
        super(itemView);
       // foto_produk = itemView.findViewById(R.id.fotoProduk);
        nama_produk = (TextView) itemView.findViewById(R.id.namaProduk);
        jumlah_produk = (TextView) itemView.findViewById(R.id.jumlahProduk);
        harga_produk = (TextView) itemView.findViewById(R.id.hargaProduk);
        produk = itemView.findViewById(R.id.produk);
    }
}
