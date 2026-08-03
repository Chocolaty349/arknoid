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

    }

    public void init(int currentlevel){
        double W = GameConst.SCREEN_WIDTH;
        double H = GameConst.SCREEN_HEIGHT;
        double paddleStartX = (W - GameConst.PADDLE_WIDTH) / 2; // paddle o giua man hinh

        paddle = new Paddle(paddleStartX, GameConst.PADDLE_Y_OFFSET, GameConst.PADDLE_WIDTH, GameConst.PADDLE_HEIGHT,
                GameConst.PADDLE_SPEED, "org/chocolaty/arknoid/images/paddle.png");
        Ball ball = new Ball(0, 0, GameConst.BALL_RADIUS, GameConst.BALL_NORMAL_IMAGE);
        ballManager = new BallManager(ball, W, H);
        ballManager.resetMainBallToPaddle(paddle.getX() + paddle.getWidth() / 2, paddle.getY());
    }

    private void setFireModeAll(boolean v){
        fireBallFlag = v;
        for (Ball b : ballManager.getBalls())
            b.setFireMode(v);
    }

    private boolean isFireMode(){
        return fireBallFlag;
    }


}
