package com.example.noblesse;

import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;

public class FirestoreConnection {

    private final Firestore db;
    private static FirestoreConnection instance = null;

    public static FirestoreConnection getInstance(){
        if (instance == null)
            instance = new FirestoreConnection();

        return instance;
    }

    private FirestoreConnection(){
        db = FirestoreClient.getFirestore();
    }

    public Firestore getDb() {
        return db;
    }

}
