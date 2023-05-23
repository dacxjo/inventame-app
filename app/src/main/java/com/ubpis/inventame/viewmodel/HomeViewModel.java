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
            Product p = new Product();  p.setName("Test");
            p.setId("code");
            p.setImageUrl("https://firebasestorage.googleapis.com/v0/b/inventame-8f7f9.appspot.com/o/Products%2Ftest.jpg?alt=media&token=3b3b5b1a-4b9a-4b9e-8b0a-4b9b6b0b0b0b");
            p.setStock(120);
            p.setDescription("Description");
            p.setPrice(120);
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
