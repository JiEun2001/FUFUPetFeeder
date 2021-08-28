package com.example.fufuproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText Name;
    private EditText Password;
    private TextView Attempt, Register;
    private Button Login;
    private int counter=5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Name = (EditText) findViewById(R.id.editTextName);
        Password = (EditText) findViewById(R.id.editTextPassword);
        Attempt = (TextView) findViewById(R.id.textViewAttempt);
        Login = (Button) findViewById(R.id.ButtonLogin);
        Register = (TextView) findViewById(R.id.tvRegister);


        Attempt.setText("No. of attempt remaining: 5");

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(Name.getText().toString(), Password.getText().toString());
            }
        });
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RegistrationActivity.class));
            }
        });




    }

    private void validate(String UserName, String UserPassword){
        if((UserName.equals("admin")) && (UserPassword.equals("jasonhensem123"))){
            Intent intent = new Intent (MainActivity.this, SecondActivity.class);
            startActivity(intent);

        }else{
            counter--;

            Attempt.setText("No. of attempt remaining: " + String.valueOf(counter));


            if(counter == 0){
                Login.setEnabled(false);


            }
        }

    }

}