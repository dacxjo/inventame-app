package com.ubpis.inventame.view.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.search.SearchView;
import com.google.firebase.auth.FirebaseAuth;
import com.ubpis.inventame.R;
import com.ubpis.inventame.data.model.Product;
import com.ubpis.inventame.databinding.FragmentInventoryBinding;
import com.ubpis.inventame.view.adapter.ProductCardAdapter;
import com.ubpis.inventame.viewmodel.InventoryViewModel;

import java.util.ArrayList;

public class InventoryFragment extends Fragment {

    private InventoryViewModel viewModel;
    private ProductCardAdapter productCardAdapter;
    private FragmentInventoryBinding binding;

    public static InventoryFragment newInstance() {
        return new InventoryFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentInventoryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(InventoryViewModel.class);
        LinearLayoutManager manager = new LinearLayoutManager(
                this.getContext(), LinearLayoutManager.VERTICAL, false
        );

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.productList.getContext(),
                manager.getOrientation());
        binding.productList.addItemDecoration(dividerItemDecoration);
        binding.productList.setLayoutManager(manager);
        productCardAdapter = new ProductCardAdapter(
                viewModel.getProducts().getValue()
        );

        productCardAdapter.setOnClickCardListener(this::showEditProductDialog);

        binding.productList.setAdapter(productCardAdapter);

        final Observer<ArrayList<Product>> observerProducts= products -> {
            if (viewModel.getProducts().getValue().isEmpty()) {
                binding.emptyState.setVisibility(View.VISIBLE);
                binding.productList.setVisibility(View.GONE);
            } else {
                binding.emptyState.setVisibility(View.GONE);
                binding.productList.setVisibility(View.VISIBLE);
            }
            productCardAdapter.notifyDataSetChanged();
        };
        viewModel.getProducts().observe(this.getViewLifecycleOwner(),observerProducts);

        //TODO: Load from business or businessId
        viewModel.loadProductsFromRepository(FirebaseAuth.getInstance().getCurrentUser().getUid());

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
            }else if(newState == SearchView.TransitionState.HIDDEN){
                bottomNav.setVisibility(View.VISIBLE);
                bottomNav.animate().translationY(0).setDuration(300).setListener(null);
                fab.show();
            }
        });

    }

    private void showEditProductDialog(Product product){
        new ProductDialogFragment(true,product).show(
                getChildFragmentManager(), ProductDialogFragment.TAG);
    }
}