package com.ubpis.inventame;

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

import com.ubpis.inventame.data.model.CartItem;
import com.ubpis.inventame.view.adapter.ItemAdapter;

import java.util.ArrayList;

public class CartFragment extends Fragment {
    private RecyclerView itemCartList;
    private ItemAdapter itemAdapter;

    public CartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Cesta.
     */
    // TODO: Rename and change types and number of parameters
    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
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
        itemAdapter = new ItemAdapter(cartItems);
        itemCartList.setAdapter(itemAdapter);
        TextView textView = (TextView) view.findViewById(R.id.totalItems);
        if (cartItems.size() > 1){
            textView.setText(textView.getText().toString().replace("1", Integer.toString(cartItems.size())));
            textView.setText(textView.getText().toString().replace("item", "items"));
        }
    }
}