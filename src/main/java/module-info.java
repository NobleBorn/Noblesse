module com.example.noblesse {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires firebase.admin;
    requires com.google.auth.oauth2;
    requires com.google.auth;
    requires google.cloud.firestore;
    requires google.cloud.core;
    requires org.apache.commons.csv;
    requires org.json;
    requires com.google.api.apicommon;
    requires commons.validator;

    opens com.example.noblesse to javafx.fxml, google.cloud.firestore;
    exports com.example.noblesse;
}