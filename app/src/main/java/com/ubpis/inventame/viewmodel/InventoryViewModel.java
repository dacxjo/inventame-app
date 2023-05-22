package com.ubpis.inventame.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ubpis.inventame.data.model.Product;
import com.ubpis.inventame.data.repository.ProductRepository;

import java.util.ArrayList;


public class InventoryViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<Product>> products;
    private final ProductRepository productRepository;
    private final MutableLiveData<Boolean> isLoading;
    public final MutableLiveData<Product> selected = new MutableLiveData<>();

    public InventoryViewModel() {

        products = new MutableLiveData<>(new ArrayList<>());
        productRepository = ProductRepository.getInstance();
        isLoading = new MutableLiveData<>(false);
        productRepository.addOnLoadProductsListener((products, isFromCache) -> setProducts(products));
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<Product> getSelected() {
        return this.selected;
    }

    public void setSelected(Product product) {
        this.selected.setValue(product);
    }

    public void loadProductsFromRepository(String businessId) {
        isLoading.setValue(true);
        productRepository.getProducts(products.getValue(), businessId);
    }

    public void addProduct(Product product) {
        productRepository.addProduct(product);
    }

    public LiveData<ArrayList<Product>> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> prods) {
        products.setValue(prods);
    }


}