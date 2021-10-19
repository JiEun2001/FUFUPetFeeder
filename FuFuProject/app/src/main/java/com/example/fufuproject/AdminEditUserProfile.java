package com.example.fufuproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminEditUserProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_user_profile);

        EditText AdminUserName,AdminUserAge,AdminUserEmail;
        Button savebtn;

        savebtn = findViewById(R.id.btnAdminUserSave);
        AdminUserName = findViewById(R.id.etAdminUserName);
        AdminUserAge = findViewById(R.id.etAdminUserAge);
        AdminUserEmail = findViewById(R.id.etAdminUserEmail);

        Bundle bundle = getIntent().getExtras();

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference dRef = firebaseDatabase.getReference("users/" + bundle.getString("userID"));

        dRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);


                    AdminUserName.setText(userProfile.getUserName());
                    Log.d("",userProfile.getUserName());
                    AdminUserAge.setText(userProfile.getUserAge());
                    AdminUserEmail.setText(userProfile.getUserEmail());

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(AdminEditUserProfile.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = AdminUserName.getText().toString();
                String age = AdminUserAge.getText().toString();
                String email = AdminUserEmail.getText().toString();

                UserProfile userProfile = new UserProfile(age, email ,name);
                dRef.setValue(userProfile);

                finish();

            }
        });
    }
}