package org.chocolaty.arknoid.model.manager;

import javafx.scene.canvas.GraphicsContext;
import org.chocolaty.arknoid.model.GameConst;
import org.chocolaty.arknoid.model.entity.Brick;
import org.chocolaty.arknoid.model.entity.Paddle;
import org.chocolaty.arknoid.model.entity.powerup.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class PowerupManager {
    private final List<PowerUp> falling = new ArrayList<>();
    private double fireBallUntil = -1; // ban dau khong chay
    private double nowSec = 0;

    private final BallManager balls;
    private final Paddle paddle;
    private final Consumer<Boolean> setFireMode;
    private final Supplier<Boolean> getFireMode;

    private final Random rng = new Random();

    public PowerupManager(BallManager balls, Paddle paddle, Consumer<Boolean> setFireMode, Supplier<Boolean> getFireMode) {
        this.balls = balls;
        this.paddle = paddle;
        this.setFireMode = setFireMode;
        this.getFireMode = getFireMode;
    }

    private void apply(PowerUp p) {
        PowerUpContext ctx = new PowerUpContext(balls, paddle, nowSec, setFireMode, getFireMode);
        if (p instanceof FireBallPowerUp fb) {
            // dem thoi gian hieu luc fireballpowerup
            // thoi gian hieu luc cua extendpaddle do paddle tu quan ly
            // multiballpowerup khong gioi han thoi gian
            fb.apply(ctx);
            fireBallUntil = nowSec + fb.getDuration(); // bong lua co tac dung tu hien tai + thoi gian hieu luc
        } else
            p.apply(ctx);
    }

    public void render(GraphicsContext g) {
        for (PowerUp p : falling)
            p.render(g);
    }

    public void update(double dt) {
        /** cap nhat vi tri cho powerup trong khi roi theo thoi gian roi dt*/
        nowSec += dt;

        for (PowerUp p : falling) {
            p.update(dt);
            if (p.isActive() && p.checkCatch(paddle)) // da catch -> powerup class tu dat active = false
                apply(p);
        }
        falling.removeIf(p -> !p.isActive());

        if (getFireMode.get() && fireBallUntil > 0 && nowSec >= fireBallUntil) {
            setFireMode.accept(false);
            fireBallUntil = -1;
        }
    }

    public void clearAll() {
        falling.clear();
        setFireMode.accept(false);
        fireBallUntil = -1;
        nowSec = 0;
    }

    public void maybeDropFrom(Brick b) {
        if (!b.isDestructible()) return;
        if (rng.nextDouble() > GameConst.POWERUP_DROP_RATE) return;

        double x = b.getCenterX();
        double y = b.getCenterY();

        double roll = rng.nextDouble();
        if (roll < 0.34) {
            falling.add(new FireBallPowerUp(x, y, GameConst.FIREBALL_DURATION));
        } else if (roll < 0.67) {
            falling.add(new ExpandPaddlePowerUp(x, y, GameConst.EXPAND_FACTOR, GameConst.EXPAND_DURATION));
        } else {
            falling.add(new MultiBallPowerUp(x, y));
        }
    }
}
