package com.ubpis.inventame.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ubpis.inventame.data.model.Product;

import java.util.ArrayList;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<Product>> topThreeProducts;

    public HomeViewModel() {
        topThreeProducts = new MutableLiveData<>(new ArrayList<>());
        ArrayList<Product> testArrayList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Product p = new Product(Integer.toString(i + 1), String.format("Prod%s", i + 1), String.format("Desc %s", i + 1), Integer.toString(i + 1), "https://source.unsplash.com/random/?Product&" + i, false);
            testArrayList.add(p);
        }
        this.setTopThreeProducts(testArrayList);
    }

    public MutableLiveData<ArrayList<Product>> getTopThreeProducts() {
        return topThreeProducts;
    }

    public void setTopThreeProducts(ArrayList<Product> products) {
        topThreeProducts.setValue(products);
    }
}
