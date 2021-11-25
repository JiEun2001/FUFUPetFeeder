package com.example.fufuproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

    Context context;
    ArrayList<UserProfile> list;

    public MyAdapter(Context context, ArrayList<UserProfile> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {

        UserProfile user = list.get(position);
        holder.userAge.setText(user.getUserAge());
        holder.userEmail.setText(user.getUserEmail());
        holder.userName.setText(user.getUserName());

        holder.deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference dRef = firebaseDatabase.getReference("users/" + user.getKey());
                Log.d("",user.getKey());
                dRef.removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        if(ref == null){
                            Toast.makeText(context,"delete" ,Toast.LENGTH_SHORT).show();
                        }else{
                            FirebaseStorage fStore = FirebaseStorage.getInstance();
                            StorageReference sRef = fStore.getReference("users/").child(user.getKey())
                                    .child("Images/Profile Pic");


                            sRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(context,"deleted" ,Toast.LENGTH_SHORT).show();
                                    context.startActivity(new Intent(context,ViewListUser.class));
                                    Log.d("",user.getKey());

                                    ((Activity)view.getContext()).finish();


                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    Toast.makeText(context,"error" ,Toast.LENGTH_SHORT).show();

                                }
                            });
                        }

                    }
                });

            }
        });

        holder.editItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, AdminEditUserProfile.class);

                i.putExtra("userID",user.getKey());
                context.startActivity(i);


            }
        });
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder{

        TextView userAge, userEmail, userName;
        ImageView deleteItem,editItem;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            userEmail = itemView.findViewById(R.id.tvEmail);
            userAge = itemView.findViewById(R.id.tvAge);
            userName = itemView.findViewById(R.id.tvName);
            deleteItem = itemView.findViewById(R.id.DeleteItem);
            editItem = itemView.findViewById(R.id.Edit);


        }
    }
}
