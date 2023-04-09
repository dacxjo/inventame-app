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
import com.ubpis.inventame.data.model.Category;
import com.ubpis.inventame.view.adapter.CategoryAdapter;
import com.ubpis.inventame.viewmodel.RegisterStepOneViewModel;
import com.ubpis.inventame.viewmodel.SavedStateViewModel;

public class RegisterStepOneFragment extends Fragment {

    private RegisterStepOneViewModel mViewModel;
    private SavedStateViewModel vm;
    private RecyclerView categoryList;
    private Button backButton;
    private CategoryAdapter categoryAdapter;
    private Button nextButton;

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
        vm = new ViewModelProvider(this).get(SavedStateViewModel.class);
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

        //nextButton.setOnClickListener(this::showDialog);
    }

    private void onClickRegisterCard(int position){
        Category selected = mViewModel.checkCategoryByPosition(position);
        vm.setCategory(selected.getTitle());
        categoryAdapter.notifyDataSetChanged();
        if(vm.getCategory() != null){
            nextButton.setEnabled(true);
        }
    }

    private void goBack(View view){
        NavDirections action = RegisterStepOneFragmentDirections.actionRegisterStepOneToStartupFragment();
        Navigation.findNavController(view).navigate(action);
    }

    private void showDialog(View view){
        System.out.println("Show dialog");
        new AddProductDialogFragment().show(
                getChildFragmentManager(), AddProductDialogFragment.TAG);
    }
}