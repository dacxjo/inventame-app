package com.ubpis.inventame.view.fragment;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.ubpis.inventame.R;
import com.ubpis.inventame.data.model.CartItem;
import com.ubpis.inventame.view.adapter.ItemCardAdapter;

import java.util.ArrayList;

public class CartFragment extends DialogFragment {
    public static String TAG = "CartFragment";
    private RecyclerView itemCartList;
    private ItemCardAdapter itemCardAdapter;
    private TextView totalPrice, subtotalPrice, IVAPrice, headlineIVA;
    private ExtendedFloatingActionButton fab;
    private double total = 0;

    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
            dialog.getWindow().setWindowAnimations(R.style.AppTheme_Slide);
        }
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_FullScreenDialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fab = view.findViewById(R.id.chargeFABButton);
        itemCartList = view.findViewById(R.id.itemCartList);
        LinearLayoutManager manager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        itemCartList.setLayoutManager(manager);
        ArrayList<CartItem> cartItems = new ArrayList<>();
        cartItems.add(new CartItem("https://www.cardamomo.news/__export/1655926043504/sites/debate/img/2022/06/22/refresco_de_cola.png_1902800913.png", "Coca-Cola 330 ml", 1,0.60f));
        cartItems.add(new CartItem("https://static2.mujerhoy.com/www/multimedia/202205/13/media/cortadas/cargo-aper-krxD--984x552@MujerHoy.jpg", "Pantalón Tejano - Talla 40", 4, 20.00f));
        itemCardAdapter = new ItemCardAdapter(cartItems);
        itemCartList.setAdapter(itemCardAdapter);
        TextView textView = view.findViewById(R.id.totalItems);
        textView.setText(String.format(textView.getText().toString(), cartItems.size()));
        if (cartItems.size() > 1){
            textView.setText(textView.getText().toString().replace("item", "items"));
        }

        for (CartItem cartItem : cartItems) {
            total += cartItem.getTotalPrice();
        }

        totalPrice = view.findViewById(R.id.totalPrice);
        subtotalPrice = view.findViewById(R.id.subtotalPrice);
        IVAPrice = view.findViewById(R.id.IVAPrice);
        headlineIVA = view.findViewById(R.id.headlineIVA);

        //set all prices
        totalPrice.setText(String.format("%.2f", total) + "€");
        subtotalPrice.setText(String.format("%.2f", total/1.21) + "€");
        IVAPrice.setText(String.format("%.2f", total - (total/1.21)) + "€");

        //set IVA
        headlineIVA.setText(String.format(headlineIVA.getText().toString(), 21));

        fab.setOnClickListener(v -> new SoftPOSPaymentFragment(total).show(
                getParentFragmentManager(), SoftPOSPaymentFragment.TAG));
    }
}