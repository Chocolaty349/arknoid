    module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.prefs;

    requires com.almasb.fxgl.all;
        requires javafx.graphics;

        opens org.chocolaty.arknoid to javafx.fxml;
    exports org.chocolaty.arknoid;
}