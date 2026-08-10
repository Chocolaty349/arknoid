package org.chocolaty.arknoid.model.entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Brick {
    private double x, y;
    private double width, height;
    private int health;
    private Color color;
    private boolean destroyed;
    private String powerUpType;

    public Brick(double x, double y, double width, double height, int health, Color color, String powerUpType) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.health = health;
        this.color = color;
        this.destroyed = false;
        this.powerUpType = powerUpType;
    }

    public void hit() {
        health--;

        if (health <= 0) {
            destroyed = true;
        }
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(this.color);
        gc.fillRect(this.x, this.y, width, height);
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(1.5);
        gc.strokeRect(this.x, this.y, this.width, this.height);
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public double getWidth() { return width; }
    public double getHeight() { return height; }
    public boolean isDestroyed() { return destroyed; }
    public String getPowerUpType() { return powerUpType; }
    public int getHealth() { return health; }
}