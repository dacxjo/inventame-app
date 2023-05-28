package com.ubpis.inventame.view.fragment;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.ubpis.inventame.R;
import com.ubpis.inventame.databinding.FragmentHomeBinding;
import com.ubpis.inventame.view.activity.OnboardActivityDirections;
import com.ubpis.inventame.view.adapter.ProductCardAdapter;
import com.ubpis.inventame.viewmodel.HomeViewModel;
import com.ubpis.inventame.viewmodel.InventoryViewModel;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment {


    private final int ANIMATION_DURATION = 1500;
    private HomeViewModel mViewModel;
    private InventoryViewModel inventoryViewModel;
    private RecyclerView topThreeProductsList;
    private ProductCardAdapter productAdapter;
    private PieChart shareChart;
    private Toolbar toolbar;
    private TextView totalProducts, totalEmployees, totalValue;
    private FragmentHomeBinding binding;
    private FirebaseAuth auth;


    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        inventoryViewModel = new ViewModelProvider(this).get(InventoryViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        auth = FirebaseAuth.getInstance();
        mViewModel.loadEmployeesCountFromRepository(auth.getCurrentUser().getUid());
        mViewModel.loadProductsCountFromRepository(auth.getCurrentUser().getUid());
        inventoryViewModel.loadProductsFromRepository(auth.getCurrentUser().getUid());
        topThreeProductsList = view.findViewById(R.id.top_three_products_list);
        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.baseline_logout_24);
        setupTopThreeProductsList();
        setupShareChart(view);
        setupCounters(view);
        binding.toolbar.setNavigationOnClickListener(this::logout);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    private void setupCounters(View view) {

        Locale locale = new Locale("es", "ES");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);

        totalEmployees = view.findViewById(R.id.total_employees);
        totalProducts = view.findViewById(R.id.total_products);
        totalValue = view.findViewById(R.id.total_value);

        ValueAnimator productsAnimator = new ValueAnimator();
        ValueAnimator employeesAnimator = new ValueAnimator();
        ValueAnimator valueAnimator = new ValueAnimator();

        mViewModel.getEmployeesCount().observe(getViewLifecycleOwner(), integer -> {
            if(integer == null) return;
            employeesAnimator.setObjectValues(0,integer);
            employeesAnimator.addUpdateListener(animation -> totalEmployees.setText(String.valueOf(animation.getAnimatedValue())));
            employeesAnimator.setDuration(this.ANIMATION_DURATION);
            employeesAnimator.start();
        });

        mViewModel.getProductsCount().observe(getViewLifecycleOwner(), integer -> {
            if(integer == null) return;
            productsAnimator.setObjectValues(0, integer);
            productsAnimator.addUpdateListener(animation -> totalProducts.setText(String.valueOf(animation.getAnimatedValue())));
            productsAnimator.setDuration(this.ANIMATION_DURATION);
            productsAnimator.start();
        });

        inventoryViewModel.getTotalValue().observe(getViewLifecycleOwner(), aDouble -> {
            if(aDouble == null) return;
            valueAnimator.setFloatValues(0, aDouble);
            valueAnimator.addUpdateListener(animation -> totalValue.setText(currencyFormatter.format(animation.getAnimatedValue())));
            valueAnimator.setDuration(this.ANIMATION_DURATION);
            valueAnimator.start();
        });



    }

    private void setupShareChart(View view) {
        shareChart = view.findViewById(R.id.share_chart);
        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(18.5f, "Green"));
        entries.add(new PieEntry(26.7f, "Yellow"));
        entries.add(new PieEntry(24.0f, "Red"));
        PieDataSet set = new PieDataSet(entries, null);
        set.setColors(new int[]{R.color.seed, R.color.md_theme_dark_surfaceTint, R.color.md_theme_dark_onSecondary}, this.getContext());
        PieData data = new PieData(set);
        data.setValueTextColor(getResources().getColor(R.color.md_theme_dark_onSurface));
        data.setValueTextSize(15f);
        shareChart.setData(data);
        shareChart.getDescription().setEnabled(false);
        shareChart.setDrawEntryLabels(false);
        shareChart.getLegend().setForm(Legend.LegendForm.CIRCLE);
        shareChart.getLegend().setOrientation(Legend.LegendOrientation.VERTICAL);
        shareChart.getLegend().setDrawInside(false);
        shareChart.getLegend().setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        shareChart.setDrawHoleEnabled(false);
        shareChart.invalidate();
    }

    private void setupTopThreeProductsList() {
        LinearLayoutManager manager = new LinearLayoutManager(
                this.getContext(), LinearLayoutManager.VERTICAL, false
        );
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(topThreeProductsList.getContext(),
                manager.getOrientation());
        topThreeProductsList.setLayoutManager(manager);
        productAdapter = new ProductCardAdapter(
                mViewModel.getTopThreeProducts().getValue(),
                false,
                true
        );
        topThreeProductsList.setAdapter(productAdapter);
        topThreeProductsList.addItemDecoration(dividerItemDecoration);
    }

    private void logout(View view) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getContext())
                .setTitle("¿Cerrar sesión?")
                .setMessage("No te vayas, te extrañaremos :c")
                .setPositiveButton("Si", (dialog, which) -> {
                    PreferenceManager.getDefaultSharedPreferences(getContext()).edit().clear().apply();
                    auth.signOut();
                    NavDirections action = OnboardActivityDirections.actionGlobalOnBoardActivity();
                    Navigation.findNavController(view).popBackStack();
                    Navigation.findNavController(view).clearBackStack(R.id.startupFragment);
                    Navigation.findNavController(view).navigate(action);
                })
                .setNegativeButton("No", null);
        builder.create().show();
    }
}