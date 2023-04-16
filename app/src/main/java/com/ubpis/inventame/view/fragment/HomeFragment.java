package com.ubpis.inventame.view.fragment;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.protobuf.Value;
import com.ubpis.inventame.R;
import com.ubpis.inventame.view.adapter.ProductCardAdapter;
import com.ubpis.inventame.viewmodel.HomeViewModel;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {


    private final int ANIMATION_DURATION = 1500;
    private HomeViewModel mViewModel;
    private RecyclerView topThreeProductsList;
    private ProductCardAdapter productAdapter;
    private PieChart shareChart;
    private Toolbar toolbar;
    private TextView totalProducts, totalEmployees, totalValue;


    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        topThreeProductsList = view.findViewById(R.id.top_three_products_list);
        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.outline_settings_24);
        setupTopThreeProductsList();
        setupShareChart(view);
        setupCounters(view);
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

        valueAnimator.setFloatValues(0, 1250.53f);
        valueAnimator.addUpdateListener(animation -> totalValue.setText(currencyFormatter.format(animation.getAnimatedValue())));
        valueAnimator.setDuration(this.ANIMATION_DURATION);

        productsAnimator.setObjectValues(0, 100);
        productsAnimator.addUpdateListener(animation -> totalProducts.setText(String.valueOf(animation.getAnimatedValue())));
        productsAnimator.setDuration(this.ANIMATION_DURATION);

        employeesAnimator.setObjectValues(0, 10);
        employeesAnimator.addUpdateListener(animation -> totalEmployees.setText(String.valueOf(animation.getAnimatedValue())));
        employeesAnimator.setDuration(this.ANIMATION_DURATION);

        productsAnimator.start();
        employeesAnimator.start();
        valueAnimator.start();
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
                mViewModel.getTopThreeProducts().getValue()
        );
        topThreeProductsList.setAdapter(productAdapter);
        topThreeProductsList.addItemDecoration(dividerItemDecoration);
    }
}