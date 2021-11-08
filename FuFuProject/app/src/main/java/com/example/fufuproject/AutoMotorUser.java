package com.example.fufuproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AutoMotorUser extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    AutoAdapter autoAdapter;
    ArrayList<AutoMotor> list;
    Button add;
    TextView tvTimer;
    int t2Hour,t2Minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_motor_user);

        tvTimer =findViewById(R.id.tvTime);
        add = (Button) findViewById(R.id.btnAddAutotime);
        recyclerView = findViewById(R.id.Automotorlist);
        databaseReference = FirebaseDatabase.getInstance().getReference("autoFeed");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        autoAdapter = new AutoAdapter(this,list);
        recyclerView.setAdapter(autoAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    AutoMotor autoMotor = dataSnapshot.getValue(AutoMotor.class);
                    list.add(autoMotor);


                }
                autoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        tvTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //initiate time picker dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        AutoMotorUser.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                //initialize hour and minute
                                t2Hour = i;
                                t2Minute = i1;
                                //store hour and minute in string
                                String time = t2Hour + ":" + t2Minute;
                                //initialize 24 hours time format
                                SimpleDateFormat f24Hours = new SimpleDateFormat(
                                        "HH:mm"
                                );
                                try {
                                    Date date = f24Hours.parse(time);
                                    //initialize 12 hours time format
                                    SimpleDateFormat f12Hours = new SimpleDateFormat(
                                            "hh:mm aa"
                                    );
                                    //set selected time on text view
                                    tvTimer.setText(f12Hours.format(date));

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        },12,0,false
                );
                timePickerDialog.updateTime(t2Hour,t2Minute);
                timePickerDialog.show();

            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.push().child("time").setValue(tvTimer.getText());

            }
        });

    }
}