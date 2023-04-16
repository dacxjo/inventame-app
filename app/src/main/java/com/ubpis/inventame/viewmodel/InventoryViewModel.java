package com.ubpis.inventame.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ubpis.inventame.data.model.Product;
import com.ubpis.inventame.view.adapter.ProductCardAdapter;

import java.util.ArrayList;


public class InventoryViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<Product>> products;

    public InventoryViewModel() {

        products = new MutableLiveData<>(new ArrayList<>());

        // TODO: Remove this, is just for testing purposes
        // TODO: Use Firebase to retrieve categories
        ArrayList<Product> testArrayList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Product p = new Product(Integer.toString(i + 1), String.format("Prod%s", i + 1), String.format("Desc %s", i + 1), Integer.toString(i + 1), "https://source.unsplash.com/random/?Product&" + i, false);
            testArrayList.add(p);
        }
        this.setProducts(testArrayList);
    }

    public LiveData<ArrayList<Product>> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> prods) {
        products.setValue(prods);
    }

}