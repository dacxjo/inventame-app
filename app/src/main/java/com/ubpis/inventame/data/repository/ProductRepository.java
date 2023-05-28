package com.ubpis.inventame.data.repository;

import android.graphics.Bitmap;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ubpis.inventame.data.model.Employee;
import com.ubpis.inventame.data.model.Product;
import com.ubpis.inventame.data.model.UserType;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Map;

public class ProductRepository {
    private static final String TAG = "ProductRepository";

    private static final ProductRepository instance = new ProductRepository();

    private final CollectionReference productsCollection;
    public ArrayList<ProductRepository.OnLoadProductsListener> onLoadProductsListeners = new ArrayList<>();

    public ArrayList<ProductRepository.OnAddProductListener> onAddProductListeners = new ArrayList<>();
    public ArrayList<ProductRepository.OnGetProductsCountListener> onGetProductsCountListeners = new ArrayList<>();
    private ProductRepository() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.setFirestoreSettings(new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build());
        this.productsCollection = db.collection("products");
    }

    public static ProductRepository getInstance() {
        return instance;
    }

    public void addOnLoadProductsListener(ProductRepository.OnLoadProductsListener listener) {
        onLoadProductsListeners.add(listener);
    }

    public void addOnAddProductListener(ProductRepository.OnAddProductListener listener) {
        onAddProductListeners.add(listener);
    }

    public void addOnGetProductsCountListener(ProductRepository.OnGetProductsCountListener listener) {
        onGetProductsCountListeners.add(listener);
    }

    public void getProducts(ArrayList<Product> products, String businessId) {
        Query query = productsCollection.whereEqualTo("businessId", businessId).orderBy("createdAt", Query.Direction.DESCENDING);
        query.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null || !querySnapshot.isEmpty()) {
                            products.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Product product = new Product();
                                product.setId(document.getId());
                                product.setBarcode(document.getString("barcode"));
                                product.setName(document.getString("name"));
                                product.setDescription(document.getString("description"));
                                product.setBusinessId(document.getString("businessId"));
                                product.setBatch(document.getString("batch"));
                                product.setExpirationDate(document.getString("expirationDate"));
                                product.setPrice(document.getDouble("price").floatValue());
                                product.setExpired(false);
                                product.setStock(document.getLong("stock").intValue());
                                product.setImageUrl(document.getString("imageUrl"));
                                product.setCreatedAt(document.getTimestamp("createdAt"));
                                products.add(product);
                            }
                            for (ProductRepository.OnLoadProductsListener l : onLoadProductsListeners) {
                                l.onLoadProducts(products, querySnapshot.getMetadata().isFromCache());
                            }
                        }

                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });
    }

    public void getProductsCount(String businessId) {
        Query query = productsCollection.whereEqualTo("businessId", businessId);
        query.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null || !querySnapshot.isEmpty()) {
                            for (ProductRepository.OnGetProductsCountListener l : onGetProductsCountListeners) {
                                l.onGetProductsCount(querySnapshot.size());
                            }
                        }

                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });
    }

    public void addProduct(Product product) {
        productsCollection.add(product).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d(TAG, "DocumentSnapshot added with ID: " + task.getResult().getId());
                product.setId(task.getResult().getId());
                updateProduct(product);
                for (ProductRepository.OnAddProductListener l : onAddProductListeners) {
                    l.onAddProduct(product);
                }
            } else {
                Log.w(TAG, "Error adding document", task.getException());
            }
        });
    }

    public void updateProduct(Product product) {
        Map<String, Object> map = product.toMap();
        productsCollection.document(product.getId()).update(map).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d(TAG, "DocumentSnapshot added with ID: " + task.getResult());
            } else {
                Log.w(TAG, "Error adding document", task.getException());
            }
        });
    }

    public void deleteProduct(String id) {
        productsCollection.document(id).delete().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d(TAG, "DocumentSnapshot added with ID: " + task.getResult());
            } else {
                Log.w(TAG, "Error adding document", task.getException());
            }
        });
    }

    public void uploadPicture(String uid, Bitmap logoBitmap, OnSuccessListener onSuccessListener) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        logoBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] logoData = baos.toByteArray();
        StorageReference productRef = FirebaseStorage.getInstance().getReference().child("products/" + uid + ".png");
        UploadTask uploadTask = productRef.putBytes(logoData);
        uploadTask.addOnSuccessListener(taskSnapshot -> productRef.getDownloadUrl().addOnSuccessListener(onSuccessListener));
    }

    public interface OnLoadProductsListener {
        void onLoadProducts(ArrayList<Product> products, boolean isFromCache);
    }

    public interface OnAddProductListener {
        void onAddProduct(Product product);
    }

    public interface OnGetProductsCountListener {
        void onGetProductsCount(int count);
    }
}
