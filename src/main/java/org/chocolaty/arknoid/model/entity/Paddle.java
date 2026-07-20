package org.chocolaty.arknoid.model.entity;

import javafx.scene.image.Image;

public class Paddle extends GameObject{
    private double x, y, height, speed;
    private final double normalWidth;
    private double extendWidth;
    private Image paddleImage;
    private Image paddleExtendImage;
    private int shirkCountdown;

    public Paddle(double x, double y, double normalWidth, double height, String imagePath){
        this.x = x;
        this.y = y;
        this.normalWidth = normalWidth;
        this.height = height;
        this.paddleImage = new Image(getClass().getResource(imagePath).toExternalForm());
    }

}
