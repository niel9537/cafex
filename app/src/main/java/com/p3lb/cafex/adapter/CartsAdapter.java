package com.p3lb.cafex.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.p3lb.cafex.Config;
import com.p3lb.cafex.MenuInventori.TampilDetailInventori;
import com.p3lb.cafex.MenuProduk.EditDataProduk;
import com.p3lb.cafex.MenuTransaksi.TambahSelectMenu;
import com.p3lb.cafex.MenuTransaksi.TampilCheckoutMenu;
import com.p3lb.cafex.MenuTransaksi.TampilDataMenu;
import com.p3lb.cafex.R;
import com.p3lb.cafex.model.produk.Products;
import com.p3lb.cafex.model.transaksi.Cart;
import com.p3lb.cafex.model.transaksi.DetailTransaksi;

import java.util.List;
import java.util.Locale;

public class CartsAdapter extends RecyclerView.Adapter<CartsAdapter.MyViewHolder> {
    List<Cart> cartList;
    public static String idkuu = "";
    public CartsAdapter(List<Cart> carts) {
        cartList = carts;
    }


    @Override
    public CartsAdapter.MyViewHolder onCreateViewHolder (ViewGroup parent, int viewType){
//        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_row_data_cart, parent, false);
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_row_data_newcart, parent, false);
        CartsAdapter.MyViewHolder mViewHolder = new CartsAdapter.MyViewHolder(mView);
        return mViewHolder;
    }


    @Override
    public void onBindViewHolder (CartsAdapter.MyViewHolder holder, final int position){
        holder.id_item.setText(cartList.get(position).getId_detailtransaksi());
        int number = Integer.parseInt(cartList.get(position).getHarga_subtotal());
        String str = String.format(Locale.US, "%,d", number).replace(',', '.');
        holder.harga_item.setText(""+str);
        holder.jumlah_item.setText("x "+cartList.get(position).getJumlah_item());
        holder.kategori_item.setText(cartList.get(position).getKategori_produk());
        holder.nama_item.setText(cartList.get(position).getNama_produk());
        Glide.with(holder.itemView.getContext())
                .load(Config.IMAGES_URL + cartList.get(position).getFoto_produk())
                .apply(new RequestOptions().override(350, 550))
                .apply(RequestOptions.circleCropTransform())
                .into(holder.foto_produk);
        holder.hapusitemkeranjang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(view.getContext(), TampilCheckoutMenu.class);
                mIntent.putExtra("iddetailtransaksi", cartList.get(position).getId_detailtransaksi());
                view.getContext().startActivity(mIntent);
            }
        });
        idkuu = cartList.get(position).getId_detailtransaksi();

    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public String getIddelete(int position){
        return cartList.get(position).getId_detailtransaksi();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nama_item, harga_item, kategori_item, jumlah_item, id_item, hapusitemkeranjang;
        public ImageView foto_produk;

        public MyViewHolder(View itemView) {
            super(itemView);
            hapusitemkeranjang = (Button) itemView.findViewById(R.id.hapusitemkeranjang);
            id_item = (TextView) itemView.findViewById(R.id.idDetailtransaksi);
            nama_item = (TextView) itemView.findViewById(R.id.namaItem);
            harga_item = (TextView) itemView.findViewById(R.id.hargaItem);
            kategori_item = (TextView) itemView.findViewById(R.id.kategoriItem);
            jumlah_item = (TextView) itemView.findViewById(R.id.jumlahItem);
            foto_produk = (ImageView) itemView.findViewById(R.id.foto_produk);
        }
    }
}
