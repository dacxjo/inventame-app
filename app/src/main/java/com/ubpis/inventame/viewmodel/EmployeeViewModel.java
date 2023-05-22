package com.ubpis.inventame.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ubpis.inventame.data.model.Employee;
import com.ubpis.inventame.data.repository.EmployeeRepository;

import java.util.ArrayList;


public class EmployeeViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<Employee>> employees;
    private final EmployeeRepository employeeRepository;
    private final MutableLiveData<Boolean> isLoading;
    public final MutableLiveData<Employee> selected = new MutableLiveData<>();

    public EmployeeViewModel() {
        employees = new MutableLiveData<>(new ArrayList<>());
        employeeRepository = EmployeeRepository.getInstance();
        isLoading = new MutableLiveData<>(false);
        employeeRepository.addOnLoadEmployeesListener((employees, isFromCache) -> setEmployees(employees));
        employeeRepository.addOnLoadEmployeesListener((employees, isFromCache) -> isLoading.setValue(false));
    }

    public LiveData<ArrayList<Employee>> getEmployees() {
        return employees;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<Employee> getSelected() {
        return selected;
    }

    public void setSelected(Employee employee) {
        selected.setValue(employee);
    }

    public void setEmployees(ArrayList<Employee> emps) {
        employees.setValue(emps);
    }

    public void loadEmployeesFromRepository(String businessId) {
        isLoading.setValue(true);
        employeeRepository.getEmployees(employees.getValue(), businessId);
    }

    public void addEmployee(Employee employee) {
        employeeRepository.addEmployee(employee);
    }

    public void updateEmployee(Employee employee) {
        employeeRepository.updateEmployee(employee);
    }

    public void deleteEmployee(String employeeId) {
        employeeRepository.deleteEmployee(employeeId);
    }

}