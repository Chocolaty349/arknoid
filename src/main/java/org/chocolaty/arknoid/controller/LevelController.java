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

    private void startRenderLoop() {
        renderLoop = new AnimationTimer() {
            @Override public void handle(long now) { renderMenu(); }
        };
        renderLoop.start();
    }

    private void redraw() { renderMenu(); }

    private void renderMenu() {
        double w = levelCanvas.getWidth();
        double h = levelCanvas.getHeight();

        // === BACKGROUND ===
        if (bgImage != null) {
            g.drawImage(bgImage, 0, 0, w, h);
        } else {
            g.setFill(Color.rgb(15, 25, 50));
            g.fillRect(0, 0, w, h);
        }

/*        // Title
        g.setFill(Color.WHITE);
        g.setTextAlign(TextAlignment.CENTER);
        g.setFont(Font.font("System", FontWeight.BOLD, 38));
        g.fillText("CHỌN LEVEL", w/2, 90);*/

        // === GRID 2x2 với stroke (kẻ ô vuông bằng bút) ===
        int rows = 2, cols = 2; // 4 level
        double padX = 120, padY = 150; // lề trong
        double cellW = (w - padX*2) / cols;
        double cellH = (h - padY*2) / rows;

        // bút vẽ khung
        g.setStroke(Color.WHITE);
        g.setLineWidth(3.0);

        int levelIdx = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                levelIdx++;
                double x = padX + c*cellW;
                double y = padY + r*cellH;

                // khung ô vuông (stroke-only)
                double radius = 18;
                g.strokeRoundRect(x, y, cellW, cellH, radius, radius);

                // Tên level
                g.setFill(Color.WHITE);
                g.setFont(Font.font("System", FontWeight.BOLD, Math.max(20, cellH*0.12)));
                g.fillText("LEVEL " + levelIdx, x + cellW/3, y + cellH*0.28);

                // Sao (3 sao tối đa)
                int stars = LevelManager.getStars(levelIdx); // 0..3
                double starSize = Math.min(cellW, cellH) * 0.16;
                double gap = starSize * 0.25;
                double totalW = starSize*GameConst.MAX_STARS + gap*(GameConst.MAX_STARS-1);
                double sx = x + (cellW - totalW)/2;
                double sy = y + cellH*0.50;

                for (int i = 0; i < GameConst.MAX_STARS; i++) {
                    Image img = (i < stars) ? starImg : starEmptyImg;
                    if (img != null) {
                        g.drawImage(img, sx + i*(starSize+gap), sy, starSize, starSize);
                    } else {
                        // fallback
                        g.setFill(i < stars ? Color.GOLD : Color.GRAY);
                        g.fillOval(sx + i*(starSize+gap), sy, starSize, starSize);
                    }
                }
            }
        }
    }

    private void handleClick(double mouseX, double mouseY) {
        // Tính hit-test theo GRID ở trên
        double w = levelCanvas.getWidth();
        double h = levelCanvas.getHeight();
        int rows = 2, cols = 2;
        double padX = 120, padY = 150;
        double cellW = (w - padX*2) / cols;
        double cellH = (h - padY*2) / rows;

        // ô nào được click?
        if (mouseX < padX || mouseX > w - padX || mouseY < padY || mouseY > h - padY) return;

        int col = (int) ((mouseX - padX) / cellW);
        int row = (int) ((mouseY - padY) / cellH);
        int level = row * cols + col + 1; // 1..4

        if (level >= 1 && level <= 4 && LevelManager.isLevelUnlocked(level)) {
            loadGameLevel(level);
        }
    }

    private void loadGameLevel(int level) {
        if (navigating) return;
        navigating = true;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/chocolaty/arknoid/view/game-view.fxml"));
            AnchorPane gameRoot = loader.load();

            GameController gc = loader.getController();
            gc.setCurrentLevel(level);

            Stage s = (Stage) root.getScene().getWindow();
            s.setScene(new Scene(gameRoot, root.getWidth(), root.getHeight()));
            s.show();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (renderLoop != null) renderLoop.stop();
        }
    }
}
