package com.ubpis.inventame.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

public class SavedStateViewModel extends ViewModel {
    private SavedStateHandle state;

    public SavedStateViewModel(SavedStateHandle savedStateHandle) {
        state = savedStateHandle;
    }

    public void setCategory(String category){
        state.set("category",category);
    }

    public String getCategory(){
        return state.get("category");
    }

}
