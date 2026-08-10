package org.chocolaty.arknoid.controller;

import org.chocolaty.arknoid.model.GameConst;
import org.chocolaty.arknoid.model.system.LevelManager;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class LevelController {
    @FXML private Canvas levelCanvas;
    @FXML private AnchorPane root;

    private GraphicsContext g;
    private AnimationTimer renderLoop;
    private boolean navigating = false;

    // Đổi đường dẫn cho đúng chỗ ảnh của bạn
    private static final String BG_PATH       = GameConst.LEVEL_BG_IMAGE;
    private static final String STAR_FULL     = GameConst.LEVEL_STAR_IMAGE;
    private static final String STAR_EMPTY    = GameConst.LEVEL_STAREMT_IMAGE;

    private Image bgImage, starImg, starEmptyImg;

    @FXML
    private void initialize() {
        // canvas full window
        levelCanvas.widthProperty().bind(root.widthProperty());
        levelCanvas.heightProperty().bind(root.heightProperty());

        // close → stop loop
        root.sceneProperty().addListener((o, os, ns) -> {
            if (ns != null) {
                Stage s = (Stage) ns.getWindow();
                if (s != null) {
                    s.setOnCloseRequest(e -> {
                        try { if (renderLoop != null) renderLoop.stop(); } catch (Exception ignored) {}
                        Platform.exit();
                    });
                }
            }
        });

        g = levelCanvas.getGraphicsContext2D();
        loadImages();

        // Vẽ mỗi frame (hoặc bạn có thể gọi redraw() khi resize là đủ)
        startRenderLoop();

        // focus + click
        levelCanvas.setFocusTraversable(true);
        levelCanvas.requestFocus();
        levelCanvas.setOnMouseClicked(e -> handleClick(e.getX(), e.getY()));

        // redraw khi resize
        levelCanvas.widthProperty().addListener((obs, o, n) -> redraw());
        levelCanvas.heightProperty().addListener((obs, o, n) -> redraw());
    }

    private void loadImages() {
        bgImage      = load(BG_PATH);
        starImg      = load(STAR_FULL);
        starEmptyImg = load(STAR_EMPTY);
    }

    private Image load(String path) {
        try {
            var url = getClass().getResource(path);
            return (url != null) ? new Image(url.toExternalForm(), true) : null;
        } catch (Exception e) {
            System.err.println("Failed to load: " + path + " -> " + e.getMessage());
            return null;
        }
    }

