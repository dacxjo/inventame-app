package com.ubpis.inventame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startup_activity);
    }

    public void login(View view){
        Intent intent = new Intent(MainActivity.this, Login.class);
        startActivity(intent);
    }

    public void register(View view){
        //Intent intent = new Intent();
        //startActivity(intent);
    }
}