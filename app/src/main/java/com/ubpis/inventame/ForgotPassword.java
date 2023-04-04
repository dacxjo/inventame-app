package com.ubpis.inventame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ForgotPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password_activity);
    }

    public void returnBack(View view){
        Intent intent = new Intent(ForgotPassword.this, Login.class);
        startActivity(intent);
    }

    public void send(View view){
        // si es correcto el correo electronico
        Toast.makeText(getApplicationContext(), "Se ha enviado el correo correctamente.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(ForgotPassword.this, Login.class);
        startActivity(intent);
    }
}