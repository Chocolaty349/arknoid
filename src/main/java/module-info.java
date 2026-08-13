    module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.prefs;

    requires com.almasb.fxgl.all;
        requires javafx.graphics;
    requires java.desktop;

        opens org.chocolaty.arknoid to javafx.fxml;
    opens org.chocolaty.arknoid.controller to javafx.fxml;
    exports org.chocolaty.arknoid;
}