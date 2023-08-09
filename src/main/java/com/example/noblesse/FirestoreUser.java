package com.example.noblesse;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class FirestoreUser {

    private final CollectionReference usersCollection;
    private DocumentReference userDocRef;
    private final Firestore db;

    public FirestoreUser(){
        db = FirestoreConnection.getInstance().getDb();

        // Reference to the "users" collection
        usersCollection = db.collection("users");
    }

    public boolean storeUserInfo(String userId, User user) throws ExecutionException, InterruptedException {
        // Reference to a new document for the user with the specified userId
        userDocRef = usersCollection.document(userId);
        boolean exists = userExists();
        boolean success = !exists;

        if (!exists) {
            // Set the user object as the data in the document
            userDocRef.set(user);
        }

        try {
            closeConnection();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        return success;
    }

    private boolean userExists() throws ExecutionException, InterruptedException {
        ApiFuture<DocumentSnapshot> future = userDocRef.get();

        DocumentSnapshot document = future.get(); // This blocks until the operation is complete
        return document.exists();
    }

    public ArrayList<String> getUserInfo(String userId) throws ExecutionException, InterruptedException, LogInException {
        DocumentReference userDoc = usersCollection.document(userId);
        ApiFuture<DocumentSnapshot> future = userDoc.get();

        DocumentSnapshot document = future.get();

        ArrayList<String> info = new ArrayList<>();

        if (document.exists()){
            info.add(document.getString("email"));
            info.add(document.getString("username"));
            info.add(document.getString("password"));

            try {
                closeConnection();
            } catch (Exception e){
                System.out.println(e.getMessage());
            }

            return info;

        } else {
            throw new LogInException("This user does not exists!");
        }
    }

    private void closeConnection() throws Exception {
        db.close();
    }
}
