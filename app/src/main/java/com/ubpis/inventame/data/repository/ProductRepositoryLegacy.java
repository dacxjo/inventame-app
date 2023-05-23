package com.ubpis.inventame.data.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.ubpis.inventame.data.model.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductRepositoryLegacy {

    private static final String TAG = "Repository";

    /**
     * Autoinstància, pel patró singleton
     */
    private static final ProductRepositoryLegacy mInstance = new ProductRepositoryLegacy();

    /**
     * Referència a la Base de Dades
     */
    private FirebaseFirestore mDb;

    /**
     * Definició de listener (interficie),
     * per escoltar quan s'hagin acabat de llegir els usuaris de la BBDD
     */
    public interface OnLoadProductsListener {
        void onLoadProducts(ArrayList<Product> products);
    }

    public ArrayList<OnLoadProductsListener> mOnLoadProductsListeners = new ArrayList<>();

    /**
     * Definició de listener (interficie)
     * per poder escoltar quan s'hagi acabat de llegir la Url de la foto de perfil
     * d'un usuari concret
     */
    public interface OnLoadProductPictureUrlListener {
        void OnLoadProductPictureUrl(String pictureUrl);
    }

    public OnLoadProductPictureUrlListener mOnLoadProductPictureUrlListener;

    /**
     * Constructor privat per a forçar la instanciació amb getInstance(),
     * com marca el patró de disseny Singleton class
     */
    private ProductRepositoryLegacy() {
        mDb = FirebaseFirestore.getInstance();
    }

    /**
     * Retorna aqusta instancia singleton
     *
     * @return
     */
    public static ProductRepositoryLegacy getInstance() {
        return mInstance;
    }

    /**
     * Afegir un listener de la operació OnLoadUsersListener.
     * Pot haver-n'hi només un. Fem llista, com a exemple, per demostrar la flexibilitat
     * d'aquest disseny.
     *
     * @param listener
     */
    public void addOnLoadProductsListener(OnLoadProductsListener listener) {
        mOnLoadProductsListeners.add(listener);
    }

    /**
     * Setejem un listener de la operació OnLoadUserPictureUrlListener.
     * En aquest cas, no és una llista de listeners. Només deixem haver-n'hi un,
     * també a tall d'exemple.
     *
     * @param listener
     */
    public void setOnLoadProductPictureListener(OnLoadProductPictureUrlListener listener) {
        mOnLoadProductPictureUrlListener = listener;
    }

    /**
     * Mètode que llegeix els usuaris. Vindrà cridat des de fora i quan acabi,
     * avisarà sempre als listeners, invocant el seu OnLoadUsers.
     */
    public void loadProducts(ArrayList<Product> products) {
        products.clear();
        mDb.collection("products")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Product product = new Product();
                                products.add(product);
                            }
                            /* Callback listeners */
                            for (OnLoadProductsListener l : mOnLoadProductsListeners) {
                                l.onLoadProducts(products);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    /**
     * Mètode que llegeix la Url d'una foto de perfil d'un usuari indicat pel seu
     * email. Vindrà cridat des de fora i quan acabi, avisarà sempre al listener,
     * invocant el seu OnLoadUserPictureUrl.
     */
    public void loadPictureOfProduct(String id) {
        mDb.collection("products")
                .document(id)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document != null) {
                                mOnLoadProductPictureUrlListener.OnLoadProductPictureUrl(document.getString("picture_url"));
                            } else {
                                Log.d("LOGGER", "No such document");
                            }
                        } else {
                            Log.d("LOGGER", "get failed with ", task.getException());
                        }
                    }
                });
    }

    /**
     * Mètode que afegeix un nou usuari a la base de dades. Utilitzat per la funció
     * de Sign-Up (registre) de la SignUpActivity.
     *
     * @param id
     * @param name
     * @param price
     * @param stock
     */
    public void addProduct(
            String id,
            String name,
            String price,
            String stock
    ) {
        // Obtenir informació personal de l'usuari
        Map<String, Object> registeredProduct = new HashMap<>();
        registeredProduct.put("name", name);
        registeredProduct.put("price", price);
        registeredProduct.put("stock", stock);
        registeredProduct.put("picture_url", null);

        // Afegir-la a la base de dades
        mDb.collection("products").document(id).set(registeredProduct)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Sign up completion succeeded");
                        } else {
                            Log.d(TAG, "Sign up completion failed");
                        }
                    }
                });
    }

    /**
     * Mètode que guarda la Url d'una foto de perfil que un usuari hagi pujat
     * des de la HomeActivity a la BBDD. Concretament, es cridat pel HomeActivityViewModel.
     *
     * @param productId
     * @param pictureUrl
     */
    public void setPictureUrlOfProduct(String productId, String pictureUrl) {
        Map<String, Object> userEntry = new HashMap<>();
        userEntry.put("picture_url", pictureUrl);

        mDb.collection("products")
                .document(productId)
                .set(userEntry, SetOptions.merge())
                .addOnSuccessListener(documentReference -> {
                    Log.d(TAG, "Photo upload succeeded: " + pictureUrl);
                })
                .addOnFailureListener(exception -> {
                    Log.d(TAG, "Photo upload failed: " + pictureUrl);
                });
    }
}
