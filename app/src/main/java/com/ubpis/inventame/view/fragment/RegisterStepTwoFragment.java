package com.ubpis.inventame.view.fragment;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ubpis.inventame.R;
import com.ubpis.inventame.viewmodel.RegisterStepTwoViewModel;

public class RegisterStepTwoFragment extends Fragment {

    private RegisterStepTwoViewModel mViewModel;

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

}