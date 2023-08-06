package com.ubpis.inventame.view.fragment;

import android.content.SharedPreferences;
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
import androidx.preference.PreferenceManager;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ubpis.inventame.databinding.FragmentLoginBinding;
import com.ubpis.inventame.viewmodel.LoginViewModel;

public class LoginFragment extends Fragment {


    private FirebaseAuth auth;
    private FragmentLoginBinding binding;
    private LoginViewModel mViewModel;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        auth = FirebaseAuth.getInstance();
        binding = DataBindingUtil.inflate(inflater, com.ubpis.inventame.R.layout.fragment_login, container, false);
        mViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        binding.setViewModel(mViewModel);
        binding.setLifecycleOwner(this);
        setupValidationObservers();
        return binding.getRoot();
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

    private void setupValidationObservers() {
        mViewModel.getEmail().observe(getViewLifecycleOwner(), email -> mViewModel.checkEmail());
        mViewModel.getPassword().observe(getViewLifecycleOwner(), password -> mViewModel.checkPassword());
        mViewModel.errors.observe(getViewLifecycleOwner(), errors -> {
            if (errors.containsKey("email")) {
                binding.emailTextField.setError(getString(errors.get("email")));
            } else {
                binding.emailTextField.setError(null);
            }
            if (errors.containsKey("password")) {
                binding.passwordTextField.setError(getString(errors.get("password")));
            } else {
                binding.passwordTextField.setError(null);
            }
            binding.buttonLogin.setEnabled(mViewModel.isValidForm());
        });
    }

    private void resetFormState() {
        binding.emailTextField.setError(null);
        binding.passwordTextField.setError(null);
    }

    private void login(String username, String password) {
        resetFormState();
        binding.buttonLogin.setEnabled(false);
        auth.signInWithEmailAndPassword(username, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser user = auth.getCurrentUser();
                FirebaseFirestore.getInstance().collection("users").document(user.getUid()).get().addOnSuccessListener(documentSnapshot -> {
                    String userType = documentSnapshot.getString("type");
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.putString("userType", userType);
                    editor.commit();
                });
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
                binding.buttonLogin.setEnabled(true);
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