package com.ubpis.inventame.view.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
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
        binding = FragmentForgotPasswordBinding.inflate(inflater, container, false);
        mViewModel = new ViewModelProvider(this).get(ForgotPasswordViewModel.class);
        mViewModel.errorMessage.observe(getViewLifecycleOwner(), errorCode -> {
            if (errorCode == null) {
                return;
            }
            binding.emailTextField.setError(getContext().getString(errorCode));
        });
        binding.setViewModel(mViewModel);
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
        if (!mViewModel.validateEmail(email)) {
            return;
        }
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