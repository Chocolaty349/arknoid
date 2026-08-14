package org.chocolaty.arknoid.view;

import javafx.scene.CacheHint;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class BackgroundRenderer {
    private MediaPlayer mediaPlayer;
    private MediaView mediaView;
    private Image overlay;
    private boolean visible = false;
    private Pane root;

    public BackgroundRenderer(Pane root) {
        this.root = root;
        mediaView = new MediaView();
        mediaView.setPreserveRatio(false);
        mediaView.setSmooth(true);

        // 🔥 FIX TÀN ẢNH #1: TRANSPARENT + NO CACHE
        mediaView.setStyle("-fx-background-color: transparent;");
        mediaView.setCache(false);
        mediaView.setCacheHint(CacheHint.SPEED);

        // 🔥 FIX #2: DƯỚI CÙNG TUYỆT ĐỐI
        root.getChildren().add(0, mediaView);

        // FULL RESPONSIVE
        mediaView.fitWidthProperty().bind(root.widthProperty());
        mediaView.fitHeightProperty().bind(root.heightProperty());
    }

    public void init(String videoPath, String overlayPath) {
        // VIDEO
        try {
            var videoUrl = getClass().getResource(videoPath);
            if (videoUrl != null) {
                try {
                    Media media = new Media(videoUrl.toExternalForm());
                    mediaPlayer = new MediaPlayer(media);
                    mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                    mediaPlayer.setMute(true);
                    mediaPlayer.setAutoPlay(false);
                    mediaView.setMediaPlayer(mediaPlayer);
                    System.out.println("✅ VIDEO READY");
                } catch (Exception e) {
                    System.err.println("❌ VIDEO ERROR: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.err.println("FAILED TO LOAD VIDEO: " + videoPath);
            System.err.println("Error: " + e.getMessage());
            mediaPlayer = null;
        }

        // OVERLAY PNG
        try {
            var overlayUrl = getClass().getResource(overlayPath);
            if (overlayUrl != null) {
                overlay = new Image(overlayUrl.toExternalForm(), true);
                System.out.println("✅ OVERLAY READY");
            }
        } catch (Exception e) {
            System.err.println("FAILED TO LOAD OVERLAY: " + overlayPath);
            overlay = null;
        }
    }

    public void render(GraphicsContext g) {
        if (overlay != null) {
            double w = g.getCanvas().getWidth();
            double h = g.getCanvas().getHeight();
            g.drawImage(overlay, 0, 0, w, h);
        }
    }

    public void show() {
        if (mediaPlayer != null) mediaPlayer.play();
        mediaView.setVisible(true);
        mediaView.setManaged(true);
        visible = true;
    }

    public void hide() {
        if (mediaPlayer != null) mediaPlayer.pause();
        mediaView.setVisible(false);
        mediaView.setManaged(false);
        visible = false;
    }

    public void cleanup() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
        }
    }

    public void dispose() {
        cleanup();

        try {
            if (root != null && mediaView != null) {
                root.getChildren().remove(mediaView);
            }
        } catch (Exception ignored) {}

        // 3) Clear reference
        mediaPlayer = null;
        mediaView = null;
        overlay = null;
        visible = false;
        root = null;
    }
}
