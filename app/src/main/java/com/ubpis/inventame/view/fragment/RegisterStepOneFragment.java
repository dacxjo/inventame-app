package com.ubpis.inventame.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ubpis.inventame.R;
import com.ubpis.inventame.data.model.Category;
import com.ubpis.inventame.view.adapter.CategoryAdapter;
import com.ubpis.inventame.viewmodel.RegisterStepOneViewModel;

public class RegisterStepOneFragment extends Fragment {

    private RegisterStepOneViewModel mViewModel;
    private RecyclerView categoryList;
    private Button backButton, nextButton;
    private CategoryAdapter categoryAdapter;
    private String selectedCategory;


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
        backButton = view.findViewById(R.id.back_button);
        nextButton = view.findViewById(R.id.button_next);

        categoryList = view.findViewById(R.id.categoryList);
        LinearLayoutManager manager = new LinearLayoutManager(
                this.getContext(), LinearLayoutManager.VERTICAL, false
        );
        categoryList.setLayoutManager(manager);
        categoryAdapter = new CategoryAdapter(
                mViewModel.getCategories().getValue()
        );
        categoryAdapter.setOnClickCardListener(this::onClickRegisterCard);
        categoryList.setAdapter(categoryAdapter);

        backButton.setOnClickListener(this::goBack);

        nextButton.setOnClickListener(this::goToStepTwo);
    }

    private void onClickRegisterCard(int position) {
        Category selected = mViewModel.checkCategoryByPosition(position);
        selectedCategory = selected.getId();
        categoryAdapter.notifyDataSetChanged();
        if (selectedCategory != null) {
            nextButton.setEnabled(true);
        }
    }

    private void goBack(View view) {
        NavDirections action = RegisterStepOneFragmentDirections.actionRegisterStepOneToStartupFragment();
        Navigation.findNavController(view).navigate(action);
    }

    private void goToStepTwo(View view) {
        NavDirections action = RegisterStepOneFragmentDirections.actionRegisterStepOneToRegisterStepTwo(this.selectedCategory);
        Navigation.findNavController(view).navigate(action);
    }

}