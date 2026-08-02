module org.example {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;
    requires java.desktop;

    opens org.chocolaty.arknoid to javafx.fxml;
    exports org.chocolaty.arknoid;
}