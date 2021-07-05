package com.p3lb.cafex.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
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
import com.p3lb.cafex.MenuTransaksi.TambahSelectMenu;
import com.p3lb.cafex.R;
import com.p3lb.cafex.model.produk.Products;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MenusAdapter extends RecyclerView.Adapter<MenusAdapter.MyViewHolder> {
    List<Products> mmenuslist;

    public MenusAdapter(List<Products> menuList) {
        mmenuslist = menuList;
    }


    @Override
    public MenusAdapter.MyViewHolder onCreateViewHolder (ViewGroup parent, int viewType){
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_row_data_menu, parent, false);
        MenusAdapter.MyViewHolder mViewHolder = new MenusAdapter.MyViewHolder(mView);
        return mViewHolder;
    }


    @Override
    public void onBindViewHolder (MenusAdapter.MyViewHolder holder, final int position){
        holder.nama_produk.setText(mmenuslist.get(position).getNama_produk());
        int number = Integer.parseInt(mmenuslist.get(position).getHarga_produk());
        String str = String.format(Locale.US, "%,d", number).replace(',', '.');
        holder.harga_produk.setText("Rp "+str);
        holder.kategori_produk.setText(mmenuslist.get(position).getKategori_produk());
        Glide.with(holder.itemView.getContext())
                .load(Config.IMAGES_URL + mmenuslist.get(position).getFoto_produk())
                .apply(new RequestOptions().override(350, 550))
                .apply(RequestOptions.circleCropTransform())
                .into(holder.foto_produk);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(view.getContext(), TambahSelectMenu.class);
                mIntent.putExtra("id_produk", mmenuslist.get(position).getId_produk());
                mIntent.putExtra("nama_produk", mmenuslist.get(position).getNama_produk());
                mIntent.putExtra("kategori_produk", mmenuslist.get(position).getKategori_produk());
                mIntent.putExtra("harga_produk", mmenuslist.get(position).getHarga_produk());
                mIntent.putExtra("tanggal_produk", mmenuslist.get(position).getTanggal_produk());
                mIntent.putExtra("foto_produk", mmenuslist.get(position).getFoto_produk());
                view.getContext().startActivity(mIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mmenuslist.size();
    }

    public final void filtermenu(ArrayList<Products> filteredList){
        mmenuslist  = filteredList;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nama_produk, harga_produk, kategori_produk;
        public ImageView foto_produk;

        public MyViewHolder(View itemView) {
            super(itemView);
            nama_produk = (TextView) itemView.findViewById(R.id.namaProduk);
            harga_produk = (TextView) itemView.findViewById(R.id.hargaProduk);
            kategori_produk = (TextView) itemView.findViewById(R.id.kategoriProduk);
            foto_produk = (ImageView) itemView.findViewById(R.id.imgProduk);
        }
    }
}
