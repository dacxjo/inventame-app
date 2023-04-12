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

import com.ubpis.inventame.R;
import com.ubpis.inventame.viewmodel.ForgotPasswordViewModel;

public class ForgotPasswordFragment extends Fragment {

    private ForgotPasswordViewModel mViewModel;

    private Button backButton;

    public static ForgotPasswordFragment newInstance() {
        return new ForgotPasswordFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_forgot_password, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ForgotPasswordViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        backButton = view.findViewById(R.id.back_button);
        backButton.setOnClickListener(this::goToLogin);
    }

    private void goToLogin(View view){
        NavDirections action = ForgotPasswordFragmentDirections.actionForgotPasswordFragmentToLoginFragment();
        Navigation.findNavController(view).navigate(action);
    }
}