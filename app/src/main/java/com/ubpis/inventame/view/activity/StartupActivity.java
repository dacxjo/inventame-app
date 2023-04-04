package com.ubpis.inventame.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ubpis.inventame.R;

public class StartupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);
    }

    public void login(View view){
        Intent intent = new Intent(StartupActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void register(View view){
        //Intent intent = new Intent();
        //startActivity(intent);
    }
}