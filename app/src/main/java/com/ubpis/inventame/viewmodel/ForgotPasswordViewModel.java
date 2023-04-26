package com.ubpis.inventame.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ubpis.inventame.R;

public class ForgotPasswordViewModel extends ViewModel {

    public MutableLiveData<String> email;
    public MutableLiveData<Integer> errorMessage;

    public ForgotPasswordViewModel() {
        email = new MutableLiveData<>();
        errorMessage = new MutableLiveData<>();
    }

    public MutableLiveData<String> getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email.setValue(email);
    }

    public boolean validateEmail(String email) {
        if (email == null || email.isEmpty()) {
            errorMessage.setValue(R.string.form_validation_required);
            return false;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            errorMessage.setValue(R.string.form_validation_email);
            return false;
        }
        errorMessage.setValue(null);
        return true;
    }
}