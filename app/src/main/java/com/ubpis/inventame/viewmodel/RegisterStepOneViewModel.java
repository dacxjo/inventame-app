package com.ubpis.inventame.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ubpis.inventame.data.model.Category;
import com.ubpis.inventame.data.repository.CategoryRepository;

import java.util.ArrayList;

public class RegisterStepOneViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<Category>> categories;

    private final CategoryRepository categoryRepository;

    public RegisterStepOneViewModel() {

        categories = new MutableLiveData<>(new ArrayList<>());
        categoryRepository = CategoryRepository.getInstance();
        categoryRepository.addOnLoadCategoriesListener(categories -> setCategories(categories));
    }

    public Category checkCategoryByPosition(int position) {
        // TODO: Check if there's a better way to do this
        Category selected = null;
        for (int i = 0; i < categories.getValue().size(); i++) {
            if (i == position) {
                selected = categories.getValue().get(i);
                selected.setSelected();
            } else {
                categories.getValue().get(i).setUnselected();
            }
        }
        return selected;
    }

    public LiveData<ArrayList<Category>> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Category> cats) {
        categories.setValue(cats);
    }

    public void loadCategoriesFromRepository() {
        categoryRepository.getCategories(categories.getValue());
    }
}