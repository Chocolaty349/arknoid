package org.chocolaty.arknoid.model.entity;

import javafx.scene.canvas.GraphicsContext;
import org.chocolaty.arknoid.model.GameConst;
import javafx.scene.image.Image;

public class Ball extends GameObject {
    private double x, y, radius, velocity_x, velocity_y;
    private boolean fireMode = false;
    private boolean sticky = true; // ball on paddle
    private Image imageNormalBall;
    private Image imageFireBall;
    public Ball(){}

    public Ball(double x, double y, double radius){
    }

    public  Ball(double x, double y, double radius, String imagePath){
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.velocity_x = 0;
        this.velocity_y = 0;
        loadImage(imagePath);
        loadImageFire(GameConst.BALL_FIRE_IMAGE);
    }


    public boolean isSticky() {
        return sticky;
    }

    public void setSticky(boolean sticky) {
        this.sticky = sticky;
    }

    public double getVelocity_y() {
        return velocity_y;
    }

    public void setVelocity_y(double velocity_y) {
        this.velocity_y = velocity_y;
    }

    public double getVelocity_x() {
        return velocity_x;
    }

    public void setVelocity_x(double velocity_x) {
        this.velocity_x = velocity_x;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public void setY(double y) {
        this.y = y;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public void setX(double x) {
        this.x = x;
    }

    public boolean isFireMode() {
        return fireMode;
    }

    public void setFireMode(boolean fireMode) {
        this.fireMode = fireMode;
    }

    private void loadImage(String imagePath){
        try{
            var url = getClass().getResource(imagePath);
            if(url != null){
                imageNormalBall = new Image(url.toExternalForm(), radius * 2, radius * 2, true, true);
                System.out.println("loaded: " + imagePath);
            }
            else
                throw new IllegalArgumentException("image not found");
        }
        catch (Exception e){
            System.err.println("load failed: " + imagePath);
            imageNormalBall = null;
        }
    }

    private void loadImageFire(String imagePath) {
        try {
            var url = getClass().getResource(imagePath);
            if (url != null) {
                imageFireBall = new Image(url.toExternalForm(), radius * 2, radius * 2, true, true);
                System.out.println("loaded: " + imagePath);
            } else {
                throw new IllegalArgumentException("image not found");
            }
        } catch (Exception e) {
            System.err.println("load failed: " + imagePath);
            imageFireBall = null;
        }
    }

    public void render(GraphicsContext g) {
        g.drawImage(imageNormalBall, x - radius, y - radius, radius * 2, radius * 2);
    }

    public void renderFireball(GraphicsContext g) {
        g.drawImage(imageFireBall, x - radius, y - radius, radius * 2, radius * 2);
    }

    public void launch(double vx, double vy) {
        this.sticky = false;
        this.velocity_x = vx;
        this.velocity_y = vy;
    }

    public void update(double delta_time, double paddleX, double paddleY) {
        if (sticky) {
            x = paddleX;
            y = paddleY - radius - 2;
        } else {
            x += velocity_x * delta_time; // van toc * thoi gian = vi tri moi
            y += velocity_y * delta_time;
        }
    }

    public void setVelocity(double velocity_x, double velocity_y){
        this.velocity_x = velocity_x;
        this.velocity_y = velocity_y;
    }

    public void setPosition(double x, double y){
        this.x = x;
        this.y = y;
    }

}
