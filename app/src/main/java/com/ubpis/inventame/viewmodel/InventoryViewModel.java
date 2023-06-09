package com.ubpis.inventame.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ubpis.inventame.data.model.Product;
import com.ubpis.inventame.data.repository.ProductRepository;

import java.util.ArrayList;


public class InventoryViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<Product>> products, results;
    private final ProductRepository productRepository;
    private final MutableLiveData<Boolean> isLoading;
    public final MutableLiveData<Product> selected = new MutableLiveData<>();
    public final MutableLiveData<Integer> totalValue = new MutableLiveData<>();

    public InventoryViewModel() {

        products = new MutableLiveData<>(new ArrayList<>());
        results = new MutableLiveData<>(new ArrayList<>());
        productRepository = ProductRepository.getInstance();
        isLoading = new MutableLiveData<>(false);
        productRepository.addOnLoadProductsListener((products, isFromCache) -> {
            setProducts(products);
            calculateTotalValue();
        });
        productRepository.addOnAddProductListener((product) -> {
            loadProductsFromRepository(product.getBusinessId());
        });
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

    public void addOnAddProductListener(ProductRepository.OnAddProductListener listener) {
        productRepository.addOnAddProductListener(listener);
    }

    public void addProduct(Product product) {
        productRepository.addProduct(product);
    }


    public void updateProduct(Product product) {
        productRepository.updateProduct(product);
    }

    public void deleteProduct(String id) {
        productRepository.deleteProduct(id);
    }

    public LiveData<ArrayList<Product>> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> prods) {
        products.setValue(prods);
    }

    public void searchProducts(String query) {
        results.getValue().clear();
        if(query.isEmpty()){
            return;
        }
        ArrayList<Product> prods = new ArrayList<>(products.getValue());
        for (Product product : prods) {
            if (product.getName().toLowerCase().contains(query.toLowerCase())) {
                results.getValue().add(product);
            }
        }
    }


    public LiveData<ArrayList<Product>> getResults() {
        return results;
    }
    public void setTotalValue(int total){
        totalValue.setValue(total);
    }

    public LiveData<Integer> getTotalValue(){
        return totalValue;
    }

    public void calculateTotalValue(){
        int total = 0;
        for(Product product : products.getValue()){
            total += product.getPrice() * product.getStock();
        }
       setTotalValue(total);

    }


}