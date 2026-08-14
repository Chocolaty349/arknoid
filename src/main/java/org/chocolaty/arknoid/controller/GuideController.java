package org.chocolaty.arknoid.controller;

import org.chocolaty.arknoid.view.BgmManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class GuideController {

    @FXML private AnchorPane root;
    @FXML private ImageView img;
    @FXML private Button btnPrev, btnNext;

    // 2 trang
    private final List<String> pages = List.of(
            "/org/chocolaty/arknoid/images/guide/how_to_play.png",
            "/org/chocolaty/arknoid/images/guide/power_ups.png"
    );
    private int index = 0;

    @FXML
    private void initialize() {
        // Ảnh auto fit theo cửa sổ
        img.fitWidthProperty().bind(root.widthProperty());
        img.fitHeightProperty().bind(root.heightProperty());
        img.setPreserveRatio(true);       // (khuyên dùng) giữ tỉ lệ ảnh

        root.setFocusTraversable(true);
        root.requestFocus();

        refresh();
    }

    private void refresh() {
        // Load ảnh trang hiện tại
        img.setImage(new Image(Objects.requireNonNull(
                getClass().getResourceAsStream(pages.get(index)),
                "Missing image: " + pages.get(index))));

        // --- Ẩn/hiện nút theo trang ---
        boolean isFirst = (index == 0);
        boolean isLast  = (index == pages.size() - 1);

        btnPrev.setVisible(!isFirst);
        btnPrev.setManaged(!isFirst);
        btnNext.setVisible(!isLast);
        btnNext.setManaged(!isLast);
    }

    // ===== Buttons =====
    @FXML private void onPrev() {
        if (index > 0) {
            index--;
            refresh();
        }
    }
    @FXML private void onNext() {
        if (index < pages.size() - 1) {
            index++;
            refresh();
        }
    }

    @FXML
    private void onBack() {
        switchTo("/org/chocolaty/arknoid/view/menu-view.fxml");
        // Không cần đổi nhạc ở đây vì MenuController.initialize() đã playMenu().
        // (Gọi lại cũng không sao)
    }

    // ===== Hotkeys: ←/→, ESC =====
    @FXML
    private void onKeyPressed(KeyEvent e) {
        if (e.getCode() == KeyCode.RIGHT || e.getCode() == KeyCode.D) onNext();
        else if (e.getCode() == KeyCode.LEFT || e.getCode() == KeyCode.A) onPrev();
        else if (e.getCode() == KeyCode.ESCAPE) onBack();
        else if (e.getCode() == KeyCode.M) BgmManager.get().setMuted(!BgmManager.get().isMuted()); // (tuỳ chọn) mute nhanh
    }

    private void switchTo(String fxmlPath) {
        try {
            Stage stage = (Stage) root.getScene().getWindow();
            Parent newRoot = FXMLLoader.load(Objects.requireNonNull(
                    getClass().getResource(fxmlPath), "FXML not found: " + fxmlPath));
            stage.setScene(new Scene(newRoot, stage.getScene().getWidth(), stage.getScene().getHeight()));
            stage.centerOnScreen();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
