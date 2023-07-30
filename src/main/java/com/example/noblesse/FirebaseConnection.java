package com.example.noblesse;

import java.io.IOException;
import java.io.InputStream;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

public class FirebaseConnection {

    private static FirebaseConnection instance = null;

    public static FirebaseConnection getInstance(){
        if (instance == null)
            instance = new FirebaseConnection();

        return instance;
    }

    private FirebaseConnection(){
        try {
            InputStream serviceAccount = getClass().getResourceAsStream("/com/example/noblesse/apiKey");
            assert serviceAccount != null;
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://noblesse-2e87d-default-rtdb.europe-west1.firebasedatabase.app")
                    .build();

            FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
