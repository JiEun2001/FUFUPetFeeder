package com.example.fufuproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SecondActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private Button logout,historyuser,autouser;
    private TextView Distance;
    private ToggleButton Manualbtnuser;

    String valueDistanceUser;
    String valueMotorUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        getSupportActionBar().setTitle("");

        firebaseAuth = FirebaseAuth.getInstance();

        logout = (Button)findViewById(R.id.btnLogout);
        Distance = (TextView)findViewById(R.id.DistanceTVuser);
        Manualbtnuser = (ToggleButton)findViewById(R.id.manualBtnuser);
        historyuser = (Button) findViewById(R.id.btnHistoryuser);
        autouser = (Button) findViewById(R.id.btnAuto);



        DatabaseReference databaseReference;

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                valueDistanceUser = snapshot.child("ESP32/distance").getValue().toString();
                Distance.setText(valueDistanceUser);

                valueMotorUser = snapshot.child("ESP32/motor").getValue().toString();
                if(valueMotorUser.equals("0"))
                    Manualbtnuser.setChecked(false);
                else
                    Manualbtnuser.setChecked(true);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Manualbtnuser.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    DatabaseReference motorRef = FirebaseDatabase.getInstance().getReference("ESP32/motor");
                    motorRef.setValue(1);

                }else{
                    DatabaseReference motorRef = FirebaseDatabase.getInstance().getReference("ESP32/motor");
                    motorRef.setValue(0);
                }

            }
        });

        autouser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SecondActivity.this, AutoMotorUser.class));
            }
        });

        historyuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SecondActivity.this, ViewHistoryUser.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout ();
            }
        });
    }

    private void logout(){
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(SecondActivity.this,MainActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logoutmenu:{
                logout();
                break;
            }
            case R.id.profilemenu:{
                startActivity(new Intent(SecondActivity.this, ProfileActivity.class));
                break;
            }
            case R.id.aboutus:{
                startActivity(new Intent(SecondActivity.this, AboutUs.class));
                break;
            }
            case R.id.viewproduct:{
                startActivity(new Intent(SecondActivity.this, ViewProductAdmin.class));
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}