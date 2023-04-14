package com.ubpis.inventame.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ubpis.inventame.data.model.User;

import java.util.ArrayList;


public class EmployeeViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<User>> employees;

    public EmployeeViewModel() {

        employees = new MutableLiveData<>(new ArrayList<>());

        // TODO: Remove this, is just for testing purposes
        // TODO: Use Firebase to retrieve categories
        ArrayList<User> testArrayList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            User u = new User(String.format("Email%s", i + 1),String.format("Name%s", i + 1), String.format("Surname%s", i + 1), "https://source.unsplash.com/random/?Product&" + i);
            testArrayList.add(u);
        }
        this.setUsers(testArrayList);
    }

    public LiveData<ArrayList<User>> getUsers() {
        return employees;
    }

    public void setUsers(ArrayList<User> cats) {
        employees.setValue(cats);
    }

}