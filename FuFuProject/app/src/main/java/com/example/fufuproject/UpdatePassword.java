package com.example.fufuproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UpdatePassword extends AppCompatActivity {

    private Button update;
    private EditText newPassword;
    private FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        getSupportActionBar().setTitle("");

        update = findViewById(R.id.btnUpdatePassword);
        newPassword = findViewById(R.id.etNewPassword);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!newPassword.getText().toString().isEmpty()){
                    if(newPassword.getText().toString().length() >= 8) {


                        String userPasswordNew = newPassword.getText().toString();
                        firebaseUser.updatePassword(userPasswordNew).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(UpdatePassword.this, "Password Changed", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(UpdatePassword.this, "Password Changed Failed", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
                    }else {
                        Toast.makeText(UpdatePassword.this, "Password must be 8 or above", Toast.LENGTH_SHORT).show();

                    }


                }else{
                    Toast.makeText(UpdatePassword.this, "Please insert new password", Toast.LENGTH_SHORT).show();

                }

            }
        });



    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();

        }
        return super.onOptionsItemSelected(item);
    }
}