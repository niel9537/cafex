package com.p3lb.cafex.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.p3lb.cafex.Config;
import com.p3lb.cafex.MenuAuth.DetailRegistrasi;
import com.p3lb.cafex.MenuProduk.EditDataProduk;
import com.p3lb.cafex.R;
import com.p3lb.cafex.model.auth.Users;
import com.p3lb.cafex.model.produk.Products;

import java.util.List;

public class UsersRegistrasiAdapter extends RecyclerView.Adapter<UsersRegistrasiAdapter.MyViewHolder>{
    List<Users> muserslist;

    public UsersRegistrasiAdapter(List<Users> usersList) {
        muserslist = usersList;
    }


    @Override
    public UsersRegistrasiAdapter.MyViewHolder onCreateViewHolder (ViewGroup parent, int viewType){
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_row_data_user_register, parent, false);
        UsersRegistrasiAdapter.MyViewHolder mViewHolder = new UsersRegistrasiAdapter.MyViewHolder(mView);
        return mViewHolder;
    }


    @Override
    public void onBindViewHolder (UsersRegistrasiAdapter.MyViewHolder holder, final int position){
        holder.namauser.setText("Nama : "+muserslist.get(position).getNama_user());
        holder.noktpuser.setText("No KTP : "+muserslist.get(position).getNoktp_user());
        if(muserslist.get(position).getJabatan_user().equals("3")){
            holder.jabatanuser.setText("Jabatan : Kasir");
        }else{
            holder.jabatanuser.setText("Jabatan : Admin");
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(view.getContext(), DetailRegistrasi.class);
                mIntent.putExtra("id_user", muserslist.get(position).getId_user());
                mIntent.putExtra("nama_user", muserslist.get(position).getNama_user());
                mIntent.putExtra("noktp_user", muserslist.get(position).getNoktp_user());
                mIntent.putExtra("jabatan_user", muserslist.get(position).getJabatan_user());
                mIntent.putExtra("nohp_user", muserslist.get(position).getNohp_user());
                mIntent.putExtra("email_user", muserslist.get(position).getEmail_user());
                view.getContext().startActivity(mIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return muserslist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView namauser, noktpuser, jabatanuser;

        public MyViewHolder(View itemView) {
            super(itemView);
            namauser = (TextView) itemView.findViewById(R.id.namauser);
            noktpuser = (TextView) itemView.findViewById(R.id.noktpuser);
            jabatanuser = (TextView) itemView.findViewById(R.id.jabatanuser);

        }
    }
}
