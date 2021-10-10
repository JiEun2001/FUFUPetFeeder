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

public class AdminHomepage extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private Button logout;
    private TextView Distance;
    private ToggleButton Manual;

    String valueDistance;
    String valueMotor;

    DatabaseReference dref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_homepage);

        getSupportActionBar().setTitle("");

        firebaseAuth = FirebaseAuth.getInstance();

        logout = (Button)findViewById(R.id.btnLogout);
        Distance = (TextView)findViewById(R.id.DistanceTV);
        Manual = (ToggleButton)findViewById(R.id.manualBtn);

        dref = FirebaseDatabase.getInstance().getReference();
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                valueDistance = snapshot.child("ESP32/distance").getValue().toString();
                Distance.setText(valueDistance);

                valueMotor = snapshot.child("ESP32/motor").getValue().toString();
                if(valueMotor.equals("0"))
                    Manual.setChecked(false);
                else
                    Manual.setChecked(true);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Manual.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
        startActivity(new Intent(AdminHomepage.this,MainActivity.class));
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
                startActivity(new Intent(AdminHomepage.this, AdminProfileActivity.class));
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}