package org.chocolaty.arknoid.model.entity.powerup;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.chocolaty.arknoid.model.GameConst;
import org.chocolaty.arknoid.model.entity.Paddle;

public abstract class PowerUp {
    protected double x, y;
    protected double vy = GameConst.POWERUP_FALL_SPEED;
    protected double size = GameConst.POWERUP_SIZE;
    protected final PowerUpType type;
    protected boolean active = true;
    protected Image image;

    protected PowerUp(double x, double y, PowerUpType type) {
        this.x = x;
        this.y = y;
        this.type = type;
        loadImage();
    }

    public abstract void apply(PowerUpContext ctx);

    public boolean isActive() {
        return active; }


    private void loadImage() {
        String path = switch (type) {
            case FIRE_BALL -> GameConst.POWERUP_FIRE_IMAGE;
            case EXPAND_PADDLE -> GameConst.POWERUP_EXPAND_IMAGE;
            case MULTI_BALL -> GameConst.POWERUP_MULTI_IMAGE;
        };
        try {
            var url = getClass().getResource(path);
            if (url != null)
                image = new Image(url.toExternalForm(), size, size, true, true);
            else
                System.err.println("image not found: " + path);
        } catch (Exception e) {
            System.err.println("failed to load image: " + e.getMessage());
        }
    }

    public void update(double dt) { // cap nhat vi tri
        y += vy * dt; //chi roi thang dung
        if (y > GameConst.SCREEN_HEIGHT + 50)
            active = false;
    }

    public void render(GraphicsContext g) {
        if (!active)
            return;
        double r = size / 2;
        if (image != null)
            g.drawImage(image, x - r, y - r, size, size);
        else {
            // fallback khi khong co anh
            g.setFill(javafx.scene.paint.Color.GRAY);
            g.fillOval(x - r, y - r, size, size);
        }
    }

    public boolean checkCatch(Paddle p) {
        if (!active)
            return false;
        double px = p.getX(), py = p.getY(), pw = p.getWidth(), ph = p.getHeight();
        boolean hit = x >= px && x <= px + pw && y + size/2 >= py && y - size/2 <= py + ph;
        if (hit)
            active = false;
        return hit;
    }
}
