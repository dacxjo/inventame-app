package com.ubpis.inventame.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ubpis.inventame.data.model.Reminder;

import java.util.ArrayList;

public class NotificationsViewModel extends ViewModel {
    private final MutableLiveData<ArrayList<Reminder>> reminders;

    public NotificationsViewModel() {

        reminders = new MutableLiveData<>(new ArrayList<>());

        // TODO: Remove this, is just for testing purposes
        // TODO: Use Firebase to retrieve categories
        ArrayList<Reminder> testArrayList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Reminder remind = new Reminder("Hola", "Va a Caducar lokete", "18:05 - 6 horas atras", "https://source.unsplash.com/random/?Product&" + i);
            testArrayList.add(remind);
        }
        this.setUsers(testArrayList);
    }

    public LiveData<ArrayList<Reminder>> getUsers() {
        return reminders;
    }

    public void setUsers(ArrayList<Reminder> cats) {
        reminders.setValue(cats);
    }
}