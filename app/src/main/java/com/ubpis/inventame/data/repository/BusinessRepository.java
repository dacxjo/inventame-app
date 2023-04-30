package com.ubpis.inventame.data.repository;

import android.graphics.Bitmap;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ubpis.inventame.data.model.Business;

import java.io.ByteArrayOutputStream;

public class BusinessRepository {

    private static final BusinessRepository instance = new BusinessRepository();

    private final CollectionReference usersCollection;

    private BusinessRepository() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        this.usersCollection = db.collection("users");
    }

    public static BusinessRepository getInstance() {
        return instance;
    }

    public Task<Void> addBusiness(Business newBusiness) {
        DocumentReference docRef = usersCollection.document(newBusiness.getId());
        return docRef.set(newBusiness);
    }

    // upload logo
    public void uploadLogo(String uid, Bitmap logoBitmap, OnSuccessListener onSuccessListener) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        logoBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] logoData = baos.toByteArray();
        StorageReference logoRef = FirebaseStorage.getInstance().getReference().child("logos/" + uid + ".png");
        UploadTask uploadTask = logoRef.putBytes(logoData);
        uploadTask.addOnSuccessListener(taskSnapshot -> logoRef.getDownloadUrl().addOnSuccessListener(onSuccessListener));
    }

}