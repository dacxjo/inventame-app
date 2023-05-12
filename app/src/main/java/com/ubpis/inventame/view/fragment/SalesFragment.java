package com.ubpis.inventame.view.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.search.SearchView;
import com.ubpis.inventame.R;
import com.ubpis.inventame.data.model.CartItem;
import com.ubpis.inventame.data.model.Product;
import com.ubpis.inventame.data.model.Sale;
import com.ubpis.inventame.view.adapter.SalesCardAdapter;

import java.util.ArrayList;

public class SalesFragment extends Fragment {

    private RecyclerView saleCardList;
    private SalesCardAdapter salesCardAdapter;
    private CoordinatorLayout coordinatorLayout;
    private ArrayList<Sale> saleCards;
    private double total = 0;

    public static SalesFragment newInstance() {
        return new SalesFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sales, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        saleCardList = view.findViewById(R.id.saleCardList);
        coordinatorLayout = view.findViewById(R.id.sales_container);
        LinearLayoutManager manager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        saleCardList.setLayoutManager(manager);

        saleCards = new ArrayList<>();
        ArrayList<CartItem> cartItems = new ArrayList<>();
        cartItems.add(new CartItem("https://images.hola.com/imagenes/seleccion/20220301205438/google-nest-hub-guia/1-185-257/google-nest-hub-m.jpg", "Google Nest Hub", 1,99.00f));
        cartItems.add(new CartItem("https://cdn.businessinsider.es/sites/navi.axelspringer.es/public/media/image/2022/02/reloj-cocina-2620297.jpg", "Clock", 3, 45.00f));
        cartItems.add(new CartItem("https://www.powerplanetonline.com/cdnassets/yeelight_crystal_pendant_light_lampara_de_techo_inteligente_09_ad_l.jpg", "Pendant Lamp", 2, 200.00f));
        cartItems.add(new CartItem("https://lh3.googleusercontent.com/cewixHQrBsI-iviE4qPNPLppaYuNTccxIBTi9v2XusjhRvp-UdBpOAYr78exyrJPM5lyFjWHnEQFBSUyJuSSCd3sI-UGN67G8Nbi=s2048", "Google Pixelbook Go", 1, 600.00f));


        for (CartItem cartItem : cartItems) {
            total += cartItem.getTotalPrice();
        }
        saleCards.add(new Sale(cartItems, total));
        saleCards.add(new Sale(cartItems, total));
        saleCards.add(new Sale(cartItems, total));
        saleCards.add(new Sale(cartItems, total));
        saleCards.add(new Sale(cartItems, total));

        TextView numSales = view.findViewById(R.id.numSales);
        TextView sortType = view.findViewById(R.id.sortType);

        numSales.setText(String.format(numSales.getText().toString(), saleCards.size()));
        sortType.setText(String.format(sortType.getText().toString(), "Recently Bought"));

        SearchView searchView = requireView().findViewById(R.id.search_view);
        BottomNavigationView bottomNav = getActivity().findViewById(R.id.bottom_navigation);
        ExtendedFloatingActionButton fab = getActivity().findViewById(R.id.extended_fab);
        searchView.addTransitionListener((searchView1, previousState, newState) -> {
            if (newState == SearchView.TransitionState.SHOWING) {
                bottomNav.animate().translationY(bottomNav.getHeight()).setDuration(300).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        bottomNav.setVisibility(View.GONE);
                    }
                });
                fab.hide();
            }
            else if(newState == SearchView.TransitionState.HIDDEN){
                bottomNav.setVisibility(View.VISIBLE);
                bottomNav.animate().translationY(0).setDuration(300).setListener(null);
                fab.show();
            }
        });

        salesCardAdapter = new SalesCardAdapter(saleCards, coordinatorLayout, fab);
        saleCardList.setAdapter(salesCardAdapter);
        salesCardAdapter.setOnClickCardListener(this::showInfoSaleDialog);
    }

    private void showInfoSaleDialog(int position){
        new SaleDialogFragment(saleCards.get(position), total).show(getChildFragmentManager(), SaleDialogFragment.TAG);
    }
}