package org.chocolaty.arknoid;

import org.chocolaty.arknoid.model.GameConst;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Platform.setImplicitExit(true); // NEW

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/chocolaty/arknoid/view/menu-view.fxml"));
        AnchorPane root = loader.load();
        Scene scene = new Scene(root, GameConst.SCREEN_WIDTH, GameConst.SCREEN_HEIGHT);
        stage.setScene(scene);
        stage.setTitle("Arkanoid 7");
        stage.setResizable(false);
        stage.show();

        stage.setOnCloseRequest(e -> {
            Platform.exit();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
