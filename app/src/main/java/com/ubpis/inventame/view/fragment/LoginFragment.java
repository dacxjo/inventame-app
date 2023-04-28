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
import com.ubpis.inventame.viewmodel.LoginViewModel;

public class LoginFragment extends Fragment {

    private LoginViewModel mViewModel;

    private Button backButton, loginButton, forgotPasswordButton;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        backButton = view.findViewById(R.id.back_button);
        loginButton = view.findViewById(R.id.button_login);
        forgotPasswordButton = view.findViewById(R.id.btn_goToForgotPassword);

        backButton.setOnClickListener(this::goToStartup);
        loginButton.setOnClickListener(this::goToDemoActivity);
        forgotPasswordButton.setOnClickListener(this::goToForgotPassword);
    }

    private void goToStartup(View view) {
        NavDirections action = LoginFragmentDirections.actionLoginFragmentToStartupFragment();
        Navigation.findNavController(view).navigate(action);
    }

    private void goToDemoActivity(View view) {
        NavDirections action = LoginFragmentDirections.actionLoginFragmentToDemoActivity();
        Navigation.findNavController(view).navigate(action);
    }

    private void goToForgotPassword(View view) {
        NavDirections action = LoginFragmentDirections.actionLoginFragmentToForgotPasswordFragment();
        Navigation.findNavController(view).navigate(action);
    }
}