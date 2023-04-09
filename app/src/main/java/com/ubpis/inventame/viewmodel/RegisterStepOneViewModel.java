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

        ArrayList<Category> testArrayList = new ArrayList<>();
        Category c1 = new Category("Cat1","Desc 1","https://images.unsplash.com/photo-1560343090-f0409e92791a?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Nnx8cHJvZHVjdHxlbnwwfHwwfHw%3D&w=1000&q=80",false);
        Category c2 = new Category("Cat2","Desc 2","https://media.istockphoto.com/id/1370669395/photo/different-hair-care-products-on-wooden-table.jpg?b=1&s=170667a&w=0&k=20&c=-VKPETob_3F361SvnatkwIYWjipBZKloBtgm1jsKmf4=",false);
        Category c3 = new Category("Cat3","Desc 3","https://images.unsplash.com/photo-1505740420928-5e560c06d30e?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8cHJvZHVjdCUyMGltYWdlc3xlbnwwfHwwfHw%3D&w=1000&q=80",false);
        Category c4 = new Category("Cat2","Desc 2","https://media.istockphoto.com/id/1165099864/photo/plastic-free-set-with-eco-cotton-bag-glass-jar-green-leaves-and-recycled-tableware-top-view.jpg?b=1&s=170667a&w=0&k=20&c=lBKKbQqCp1xAj7rodBEVp_Iv36jjATSgbpTjdx9WB_A=",false);
        testArrayList.add(c1);
        testArrayList.add(c2);
        testArrayList.add(c3);
        testArrayList.add(c4);
        this.setCategories(testArrayList);
    }

    public Category checkCategoryByPosition(int position){
       Category selected = null;
        for (int i = 0; i < categories.getValue().size(); i++) {
            if(i == position){
                selected = categories.getValue().get(i);
                selected.setSelected();
            }else{
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