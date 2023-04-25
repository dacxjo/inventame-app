package com.ubpis.inventame.view.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.ubpis.inventame.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {


    private FirebaseAuth auth;
    private FragmentLoginBinding binding;
    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        auth = FirebaseAuth.getInstance();
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.backButton.setOnClickListener(this::goToStartup);
        binding.buttonLogin.setOnClickListener(v -> {
            String username = binding.email.getText().toString();
            String password = binding.password.getText().toString();
            login(username, password);
        });
        binding.btnGoToForgotPassword.setOnClickListener(this::goToForgotPassword);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    private void resetFormState() {
        binding.emailTextField.setError(null);
        binding.passwordTextField.setError(null);
    }

    private void login(String username, String password) {
        resetFormState();
        auth.signInWithEmailAndPassword(username, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d("LoginFragment", "signInWithEmail:success");
                goToMainActivity(binding.getRoot());
            } else {
                try {
                    throw task.getException();
                } catch (FirebaseAuthException e) {
                    String errorCode = e.getErrorCode();
                    switch (errorCode) {
                        case "ERROR_INVALID_EMAIL":
                            binding.emailTextField.setError("Email inválido");
                            break;
                        case "ERROR_WRONG_PASSWORD":
                            binding.passwordTextField.setError("Contraseña incorrecta");
                            break;
                        case "ERROR_USER_NOT_FOUND":
                            // TODO: Better error notification
                            Snackbar.make(binding.getRoot(), "Usuario no encontrado", Snackbar.LENGTH_SHORT)
                                    .setAnchorView(binding.buttonLogin)
                                    .setTextColor(Color.WHITE)
                                    .show();
                            break;
                    }
                    Log.w("LoginFragment", errorCode);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void goToStartup(View view) {
        NavDirections action = LoginFragmentDirections.actionLoginFragmentToStartupFragment();
        Navigation.findNavController(view).navigate(action);
    }

    private void goToMainActivity(View view) {
        NavDirections action = LoginFragmentDirections.actionLoginFragmentToMainActivity();
        Navigation.findNavController(view).navigate(action);
    }

    private void goToForgotPassword(View view) {
        NavDirections action = LoginFragmentDirections.actionLoginFragmentToForgotPasswordFragment();
        Navigation.findNavController(view).navigate(action);
    }
}