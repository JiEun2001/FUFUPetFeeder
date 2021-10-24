package com.example.fufuproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AboutUsAdmin extends AppCompatActivity {

    ImageView IvJason;
    ImageView IvFarhan;
    ImageView IvFaiz;
    ImageView IvDina;
    ImageView IvHasya;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        IvJason = (ImageView) findViewById(R.id.ivjason);
        IvJason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AboutUsAdmin.this, AdminJason.class));
            }
        });
        setContentView(R.layout.activity_about_us);
        IvFarhan = (ImageView) findViewById(R.id.ivfarhan);
        IvFarhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AboutUsAdmin.this, AdminFarhan.class));
            }
        });
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        IvFaiz = (ImageView) findViewById(R.id.ivfaiz);
        IvFaiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AboutUsAdmin.this, AdminFaiz.class));
            }
        });
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        IvDina = (ImageView) findViewById(R.id.ivdina);
        IvDina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AboutUsAdmin.this, AdminDina.class));
            }
        });
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        IvHasya = (ImageView) findViewById(R.id.ivhasya);
        IvHasya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AboutUsAdmin.this, AdminHasya.class));
            }
        });
    }
}