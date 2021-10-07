package com.example.fufuproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AdminLogInActivity extends AppCompatActivity {

    private EditText Name;
    private EditText Password;
    private TextView Attempt;
    private Button Login;
    private int counter=5;
    private TextView userRegistration;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private TextView forgotPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        Name = (EditText) findViewById(R.id.editTextName);
        Password = (EditText) findViewById(R.id.editTextPassword);
        Attempt = (TextView) findViewById(R.id.textViewAttempt);
        Login = (Button) findViewById(R.id.ButtonLogin);
        userRegistration = (TextView) findViewById(R.id.tvRegister);
        forgotPassword = (TextView) findViewById(R.id.tvForgorPassword);


        Attempt.setText("No. of attempt remaining: 5");

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        if(user !=null) {
            finish();
            startActivity(new Intent(AdminLogInActivity.this, SecondActivity.class));
        }

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!Name.getText().toString().isEmpty() && !Password.getText().toString().isEmpty()) {
                    validate(Name.getText().toString(), Password.getText().toString());
                }else{
                    Toast.makeText(AdminLogInActivity.this, "Please insert Email and password", Toast.LENGTH_SHORT).show();

                }
            }
        });


        userRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(AdminLogInActivity.this, RegistrationActivity.class));
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminLogInActivity.this , PasswordActivity.class));
            }
        });


    }

    private void validate(String UserName, String UserPassword) {

        progressDialog.setMessage("Processing Please Wait");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(UserName,UserPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    progressDialog.dismiss();
                    //Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    CheckEmailVerification();
                    //startActivity(new Intent(MainActivity.this, SecondActivity.class));

                }else{
                    Toast.makeText(AdminLogInActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    counter--;
                    Attempt.setText("No of attempts remaining: " + counter);
                    progressDialog.dismiss();
                    if (counter == 0){
                        Login.setEnabled(false);
                    }
                }

            }
            private void CheckEmailVerification(){
                FirebaseUser firebaseUser = firebaseAuth.getInstance().getCurrentUser();
                boolean emailflag = firebaseUser.isEmailVerified();

                if(emailflag){
                    finish();
                    startActivity(new Intent(AdminLogInActivity.this, SecondActivity.class));
                }else{
                    Toast.makeText(AdminLogInActivity.this, "Please verify your email", Toast.LENGTH_SHORT).show();
                    firebaseAuth.signOut();
                }
            }

        });
    }




}

