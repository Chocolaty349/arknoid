package org.chocolaty.arknoid.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import org.chocolaty.arknoid.model.GameManager;

public class GameController {
    @FXML
    private Canvas canvas;
    @FXML
    private AnchorPane rootPane;

    @FXML
    private Button btnStart;
    @FXML
    private Label lblScore;

    private GameManager game;
    private Stage stage;
    private boolean gameInitialized = false;

    private int currentLevel = 1;

    private void initialize() {
        canvas.widthProperty().bind(rootPane.widthProperty());
        canvas.heightProperty().bind(rootPane.heightProperty());

        rootPane.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                Stage s = (Stage) newScene.getWindow();
                if (s != null) {
                    s.setOnCloseRequest(ev -> {
                        try {
                            if (game != null)
                                game.dispose();
                        } catch (Exception ignored) {}
                        Platform.exit();
                    });
                }
            }
        });

        rootPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.doubleValue() > 100 && !gameInitialized)
                Platform.runLater(this::initGame);
        });

        if (btnStart != null)
            btnStart.setVisible(true);
    }

    void initGame() {
        if (gameInitialized) return;
        gameInitialized = true;

        if (rootPane.getScene() == null) {
            System.err.println("Scene not ready in initGame - retrying...");
            Platform.runLater(this::initGame);
            return;
        }

        canvas.widthProperty().bind(rootPane.widthProperty());
        canvas.heightProperty().bind(rootPane.heightProperty());

        stage = (Stage) rootPane.getScene().getWindow();
        game = new GameManager(canvas, stage);
        game.init(currentLevel);

        stage.setOnCloseRequest(e -> {
            game.shutdown();
            Platform.exit();
        });

        canvas.setFocusTraversable(true);
        Platform.runLater(canvas::requestFocus);
        canvas.setOnMouseClicked(e -> canvas.requestFocus());

        game.start();
    }
}
