package com.ubpis.inventame.view.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.ubpis.inventame.R;
import com.ubpis.inventame.databinding.FragmentForgotPasswordBinding;
import com.ubpis.inventame.viewmodel.ForgotPasswordViewModel;

public class ForgotPasswordFragment extends Fragment {

    private ForgotPasswordViewModel mViewModel;
    private FragmentForgotPasswordBinding binding;
    private FirebaseAuth auth;

    public static ForgotPasswordFragment newInstance() {
        return new ForgotPasswordFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        auth = FirebaseAuth.getInstance();
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_forgot_password, container, false);
        mViewModel = new ViewModelProvider(this).get(ForgotPasswordViewModel.class);
        binding.setViewModel(mViewModel);
        binding.setLifecycleOwner(this);
        setupValidationObservers();
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.backButton.setOnClickListener(this::goToLogin);
        binding.buttonRecover.setOnClickListener(this::sendForgotPasswordEmail);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    private void setupValidationObservers() {
        mViewModel.getEmail().observe(getViewLifecycleOwner(), email -> mViewModel.checkEmail());
        mViewModel.errors.observe(getViewLifecycleOwner(), errors -> {
            if (errors.containsKey("email")) {
                binding.emailTextField.setError(getString(errors.get("email")));
            }else{
                binding.emailTextField.setError(null);
            }
            if(mViewModel.isValidForm()){
                binding.buttonRecover.setEnabled(true);
            }else{
                binding.buttonRecover.setEnabled(false);
            }
        });
    }

    private void resetFormState() {
        binding.emailTextField.setError(null);
        binding.email.setText("");
    }

    private void goToLogin(View view) {
        NavDirections action = ForgotPasswordFragmentDirections.actionForgotPasswordFragmentToLoginFragment();
        Navigation.findNavController(view).navigate(action);
    }

    private void sendForgotPasswordEmail(View view) {
        String email = mViewModel.getEmail().getValue();
        binding.buttonRecover.setEnabled(false);
        auth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Snackbar.make(binding.getRoot(), "Email enviado correctamente", Snackbar.LENGTH_SHORT)
                        .setAnchorView(binding.buttonRecover)
                        .setTextColor(Color.WHITE)
                        .show();
                resetFormState();
                binding.buttonRecover.setEnabled(true);
            } else {
                Snackbar.make(binding.getRoot(), "Ha habido un error", Snackbar.LENGTH_SHORT)
                        .setAnchorView(binding.buttonRecover)
                        .setBackgroundTint(Color.RED)
                        .setTextColor(Color.WHITE)
                        .show();
                binding.buttonRecover.setEnabled(true);
            }
        });
    }
}