package com.ubpis.inventame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.widget.*;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    int digit;
    float actualQuantity;
    TextView quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        quantity = (TextView) findViewById(R.id.quantityToCharge);
        SpannableString spannableString =  new SpannableString(quantity.getText().toString());
        spannableString.setSpan(new RelativeSizeSpan(1.1875f), 0, 1, 0);
        quantity.setText(spannableString);
         */
    }
    /*
     public void numberPressed(View view){
        Button button = (Button) view;
        digit = Integer.parseInt(button.getText().toString()); // pasa a int el número del boton
        actualQuantity = Float.parseFloat(quantity.getText().toString().replace(',', '.').replaceAll(".$", "")); // pasa a float el número de la cantidad actual;
        actualQuantity = (actualQuantity * 10) + (digit * 0.01f);
        String newQuantity = String.format("%.2f", actualQuantity);

        newQuantity = newQuantity.replace('.', ',') + "€";
        quantity.setTextSize(TypedValue.COMPLEX_UNIT_SP,80);
        SpannableString spannableString =  new SpannableString(newQuantity);
        spannableString.setSpan(new RelativeSizeSpan(1.1875f), 0, newQuantity.indexOf(','), 0);
        quantity.setText(spannableString);
     }

     public void clearPressed(View view){
         quantity.setTextSize(TypedValue.COMPLEX_UNIT_SP,80);
         SpannableString spannableString =  new SpannableString("0,00€");
         spannableString.setSpan(new RelativeSizeSpan(1.1875f), 0, 1, 0);
         quantity.setText(spannableString);
     }

    public void backspacePressed(View view){
        String actualQuantityText = quantity.getText().toString();
        digit = Character.getNumericValue(actualQuantityText.charAt(actualQuantityText.length()-2));
        actualQuantity = Float.parseFloat(actualQuantityText.replace(',', '.').replaceAll(".$", "")); // pasa a float el número de la cantidad actual;
        actualQuantity = (actualQuantity / 10) - (digit * 0.001f);
        if (actualQuantity < 0) actualQuantity = -actualQuantity;

        String newQuantity = String.format("%.2f", actualQuantity);
        newQuantity = newQuantity.replace('.', ',') + "€";
        quantity.setTextSize(TypedValue.COMPLEX_UNIT_SP,80);
        SpannableString spannableString =  new SpannableString(newQuantity);
        spannableString.setSpan(new RelativeSizeSpan(1.1875f), 0, newQuantity.indexOf(','), 0);
        quantity.setText(spannableString);

    }
    */
}