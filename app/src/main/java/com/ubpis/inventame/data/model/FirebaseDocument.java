package com.ubpis.inventame.data.model;

import com.google.firebase.Timestamp;

public interface FirebaseDocument {
    String getId();

    void setId(String id);

    Timestamp getCreatedAt();

    void setCreatedAt(Timestamp createdAt);

    Timestamp getDeletedAt();

    void setDeletedAt(Timestamp deletedAt);
}
