package com.ubpis.inventame.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ubpis.inventame.R;
import com.ubpis.inventame.utils.Validation;

import java.util.HashMap;
import java.util.Map;

public class RegisterStepTwoViewModel extends ViewModel {


    public MutableLiveData<String> tradename;
    public MutableLiveData<String> description;
    public MutableLiveData<String> cif;
    public MutableLiveData<String> email;
    public MutableLiveData<String> password;
    public MutableLiveData<String> passwordConfirmation;
    public MutableLiveData<Map<String, Integer>> errors;
    private final HashMap<String, Integer> errorsMap = new HashMap<>();
    private final Validation validation = new Validation();

    public RegisterStepTwoViewModel() {
        email = new MutableLiveData<>();
        password = new MutableLiveData<>();
        passwordConfirmation = new MutableLiveData<>();
        tradename = new MutableLiveData<>();
        description = new MutableLiveData<>();
        cif = new MutableLiveData<>();
        errors = new MutableLiveData<>();
    }

    public MutableLiveData<String> getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email.setValue(email);
    }

    public MutableLiveData<String> getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password.setValue(password);
    }

    public MutableLiveData<String> getTradename() {
        return tradename;
    }

    public void setTradename(MutableLiveData<String> tradename) {
        this.tradename = tradename;
    }

    public MutableLiveData<String> getDescription() {
        return description;
    }

    public void setDescription(MutableLiveData<String> description) {
        this.description = description;
    }

    public MutableLiveData<String> getCif() {
        return cif;
    }

    public void setCif(MutableLiveData<String> cif) {
        this.cif = cif;
    }

    public MutableLiveData<String> getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(MutableLiveData<String> passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }


    public void checkEmail() {
        String emailValue = email.getValue();
        if (!validation.exists(emailValue)) {
            errorsMap.put("email", R.string.form_validation_required);
        } else if (validation.isEmail(emailValue)) {
            errorsMap.put("email", R.string.form_validation_email);
        } else {
            errorsMap.remove("email");
        }
        errors.setValue(errorsMap);
    }

    public void checkCif() {
        String cifValue = cif.getValue();
        if (!validation.exists(cifValue)) {
            errorsMap.put("cif", R.string.form_validation_required);
        } else if (!validation.validDocumentNumber(cifValue)) {
            errorsMap.put("cif", R.string.form_validation_invalid_document);
        } else {
            errorsMap.remove("cif");
        }
        errors.setValue(errorsMap);
    }

    public void checkPassword() {
        String passwordValue = password.getValue();
        if (!validation.exists(passwordValue)) {
            errorsMap.put("password", R.string.form_validation_required);
        } else if (validation.minLength(passwordValue, 6)) {
            errorsMap.put("password", R.string.form_validation_min_length);
        } else {
            errorsMap.remove("password");
        }
        errors.setValue(errorsMap);
    }

    public void checkPasswordConfirm() {
        String passwordValue = password.getValue();
        String passwordConfirmValue = passwordConfirmation.getValue();
        if (!validation.exists(passwordConfirmValue)) {
            errorsMap.put("passwordConfirm", R.string.form_validation_required);
        } else if (!validation.matches(passwordValue, passwordConfirmValue)) {
            errorsMap.put("passwordConfirm", R.string.form_validation_password_confirm);
        } else {
            errorsMap.remove("passwordConfirm");
        }
        errors.setValue(errorsMap);
    }

    public void checkTradename() {
        String tradename = this.tradename.getValue();
        if (!validation.exists(tradename)) {
            errorsMap.put("tradename", R.string.form_validation_required);
        } else {
            errorsMap.remove("tradename");
        }
        errors.setValue(errorsMap);
    }

    public boolean isValidForm() {
        return errorsMap.isEmpty()
                && tradename.getValue() != null
                && email.getValue() != null
                && password.getValue() != null
                && passwordConfirmation.getValue() != null
                && cif.getValue() != null;
    }


}