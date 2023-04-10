package com.ubpis.inventame.view.fragment;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ubpis.inventame.R;
import com.ubpis.inventame.viewmodel.RegisterStepTwoViewModel;

public class RegisterStepTwoFragment extends Fragment {

    private RegisterStepTwoViewModel mViewModel;

    private Button backButton, registerBtn;

    public static RegisterStepTwoFragment newInstance() {
        return new RegisterStepTwoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register_step_two, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(RegisterStepTwoViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String selectedCategory = RegisterStepTwoFragmentArgs.fromBundle(getArguments()).getSelectedCategory();
        backButton = view.findViewById(R.id.back_button);
        registerBtn = view.findViewById(R.id.registerBtn);
        backButton.setOnClickListener(this::goBack);
        registerBtn.setOnClickListener(this::showDialog);
    }

    private void goBack(View view) {
        NavDirections action = RegisterStepTwoFragmentDirections.actionRegisterStepTwoToRegisterStepOne();
        Navigation.findNavController(view).navigate(action);
    }


    private void showDialog(View view) {
        new AddProductDialogFragment().show(
                getChildFragmentManager(), AddProductDialogFragment.TAG);
    }
}