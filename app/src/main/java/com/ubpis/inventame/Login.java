package com.ubpis.inventame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
    }

    public void returnStartUp (View view){
        Intent intent = new Intent(Login.this, MainActivity.class);
        startActivity(intent);
    }

    public void recoverPassword (View view){
        Intent intent = new Intent(Login.this, ForgotPassword.class);
        startActivity(intent);
    }

    public void loginInventame (View view){
        //Intent intent = new Intent();
        //startActivity(intent);
    }


}