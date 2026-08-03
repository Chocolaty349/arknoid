package org.chocolaty.arknoid.model;

import org.chocolaty.arknoid.model.entity.Ball;
import org.chocolaty.arknoid.model.entity.Paddle;
import org.chocolaty.arknoid.model.manager.BallManager;
import org.chocolaty.arknoid.model.manager.BrickManager;
import org.chocolaty.arknoid.model.manager.PowerupManager;
import org.chocolaty.arknoid.model.system.LevelManager;
import org.chocolaty.arknoid.view.BackgroundRenderer;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GameManager {
    private final Canvas canvas;
    private final GraphicsContext g;
    private final BackgroundRenderer background;
    private final BrickManager brickManager;

    private Paddle paddle;
    private BallManager ballManager;
    private AnimationTimer loop;
    private boolean navigating = false;

    private boolean left, right;

    private GameState gameState;
    private Stage stage;
    private boolean isGameOver;
    private PowerupManager powerUps;
    private boolean fireBallFlag = false;
    private boolean spaceDown = false;
    private boolean spacePrev = false;

    public GameManager(Canvas canvas, Stage stage) {
        this.canvas = canvas;
        this.g = canvas.getGraphicsContext2D();
        this.background = new BackgroundRenderer((Pane) canvas.getParent());
        this.brickManager = new BrickManager();
        this.gameState = new GameState();
        this.stage = stage;
        this.isGameOver = false;
    }

    public void init(int currentLevel) {
        double W = GameConst.SCREEN_WIDTH;
        double H = GameConst.SCREEN_HEIGHT;
        double paddleStartX = (W - GameConst.PADDLE_WIDTH) / 2;

        paddle = new Paddle(paddleStartX , GameConst.PADDLE_Y_OFFSET,
                GameConst.PADDLE_WIDTH, GameConst.PADDLE_HEIGHT, GameConst.PADDLE_SPEED,
                "/org/chocolaty/arknoid/images/paddle.png");
        Ball ball = new Ball(0, 0, GameConst.BALL_RADIUS, "/org/chocolaty/arknoid/images/ball_normal.png");
        ballManager = new BallManager(ball, W, H);  // TAO MANAGER
        ballManager.getBall().reset(paddle.getX() + paddle.getWidth()/2, paddle.getY());

        String levelPath = resolveLevelPath(currentLevel);
        brickManager.loadLevel(levelPath);

        background.init("/org/chocolaty/arknoid/view/bg.mp4",
                "/org/chocolaty/arknoid/view/frame_overlay.png");

        powerUps = new PowerupManager(
                ballManager,
                paddle,
                this::setFireModeAll,   // setter
                this::isFireMode        // getter
        );

        gameState.setCurrentLevel(currentLevel);
    }

    /** Tra ve path theo level, kem kiem tra ton tai va fallback ve level1 */
    private String resolveLevelPath(int level) {
        int clamped = Math.max(1, Math.min(4, level)); // chi cho 1..4
        String base = "/org/chocolaty/arknoid/levels/level" + clamped + ".txt";
        return base;
    }

    private void setFireModeAll(boolean v) {
        fireBallFlag = v;
        for (Ball b : ballManager.getBalls()) {
            b.setFireMode(v);
        }
    }

    private boolean isFireMode() { return fireBallFlag; }

    public void start() {
        loop = new AnimationTimer() {
            long last = 0;
            @Override public void handle(long now) {
                if (last == 0) { last = now; return; }
                double dt = (now - last) / 1e9;
                update(dt);
                render();
                last = now;
            }
        };
        loop.start();
    }

    private void update(double dt) {
        if (isGameOver) return;

        if (left) paddle.moveLeft(dt);
        if (right) paddle.moveRight(dt);
        paddle.clamp(GameConst.BORDER_OFFSET_X, GameConst.SCREEN_WIDTH - GameConst.BORDER_OFFSET_X);

        boolean spacePressed = spaceDown && !spacePrev;
        spacePrev = spaceDown;
        if (spacePressed && ballManager.anySticky()) {
            ballManager.release();
        }

        ballManager.update(dt, paddle);

        powerUps.update(dt);

        ballManager.removeFallenBallsExceptLast(GameConst.SCREEN_HEIGHT);

        if (ballManager.isLastBallLost(GameConst.SCREEN_HEIGHT)) {
            gameState.loseLife();

            if (gameState.isGameOver()) {
                if (!navigating) {
                    isGameOver = true;
                    navigating = true;
                    switchToMenu();
                }
                return;
            }

            ballManager.resetMainBallToPaddle(
                    paddle.getX() + paddle.getWidth() / 2,
                    paddle.getY() - ballManager.getBall().getRadius() - GameConst.COLLISION_PUSH_OUT
            );
        }

        if (brickManager.isLevelCleared() && gameState.getLives() > 0) {
            if (!navigating) {
                int stars = gameState.getStarsEarned();
                LevelManager.saveStars(gameState.getCurrentLevel(), stars);
                navigating = true;
                switchToMenu();
            }
            return;
        }

        brickManager.updateAll(ballManager.getBalls(), fireBallFlag, destroyedBrick -> {
            powerUps.maybeDropFrom(destroyedBrick);
        });
    }

    private void render() {
        g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        background.render(g);
        paddle.render(g);
        ballManager.render(g);
        if (fireBallFlag) {
            for (Ball b : ballManager.getBalls()) b.renderFireball(g);
        }
        brickManager.render(g);
        powerUps.render(g);
        gameState.render(g);
    }

    public void shutdown() {
        if (loop != null) loop.stop();
        background.cleanup();
        System.out.println("GameManager shutdown");
    }

    public void handleKeyPressed(KeyCode code) {
        if (code == KeyCode.LEFT) left = true;
        if (code == KeyCode.RIGHT) right = true;
        if (code == KeyCode.SPACE) spaceDown = true;
    }

    public void handleKeyReleased(KeyCode code) {
        if (code == KeyCode.LEFT) left = false;
        if (code == KeyCode.RIGHT) right = false;
        if (code == KeyCode.SPACE) spaceDown = false;
    }

    public BackgroundRenderer getBackground() {
        return background;
    }

    private void switchToMenu() {
        // dam bao chi chay 1 lan
        if (loop != null) {
            try { loop.stop(); } catch (Exception ignored) {}
            loop = null;
        }
        try {
            if (background != null) background.dispose();
        } catch (Exception ignored) {}

        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/org/chocolaty/arknoid/view/level-view.fxml"));
                AnchorPane menuRoot = loader.load();

                stage.setScene(new Scene(menuRoot, GameConst.SCREEN_WIDTH, GameConst.SCREEN_HEIGHT));
                stage.show();
            } catch (Exception e) {
                System.err.println("Switch to menu failed: " + e.getMessage());
            }
        });
    }

    public void dispose() {
        // dung loop
        if (loop != null) {
            try { loop.stop(); } catch (Exception ignored) {}
            loop = null;
        }
        // dung background/media neu co
        try {
            if (background != null) background.dispose();
        } catch (Exception ignored) {}
        if (powerUps != null) powerUps.clearAll();
    }
}
