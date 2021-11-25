package com.example.fufuproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class AdminPasswordActivity extends AppCompatActivity{

    private EditText passwordEmail;
    private Button resetpassword;
    private FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_password);

        getSupportActionBar().setTitle("");

        passwordEmail = (EditText) findViewById(R.id.etPasswordEmail);
        resetpassword = (Button) findViewById(R.id.btnPasswordReset);
        firebaseAuth = FirebaseAuth.getInstance();

        resetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String useremail = passwordEmail.getText() .toString() .trim();

                if (useremail.equals("")) {
                    Toast.makeText(AdminPasswordActivity.this, "Please enter your registered Email id", Toast.LENGTH_SHORT).show();

                }else {
                    firebaseAuth.sendPasswordResetEmail(useremail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(AdminPasswordActivity .this, "PASSWORD RESET EMAIL SENT SUCCESSFULLY!", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(AdminPasswordActivity.this , MainActivity.class));
                            }else {
                                Toast.makeText(AdminPasswordActivity .this, "ERROR SENDING PASSWORD EMAIL RESET!", Toast.LENGTH_SHORT).show();

                            }

                        }
                    });
                }


            }
        });
    }
}