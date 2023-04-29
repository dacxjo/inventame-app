package com.ubpis.inventame.viewmodel;

import android.util.Patterns;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ubpis.inventame.R;

import java.util.HashMap;
import java.util.Map;

public class LoginViewModel extends ViewModel {

    public MutableLiveData<String> email;
    public MutableLiveData<String> password;
    public MutableLiveData<Map<String, Integer>> errors;
    private HashMap<String, Integer> errorsMap = new HashMap<>();

    public LoginViewModel() {
        email = new MutableLiveData<>();
        password = new MutableLiveData<>();
        errors = new MutableLiveData<>();
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


    public void checkEmail() {
        String emailValue = email.getValue();
        System.out.println(emailValue);
        if (emailValue == null || emailValue.isEmpty()) {
            errorsMap.put("email", R.string.form_validation_required);
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailValue).matches()) {
            errorsMap.put("email", R.string.form_validation_email);
            System.out.println(errors.getValue());
        } else {
            errorsMap.remove("email");
        }
        errors.setValue(errorsMap);
    }

    public void checkPassword() {
        String passwordValue = password.getValue();
        if (passwordValue == null || passwordValue.isEmpty()) {
            errorsMap.put("password", R.string.form_validation_required);
        } else {
            errorsMap.remove("password");
        }
        errors.setValue(errorsMap);
    }

    public boolean isValidForm() {
       return errorsMap.isEmpty() && email.getValue() != null && password.getValue() != null;
    }



}