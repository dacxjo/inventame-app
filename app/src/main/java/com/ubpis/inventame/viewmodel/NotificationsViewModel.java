package com.ubpis.inventame.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ubpis.inventame.data.model.Reminder;

import java.util.ArrayList;

public class NotificationsViewModel extends ViewModel {
    private final MutableLiveData<ArrayList<Reminder>> employees;

    public NotificationsViewModel() {

        employees = new MutableLiveData<>(new ArrayList<>());

        // TODO: Remove this, is just for testing purposes
        // TODO: Use Firebase to retrieve categories
        ArrayList<Reminder> testArrayList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Reminder u = new Reminder("1", "Producto " + (i + 1), "18:05 - 6 horas atras", "https://source.unsplash.com/random/?Product&" + i);
            testArrayList.add(u);
        }
        this.setUsers(testArrayList);
    }

    public LiveData<ArrayList<Reminder>> getUsers() {
        return employees;
    }

    public void setUsers(ArrayList<Reminder> cats) {
        employees.setValue(cats);
    }
}