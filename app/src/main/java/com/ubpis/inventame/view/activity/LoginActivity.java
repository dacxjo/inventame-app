package com.ubpis.inventame.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.ubpis.inventame.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void returnStartUp (View view){
        Intent intent = new Intent(LoginActivity.this, StartupActivity.class);
        startActivity(intent);
    }

    public void recoverPassword (View view){
        Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
        startActivity(intent);
    }

    public void loginInventame (View view){
        //Intent intent = new Intent();
        //startActivity(intent);
    }


}