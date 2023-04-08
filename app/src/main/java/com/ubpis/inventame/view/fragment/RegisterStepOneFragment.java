package com.ubpis.inventame.view.fragment;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ubpis.inventame.R;
import com.ubpis.inventame.view.adapter.CategoryAdapter;
import com.ubpis.inventame.viewmodel.RegisterStepOneViewModel;

public class RegisterStepOneFragment extends Fragment {

    private RegisterStepOneViewModel mViewModel;
    private RecyclerView categoryList;
    private Button backButton;
    private CategoryAdapter categoryAdapter;

    public static RegisterStepOneFragment newInstance() {
        return new RegisterStepOneFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register_step_one, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(RegisterStepOneViewModel.class);
        categoryList = view.findViewById(R.id.categoryList);
        LinearLayoutManager manager = new LinearLayoutManager(
                this.getContext(), LinearLayoutManager.VERTICAL, false
        );
        categoryList.setLayoutManager(manager);
        categoryAdapter = new CategoryAdapter(
                mViewModel.getCategories().getValue()
        );
        categoryAdapter.setOnClickCardListener(new CategoryAdapter.OnClickCardListener() {
            @Override
            public void OnClickCard(int position) {
                mViewModel.printCategoryFromPosition(position);
            }
        });
        categoryList.setAdapter(categoryAdapter);
        backButton = view.findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections action = RegisterStepOneFragmentDirections.actionRegisterStepOneToStartupFragment();
                Navigation.findNavController(view).navigate(action);
            }
        });
    }
}