package org.chocolaty.arknoid.model.entity;

public enum BrickType {
    NORMAL(1, "/org/chocolaty/arkanoid/image/bricks/brick1.png"),
    HARD(2, "/org/chocolaty/arkanoid/image/bricks/brick2.png"),
    INDESTRUCTIBLE(999, "/org/chocolaty/arkanoid/image/bricks/brick3.png");

    private final int maxHealth;
    private final String imagePath;

    BrickType(int maxHealth, String imagePath) {
        this.maxHealth = maxHealth;
        this.imagePath = imagePath;
    }

    public int getMaxHealth() { return maxHealth; }
    public String getImagePath() { return imagePath; }
}
