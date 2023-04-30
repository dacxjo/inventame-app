package com.ubpis.inventame.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ubpis.inventame.data.model.Employee;

import java.util.ArrayList;


public class EmployeeViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<Employee>> employees;

    public EmployeeViewModel() {

        employees = new MutableLiveData<>(new ArrayList<>());

        // TODO: Remove this, is just for testing purposes
        // TODO: Use Firebase to retrieve categories
        ArrayList<Employee> testArrayList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Employee e = new Employee();
            e.setName(String.format("Name%s", i + 1));
            e.setLastname(String.format("Surname%s", i + 1));
            e.setEmail(String.format("Email%s", i + 1));
            e.setImageUrl("https://source.unsplash.com/random/?People&" + i);
            testArrayList.add(e);
        }
        this.setUsers(testArrayList);
    }

    public LiveData<ArrayList<Employee>> getUsers() {
        return employees;
    }

    public void setUsers(ArrayList<Employee> cats) {
        employees.setValue(cats);
    }

}