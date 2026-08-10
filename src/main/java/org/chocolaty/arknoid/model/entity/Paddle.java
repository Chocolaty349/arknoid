package org.chocolaty.arknoid.model.entity;

import javafx.animation.PauseTransition;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.util.Duration;
import org.chocolaty.arknoid.model.GameConst;

public class Paddle extends GameObject{
    private double x, y;
    private double width, height;
    private final double speed;
    private Image image;
    private final double originalWidth;
    private PauseTransition shrinkTimer;

    public Paddle(double x, double y, double width, double height, double speed, String imagePath) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.image = new Image(getClass().getResource(imagePath).toExternalForm());
        originalWidth = width;
    }

    private void loadImage(String imagePath){
        try {
            var url = getClass().getResource(imagePath);
            if(url != null){
                image = new Image(url.toExternalForm(), width, height, true, true);
                System.out.println("loaded: " + imagePath);
            }
            else throw new IllegalArgumentException("Image not found");
        }
        catch (Exception e){
            System.err.println("failed to load: " + imagePath);
            image = null;
        }
    }
    public void expandTemporary(double factor, double durationSec){
        double newWidth = Math.min(GameConst.SCREEN_WIDTH * 0.9, originalWidth * factor);
        setWidth(newWidth);

        if(shrinkTimer != null)
            shrinkTimer.stop();

        shrinkTimer = new PauseTransition(Duration.seconds(durationSec));
        shrinkTimer.setOnFinished(e -> setWidth(originalWidth));
        shrinkTimer.play();
    }

    public void setWidth(double newW) {
        // giữ nguyên tâm theo trục X
        double cx = x + width / 2.0;
        width = newW;

        // đặt lại x theo tâm cũ
        x = cx - width / 2.0;

        //không vượt biên
        clamp(GameConst.BORDER_OFFSET_X, GameConst.SCREEN_WIDTH - GameConst.BORDER_OFFSET_X);
    }

    public void moveLeft(double dt) {
        x -= speed * dt;
    }

    public void moveRight(double dt) {
        x += speed * dt;
    }

    public void clamp(double minX, double maxX) {
        x = Math.max(minX, Math.min(maxX - width, x));
    }

    public void render(GraphicsContext g) {
        g.drawImage(image, x, y, width, height);
    }

    //getter
    public double getX() { return x; }
    public double getY() { return y; }
    public double getWidth() { return width; }
    public double getHeight() { return height; }

}


