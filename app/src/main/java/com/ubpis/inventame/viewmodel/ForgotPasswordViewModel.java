package com.ubpis.inventame.viewmodel;

import android.util.Patterns;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ubpis.inventame.R;

import java.util.HashMap;
import java.util.Map;

public class ForgotPasswordViewModel extends ViewModel {

    public MutableLiveData<String> email;
    public MutableLiveData<Map<String, Integer>> errors;
    private HashMap<String, Integer> errorsMap = new HashMap<>();

    public ForgotPasswordViewModel() {
        email = new MutableLiveData<>();
        errors = new MutableLiveData<>();
    }

    public MutableLiveData<String> getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email.setValue(email);
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

    public boolean isValidForm(){
        return errorsMap.isEmpty() && email.getValue() != null;
    }

}