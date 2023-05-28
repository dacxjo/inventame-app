package com.ubpis.inventame.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ubpis.inventame.data.model.Product;
import com.ubpis.inventame.data.repository.EmployeeRepository;

import java.util.ArrayList;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<Product>> topThreeProducts;
    private final EmployeeRepository employeeRepository;

    private final MutableLiveData<Integer> employeesCount;

    public HomeViewModel() {
        topThreeProducts = new MutableLiveData<>(new ArrayList<>());
        employeeRepository = EmployeeRepository.getInstance();
        employeesCount = new MutableLiveData<>(0);
        ArrayList<Product> testArrayList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Product p = new Product();  p.setName("Test");
            p.setId("code");
            p.setImageUrl("https://firebasestorage.googleapis.com/v0/b/inventame-8f7f9.appspot.com/o/Products%2Ftest.jpg?alt=media&token=3b3b5b1a-4b9a-4b9e-8b0a-4b9b6b0b0b0b");
            p.setStock(120);
            p.setDescription("Description");
            p.setPrice(120);
            testArrayList.add(p);
        }
        this.setTopThreeProducts(testArrayList);
        employeeRepository.addOnGetEmployeesCountListener((count) -> setEmployeesCount(count));
    }

    public MutableLiveData<ArrayList<Product>> getTopThreeProducts() {
        return topThreeProducts;
    }

    public void setTopThreeProducts(ArrayList<Product> products) {
        topThreeProducts.setValue(products);
    }

    public void setEmployeesCount(int count) {
        employeesCount.setValue(count);
    }

    public MutableLiveData<Integer> getEmployeesCount() {
        return employeesCount;
    }

    public void loadEmployeesCountFromRepository(String businessId) {
        employeeRepository.getEmployeesCount(businessId);
    }

}
