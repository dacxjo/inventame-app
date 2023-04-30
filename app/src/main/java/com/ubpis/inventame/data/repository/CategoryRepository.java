package com.ubpis.inventame.data.repository;


import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.ubpis.inventame.data.model.Category;

import java.util.ArrayList;

public class CategoryRepository {
    private static final String TAG = "CategoryRepository";

    private static final CategoryRepository instance = new CategoryRepository();

    private final CollectionReference categoriesCollection;
    public ArrayList<OnLoadCategoriesListener> onLoadCategoriesListeners = new ArrayList<>();

    private CategoryRepository() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        this.categoriesCollection = db.collection("categories");
    }

    public static CategoryRepository getInstance() {
        return instance;
    }

    public void addOnLoadCategoriesListener(OnLoadCategoriesListener listener) {
        onLoadCategoriesListeners.add(listener);
    }

    public void getCategories(ArrayList<Category> categories) {
        categories.clear();
        categoriesCollection
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d(TAG, document.getId() + " => " + document.getData());
                            Category category = new Category(
                                    document.getId(),
                                    document.getString("title"),
                                    document.getString("description"),
                                    document.getString("img"),
                                    document.getTimestamp("createdAt"),
                                    document.getTimestamp("deletedAt")
                            );
                            categories.add(category);
                        }

                        for (OnLoadCategoriesListener l : onLoadCategoriesListeners) {
                            l.onLoadCategories(categories);
                        }
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });
    }

    public interface OnLoadCategoriesListener {
        void onLoadCategories(ArrayList<Category> categories);
    }
}