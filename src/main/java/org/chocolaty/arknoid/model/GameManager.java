package org.chocolaty.arknoid.model;

import org.chocolaty.arknoid.model.entity.Ball;
import org.chocolaty.arknoid.model.manager.BrickManager;
import org.chocolaty.arknoid.model.entity.Paddle;
import org.chocolaty.arknoid.model.manager.BallManager;
import org.chocolaty.arknoid.model.manager.PowerupManager;
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


}
