package com.example.fufuproject;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AutoAdapter extends RecyclerView.Adapter<AutoAdapter.MyViewHolder> {

    Context context;

    ArrayList<AutoMotor> list;

    public AutoAdapter(Context context, ArrayList<AutoMotor> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.auto,parent,false);
        return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        AutoMotor autoMotor = list.get(position);
        holder.time.setText(autoMotor.getTime());
        //Log.d("",autoMotor.getTime());
        holder.deletetime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference dRef = firebaseDatabase.getReference("autoFeed/"+ autoMotor.getKey());

                dRef.removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        if(ref == null){
                            Toast.makeText(context,"delete" ,Toast.LENGTH_SHORT).show();
                        }else {


                        }
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder{

        TextView time;
        ImageView deletetime;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.tvTime);
            deletetime = itemView.findViewById(R.id.DeleteTime);
        }
    }

}
