package org.chocolaty.arknoid.controller;

import org.chocolaty.arknoid.view.BgmManager; // <-- thêm
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MenuController {
    @FXML private AnchorPane root;
    @FXML private ImageView bg;
    @FXML private Pane uiLayer;
    @FXML private Button btnGuide, btnPlay, btnQuit;
    @FXML private ImageView iconGuide, iconPlay, iconQuit;

    private static final double BASE_W = 900.0;
    private static final double BASE_H = 650.0;

    @FXML
    public void initialize() {
        // === phát nhạc menu ===
        BgmManager.get().playMenu(); // <-- thêm

        bg.fitWidthProperty().bind(root.widthProperty());
        bg.fitHeightProperty().bind(root.heightProperty());
        root.widthProperty().addListener((o, ov, nv) -> scaleUi());
        root.heightProperty().addListener((o, ov, nv) -> scaleUi());
        scaleUi();
    }

    private void scaleUi() {
        if (uiLayer == null || root == null) return;
        double W = root.getWidth(), H = root.getHeight();
        if (W <= 0 || H <= 0) return;
        double k = Math.min(W / BASE_W, H / BASE_H);
        uiLayer.setScaleX(k); uiLayer.setScaleY(k);
        uiLayer.setLayoutX((W - BASE_W * k) / 2.0);
        uiLayer.setLayoutY((H - BASE_H * k) / 2.0);
    }

    @FXML
    private void onKeyPressed(KeyEvent e) {
        if (e.getCode() == KeyCode.ENTER) onPlay(null);
        else if (e.getCode() == KeyCode.G) onGuide(null);
        else if (e.getCode() == KeyCode.ESCAPE) onQuit(null);
        else if (e.getCode() == KeyCode.M) BgmManager.get().setMuted(!BgmManager.get().isMuted()); // mute nhanh (tuỳ chọn)
    }

    @FXML
    private void onPlay(ActionEvent evt) {
        // chuyển scene trước
        switchTo("/org/chocolaty/arknoid/view/level-view.fxml", evt);
        // rồi đổi nhạc in-game
        BgmManager.get().playGame(); // <-- thêm
    }

    @FXML
    private void onGuide(ActionEvent evt) {
        switchTo("/org/chocolaty/arknoid/view/guide-view.fxml", evt);
    }

    @FXML
    private void onQuit(ActionEvent evt) {
        BgmManager.get().stop(); // <-- dừng nhạc khi thoát
        Stage stage = getStageFromEventOrRoot(evt);
        if (stage != null) stage.close();
    }

    private void switchTo(String fxmlPath, ActionEvent evt) {
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(
                    getClass().getResource(fxmlPath), "FXML not found: " + fxmlPath));
            Parent newRoot = loader.load();
            Stage stage = getStageFromEventOrRoot(evt);
            if (stage == null) throw new IllegalStateException("Stage not found");
            Scene current = stage.getScene();
            if (current == null) stage.setScene(new Scene(newRoot, BASE_W, BASE_H));
            else stage.setScene(new Scene(newRoot, current.getWidth(), current.getHeight()));
            stage.centerOnScreen();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private Stage getStageFromEventOrRoot(ActionEvent evt) {
        if (evt != null) {
            Object src = evt.getSource();
            if (src instanceof Node node) return (Stage) node.getScene().getWindow();
        }
        if (root != null && root.getScene() != null) return (Stage) root.getScene().getWindow();
        return javafx.stage.Window.getWindows().stream()
                .filter(w -> w instanceof Stage && ((Stage) w).isShowing())
                .map(w -> (Stage) w).findFirst().orElse(null);
    }
}
