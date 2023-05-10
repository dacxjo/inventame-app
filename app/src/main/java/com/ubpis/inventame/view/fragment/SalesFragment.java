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
import com.ubpis.inventame.view.adapter.ItemCardAdapter;
import com.ubpis.inventame.view.adapter.SaleCardAdapter;

import java.util.ArrayList;

public class SalesFragment extends Fragment {

    private RecyclerView saleCardList;
    private SaleCardAdapter saleCardAdapter;
    private CoordinatorLayout coordinatorLayout;
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

        ArrayList<Sale> saleCards = new ArrayList<>();
        ArrayList<Product> productList = new ArrayList<>();
        productList.add(new Product("149141710", "Coca-Cola 330 ml", "Lata de Coca-Cola", 0.60, 5, false));
        productList.add(new Product("149141710", "Coca-Cola 330 ml", "Lata de Coca-Cola", 0.60, 5, false));
        productList.add(new Product("149141710", "Coca-Cola 330 ml", "Lata de Coca-Cola", 0.60, 5, false));
        productList.add(new Product("149141710", "Coca-Cola 330 ml", "Lata de Coca-Cola", 0.60, 5, false));

        saleCards.add(new Sale(productList, 2.40));
        saleCards.add(new Sale(productList, 2.40));
        saleCards.add(new Sale(productList, 2.40));
        saleCards.add(new Sale(productList, 2.40));
        saleCards.add(new Sale(productList, 2.40));

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

        saleCardAdapter = new SaleCardAdapter(saleCards, coordinatorLayout, fab);
        saleCardList.setAdapter(saleCardAdapter);
        saleCardAdapter.setOnClickCardListener(this::showInfoSaleDialog);
    }

    private void showInfoSaleDialog(int position){
        new SaleDialogFragment().show(getChildFragmentManager(), SaleDialogFragment.TAG);
    }
}