package com.p3lb.cafex.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.p3lb.cafex.MenuUser.EditUser;
import com.p3lb.cafex.R;
import com.p3lb.cafex.model.user.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {
    List<User> userList;
    public static String idkuu = "";
    public UserAdapter(List<User> users) {
        userList = users;
    }


    @Override
    public UserAdapter.MyViewHolder onCreateViewHolder (ViewGroup parent, int viewType){
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_row_data_user, parent, false);
        UserAdapter.MyViewHolder mViewHolder = new UserAdapter.MyViewHolder(mView);
        return mViewHolder;
    }


    @Override
    public void onBindViewHolder (UserAdapter.MyViewHolder holder, final int position){
        holder.iduser.setText("ID : "+userList.get(position).getIdUser());
        holder.namauser.setText("Nama : "+userList.get(position).getNamaUser());
        holder.nohpuser.setText("Nomor HP : "+userList.get(position).getNohpUser());
        holder.noktpuser.setText("Nomor KTP : "+userList.get(position).getNoktpUser());
        holder.jabatanuser.setText("Jabatan : "+userList.get(position).getJabatanUser());
        holder.emailuser.setText("Email : "+userList.get(position).getEmailUser());
        if(userList.get(position).getJabatanUser().equals("3")){
            holder.jabatanuser.setText("Kasir");
        }else{
            holder.jabatanuser.setText("Admin");
        }
        idkuu = userList.get(position).getIdUser();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(view.getContext(), EditUser.class);
                mIntent.putExtra("id_user", userList.get(position).getIdUser());
                mIntent.putExtra("nama_user", userList.get(position).getNamaUser());
                mIntent.putExtra("nohp_user", userList.get(position).getNohpUser());
                mIntent.putExtra("noktp_user", userList.get(position).getNoktpUser());
                mIntent.putExtra("jabatan_user", userList.get(position).getJabatanUser());
                mIntent.putExtra("email_user", userList.get(position).getEmailUser());
                view.getContext().startActivity(mIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public String getIddelete(int position){
        return userList.get(position).getIdUser();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView iduser, namauser, nohpuser, noktpuser, jabatanuser, emailuser;
        public MyViewHolder(View itemView) {
            super(itemView);
            iduser = (TextView) itemView.findViewById(R.id.iduser);
            namauser = (TextView) itemView.findViewById(R.id.namauser);
            nohpuser = (TextView) itemView.findViewById(R.id.nohpuser);
            noktpuser = (TextView) itemView.findViewById(R.id.noktpuser);
            jabatanuser = (TextView) itemView.findViewById(R.id.jabatanuser);
            emailuser = (TextView) itemView.findViewById(R.id.emailuser);
        }
    }
}
