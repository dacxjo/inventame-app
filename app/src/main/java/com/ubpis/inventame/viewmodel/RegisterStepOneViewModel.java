package com.ubpis.inventame.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ubpis.inventame.data.model.Category;

import java.util.ArrayList;

public class RegisterStepOneViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<Category>> categories;

    public RegisterStepOneViewModel() {

        categories = new MutableLiveData<>(new ArrayList<>());

        // TODO: Remove this, is just for testing purposes
        // TODO: Use Firebase to retrieve categories
        ArrayList<Category> testArrayList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Category c = new Category(Integer.toString(i + 1), String.format("Cat%s", i + 1), String.format("Desc %s", i + 1), "https://source.unsplash.com/random/?Product&" + i, false);
            testArrayList.add(c);
        }
        this.setCategories(testArrayList);
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
}