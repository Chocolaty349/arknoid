package org.chocolaty.arknoid.model.entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Brick extends GameObject {
    private final double x, y;
    private final double width, height;
    private final BrickType type;
    private int health;
    private boolean destroyed = false;
    private Image image;

    public Brick(double x, double y, double width, double height, BrickType type) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.type = type;
        this.health = type.getMaxHealth();
        loadImage();
    }

    private void loadImage() {
        try {
            var url = getClass().getResource(type.getImagePath());
            if (url != null) {
                image = new Image(url.toExternalForm(), width, height, true, true);
                System.out.println("Brick image loaded: " + type.getImagePath());
            } else {
                throw new IllegalArgumentException("Resource not found");
            }
        } catch (Exception e) {
            System.err.println("FAILED TO LOAD BRICK IMAGE: " + type.getImagePath());
            System.err.println("Using fallback color rendering");
            image = null;
        }
    }

    public void hit() {
        damage(1);
    }

    public void damage(int amount) {
        if (!isDestructible() || destroyed) return;
        health -= Math.max(1, amount);
        if (health <= 0) destroyed = true;
    }

    public void render(GraphicsContext g) {
        if (destroyed) return;
        g.drawImage(image, x, y, width, height);

        if (health > 1 && type != BrickType.INDESTRUCTIBLE) {
            g.setFill(javafx.scene.paint.Color.WHITE);
            g.fillText(String.valueOf(health), x + width/2 - 6, y + height/2 + 6);
        }
    }

    // Getters
    public double getX() { return x; }
    public double getY() { return y; }
    public double getWidth() { return width; }
    public double getHeight() { return height; }
    public boolean isDestroyed() { return destroyed; }
    public BrickType getType() { return type; }

    public boolean isDestructible() {
        return type != BrickType.INDESTRUCTIBLE;
    }

    public double getCenterX() {
        return x + width / 2;
    }
    public double getCenterY() {
        return y + height / 2;
    }
}
