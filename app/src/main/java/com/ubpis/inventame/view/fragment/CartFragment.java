package com.ubpis.inventame.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ubpis.inventame.R;
import com.ubpis.inventame.data.model.CartItem;
import com.ubpis.inventame.view.adapter.ItemCardAdapter;

import java.util.ArrayList;

public class CartFragment extends Fragment {
    private RecyclerView itemCartList;
    private ItemCardAdapter itemCardAdapter;

    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        itemCartList = view.findViewById(R.id.itemCartList);
        LinearLayoutManager manager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        itemCartList.setLayoutManager(manager);
        ArrayList<CartItem> cartItems = new ArrayList<>();
        cartItems.add(new CartItem("https://www.cardamomo.news/__export/1655926043504/sites/debate/img/2022/06/22/refresco_de_cola.png_1902800913.png", "Coca-Cola 330 ml", 1,0.60f));
        cartItems.add(new CartItem("https://static2.mujerhoy.com/www/multimedia/202205/13/media/cortadas/cargo-aper-krxD--984x552@MujerHoy.jpg", "Pantalón Tejano - Talla 40", 4, 20.00f));
        itemCardAdapter = new ItemCardAdapter(cartItems);
        itemCartList.setAdapter(itemCardAdapter);
        TextView textView = view.findViewById(R.id.totalItems);
        if (cartItems.size() > 1){
            textView.setText(textView.getText().toString().replace("1", Integer.toString(cartItems.size())));
            textView.setText(textView.getText().toString().replace("item", "items"));
        }
    }
}