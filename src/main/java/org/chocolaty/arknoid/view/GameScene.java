package org.chocolaty.arknoid.view;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

import java.util.HashSet;
import java.util.Set;

public class GameScene {
    private final Group root;
    private final Canvas canvas;
    private final GraphicsContext gc;
    private final Set<KeyCode> activeKeys = new HashSet<>();
    private AnimationTimer gameLoop;

    public GameScene() {
        root = new Group();
        canvas = new Canvas(800, 600);
        gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);

        setupInput();
    }

    private void setupInput() {
        // Bắt sự kiện trên Canvas để chắc chắn nhận focus
        canvas.setFocusTraversable(true);
        canvas.setOnKeyPressed(e -> activeKeys.add(e.getCode()));
        canvas.setOnKeyReleased(e -> activeKeys.remove(e.getCode()));
        canvas.requestFocus();
    }

    public Group getRoot() {
        return root;
    }

    public void startGameLoop() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
                render();
            }
        };
        gameLoop.start();
    }

    private void update() {
        // TODO: cập nhật game state (ball, paddle, bricks...)
        if (activeKeys.contains(KeyCode.LEFT)) {
            // move paddle left
        }
        if (activeKeys.contains(KeyCode.RIGHT)) {
            // move paddle right
        }
    }

    private void render() {
        // ví dụ clear màn hình
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        // TODO: vẽ paddle/ball/brick...
    }
}
