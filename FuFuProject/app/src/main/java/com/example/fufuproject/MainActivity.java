package com.example.fufuproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private EditText Name;
    private EditText Password;
    private TextView Attempt;
    private Button Login;
    private int counter=5;
    private TextView userRegistration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Name = (EditText) findViewById(R.id.editTextName);
        Password = (EditText) findViewById(R.id.editTextPassword);
        Attempt = (TextView) findViewById(R.id.textViewAttempt);
        Login = (Button) findViewById(R.id.ButtonLogin);
        userRegistration = (TextView) findViewById(R.id.tvRegister);


        Attempt.setText("No. of attempt remaining: 5");

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(Name.getText().toString(), Password.getText().toString());
            }
        });


        userRegistration.setOnClickListener(new View.OnClickListener() {
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