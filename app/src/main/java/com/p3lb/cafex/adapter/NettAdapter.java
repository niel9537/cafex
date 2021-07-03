package com.p3lb.cafex.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.p3lb.cafex.R;
import com.p3lb.cafex.model.trxbulanan.Normalbulan;
import com.p3lb.cafex.model.trxtahunan.Report;
import com.p3lb.cafex.model.trxtahunan.Result;

import java.util.List;

public class NettAdapter extends RecyclerView.Adapter<NettAdapter.MyViewHolder> {
    List<Result> resultList;
    List<Report> reportList;
    public static String idkuu = "";

    public NettAdapter(List<Result> resultList, List<Report> reportList) {
        this.resultList = resultList;
        this.reportList = reportList;
    }

    @Override
    public NettAdapter.MyViewHolder onCreateViewHolder (ViewGroup parent, int viewType){
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_row_data_listbulan, parent, false);
        NettAdapter.MyViewHolder mViewHolder = new NettAdapter.MyViewHolder(mView);
        return mViewHolder;
    }


    @Override
    public void onBindViewHolder (NettAdapter.MyViewHolder holder, final int position){
        if(resultList.get(position).getBulan().equals("1")){
            holder.bulan.setText("Januari");
        }else if(resultList.get(position).getBulan().equals("2")){
            holder.bulan.setText("Februari");
        }else if(resultList.get(position).getBulan().equals("3")){
            holder.bulan.setText("Maret");
        }else if(resultList.get(position).getBulan().equals("4")){
            holder.bulan.setText("April");
        }else if(resultList.get(position).getBulan().equals("5")){
            holder.bulan.setText("Mei");
        }else if(resultList.get(position).getBulan().equals("6")){
            holder.bulan.setText("Juni");
        }else if(resultList.get(position).getBulan().equals("7")){
            holder.bulan.setText("Juli");
        }else if(resultList.get(position).getBulan().equals("8")){
            holder.bulan.setText("Agustus");
        }else if(resultList.get(position).getBulan().equals("9")){
            holder.bulan.setText("September");
        }else if(resultList.get(position).getBulan().equals("10")){
            holder.bulan.setText("Oktober");
        }else if(resultList.get(position).getBulan().equals("11")){
            holder.bulan.setText("November");
        }else if(resultList.get(position).getBulan().equals("12")){
            holder.bulan.setText("Desember");
        }
        int total = Integer.parseInt(reportList.get(position).getTotalTransaksi());
        int hbp = Integer.parseInt(resultList.get(position).getTotalBiayaproduk());
        int hasil = total-hbp;
        holder.nett.setText("Rp "+hasil);
    }

    @Override
    public int getItemCount() {
        Log.d("sizediskon ",""+resultList.size());
        return resultList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView bulan, nett;
        public MyViewHolder(View itemView) {
            super(itemView);
            bulan = (TextView) itemView.findViewById(R.id.bulan);
            nett = (TextView) itemView.findViewById(R.id.nett);
        }
    }
}
