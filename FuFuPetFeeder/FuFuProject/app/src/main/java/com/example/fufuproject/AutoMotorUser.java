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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
    DatabaseReference databaseReference;


    ImageView delete1,delete2,delete3,delete4,delete5;
    TextView tvTimer1,tvTimer2,tvTimer3,tvTimer4,tvTimer5;
    int t1Hour,t1Minute, t2Hour,t2Minute, t3Hour,t3Minute, t4Hour,t4Minute, t5Hour,t5Minute;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_motor_user);
        tvTimer1 = findViewById(R.id.tvTimer1);
        tvTimer2 = findViewById(R.id.tvTimer2);
        tvTimer3 = findViewById(R.id.tvTimer3);
        tvTimer4 = findViewById(R.id.tvTimer4);
        tvTimer5 = findViewById(R.id.tvTimer5);

        delete1 = (ImageView) findViewById(R.id.deletetimer1);
        delete2 = (ImageView) findViewById(R.id.deletetimer2);
        delete3 = (ImageView) findViewById(R.id.deletetimer3);
        delete4 = (ImageView) findViewById(R.id.deletetimer4);
        delete5 = (ImageView) findViewById(R.id.deletetimer5);

        databaseReference = FirebaseDatabase.getInstance().getReference("Masa");

        databaseReference.child("time1").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {

                }
                else {
                    tvTimer1.setText(String.valueOf(task.getResult().getValue()));
                }
            }
        });

        tvTimer1.setOnClickListener(new View.OnClickListener() {
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
                                t1Hour = i;
                                t1Minute = i1;
                                //store hour and minute in string
                                String time = t1Hour + ":" + t1Minute;
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
                                    tvTimer1.setText(f12Hours.format(date));
                                    databaseReference.child("time1").setValue(tvTimer1.getText());

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        },12,0,false
                );
                timePickerDialog.updateTime(t1Hour,t1Minute);

                timePickerDialog.show();

            }
        });

        delete1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvTimer1.setText("");
                databaseReference.child("time1").setValue("");
            }
        });


        databaseReference.child("time2").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {

                }
                else {
                    tvTimer2.setText(String.valueOf(task.getResult().getValue()));
                }
            }
        });

        tvTimer2.setOnClickListener(new View.OnClickListener() {
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
                                    tvTimer2.setText(f12Hours.format(date));
                                    databaseReference.child("time2").setValue(tvTimer2.getText());

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

        delete2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvTimer2.setText("");
                databaseReference.child("time2").setValue("");
            }
        });


        databaseReference.child("time3").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {

                }
                else {
                    tvTimer3.setText(String.valueOf(task.getResult().getValue()));
                }
            }
        });

        tvTimer3.setOnClickListener(new View.OnClickListener() {
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
                                t3Hour = i;
                                t3Minute = i1;
                                //store hour and minute in string
                                String time = t3Hour + ":" + t3Minute;
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
                                    tvTimer3.setText(f12Hours.format(date));
                                    databaseReference.child("time3").setValue(tvTimer3.getText());

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        },12,0,false
                );
                timePickerDialog.updateTime(t3Hour,t3Minute);

                timePickerDialog.show();

            }
        });

        delete3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvTimer3.setText("");
                databaseReference.child("time3").setValue("");
            }
        });

        databaseReference.child("time4").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {

                }
                else {
                    tvTimer4.setText(String.valueOf(task.getResult().getValue()));
                }
            }
        });

        tvTimer4.setOnClickListener(new View.OnClickListener() {
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
                                t4Hour = i;
                                t4Minute = i1;
                                //store hour and minute in string
                                String time = t4Hour + ":" + t4Minute;
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
                                    tvTimer4.setText(f12Hours.format(date));
                                    databaseReference.child("time4").setValue(tvTimer4.getText());

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        },12,0,false
                );
                timePickerDialog.updateTime(t4Hour,t4Minute);

                timePickerDialog.show();

            }
        });

        delete4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvTimer4.setText("");
                databaseReference.child("time4").setValue("");
            }
        });



        databaseReference.child("time5").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {

                }
                else {
                    tvTimer5.setText(String.valueOf(task.getResult().getValue()));
                }
            }
        });

        tvTimer5.setOnClickListener(new View.OnClickListener() {
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
                                t5Hour = i;
                                t5Minute = i1;
                                //store hour and minute in string
                                String time = t5Hour + ":" + t5Minute;
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
                                    tvTimer5.setText(f12Hours.format(date));
                                    databaseReference.child("time5").setValue(tvTimer5.getText());

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        },12,0,false
                );
                timePickerDialog.updateTime(t5Hour,t5Minute);

                timePickerDialog.show();

            }
        });

        delete5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvTimer5.setText("");
                databaseReference.child("time5").setValue("");
            }
        });

    }
}