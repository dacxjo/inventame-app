package com.ubpis.inventame.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ubpis.inventame.R;

import java.util.HashMap;
import java.util.Map;

public class LoginViewModel extends ViewModel {

    public MutableLiveData<String> email;
    public MutableLiveData<String> password;
    public MutableLiveData<Map<String, Integer>> errorMessages;

    public LoginViewModel() {
        email = new MutableLiveData<>();
        password = new MutableLiveData<>();
        errorMessages = new MutableLiveData<>();
    }

    public MutableLiveData<String> getEmail() {
        return email;
    }

    public MutableLiveData<String> getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email.setValue(email);
    }

    public void setPassword(String password) {
        this.password.setValue(password);
    }

    public boolean validateFields(String email, String password) {
        Map<String, Integer> errors = new HashMap<>();
        if (email == null || email.isEmpty()) {
            errors.put("email", R.string.form_validation_required);
        }
        if (password == null || password.isEmpty()) {
            errors.put("password", R.string.form_validation_required);
        }
        if (errors.isEmpty()) {
            errorMessages.setValue(null);
            return true;
        }
        errorMessages.setValue(errors);
        return false;
    }

}