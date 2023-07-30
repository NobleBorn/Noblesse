package com.example.noblesse;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;

public class FirestoreManager {

    public void storeUserInfo(String userId, User user) {
        // Get a Firestore instance
        Firestore db = FirestoreClient.getFirestore();

        // Reference to the "users" collection
        CollectionReference usersCollection = db.collection("users");

        // Reference to a new document for the user with the specified userId
        DocumentReference userDocRef = usersCollection.document(userId);

        // Set the user object as the data in the document
        userDocRef.set(user);
    }
}
