module org.example.arknoid {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;

    opens org.example.arknoid to javafx.fxml;
    exports org.example.arknoid;
}