package com.example.fufuproject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {

    Context context;

    ArrayList<Distance> list;

    public HistoryAdapter(Context context, ArrayList<Distance> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.historyitem,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Distance dist = list.get(position);
        Log.d("",dist.getHistorydistance());
        holder.distance.setText(dist.getHistorydistance());
        holder.date.setText(dist.getDate());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //@Override
    /*public int getItemCount() {
        return 0;
    }*/



    public static class MyViewHolder extends  RecyclerView.ViewHolder{

        TextView distance, date;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            distance = itemView.findViewById(R.id.tvDistance);
            date = itemView.findViewById(R.id.tvDate);

        }
    }
}
