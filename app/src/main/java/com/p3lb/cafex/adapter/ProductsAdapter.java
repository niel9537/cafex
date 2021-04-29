package com.p3lb.cafex.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

//`import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.p3lb.cafex.Config;
import com.p3lb.cafex.MenuProduk.EditDataProduk;
import com.p3lb.cafex.R;
import com.p3lb.cafex.model.produk.Products;
//import com.squareup.picasso.Picasso;

import java.util.List;

//import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.MyViewHolder>{
    List<Products> mproductsList;

    public ProductsAdapter(List<Products> productsList) {
        mproductsList = productsList;
    }


    @Override
    public MyViewHolder onCreateViewHolder (ViewGroup parent, int viewType){
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_row_data_produk, parent, false);
        MyViewHolder mViewHolder = new MyViewHolder(mView);
        return mViewHolder;
    }


    @Override
    public void onBindViewHolder (MyViewHolder holder,final int position){
        holder.nama_produk.setText(mproductsList.get(position).getNama_produk());
        holder.harga_produk.setText(mproductsList.get(position).getHarga_produk());
        holder.kategori_produk.setText(mproductsList.get(position).getKategori_produk());
        Glide.with(holder.itemView.getContext())
                .load(Config.IMAGES_URL + mproductsList.get(position).getFoto_produk())
                .apply(new RequestOptions().override(350, 550))
                .apply(RequestOptions.circleCropTransform())
                .into(holder.foto_produk);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(view.getContext(), EditDataProduk.class);
                mIntent.putExtra("id_produk", mproductsList.get(position).getId_produk());
                mIntent.putExtra("nama_produk", mproductsList.get(position).getNama_produk());
                mIntent.putExtra("kategori_produk", mproductsList.get(position).getKategori_produk());
                mIntent.putExtra("tanggal_produk", mproductsList.get(position).getTanggal_produk());
                mIntent.putExtra("foto_produk", mproductsList.get(position).getFoto_produk());
                view.getContext().startActivity(mIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mproductsList.size();
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
