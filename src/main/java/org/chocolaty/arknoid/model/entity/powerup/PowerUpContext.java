package org.chocolaty.arknoid.model.entity.powerup;

import org.chocolaty.arknoid.model.entity.Paddle;
import org.chocolaty.arknoid.model.manager.BallManager;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class PowerUpContext {
    /**PowerUpContext de quan ly qua trinh ap dung powerup cho cac bong (ap dung cho cai gi),
     * PowerUpManager de quan ly cac powerup da sinh ra (cai gi se duoc ap dung)*/
    public final BallManager balls;
    public final Paddle paddle;
    public final double now;
    public final Consumer<Boolean> setFireMode; // đặt fire cho tất cả bóng
    public final Supplier<Boolean> getFireMode;

    public PowerUpContext(BallManager balls, Paddle paddle, double now,
                          Consumer<Boolean> setFireMode, Supplier<Boolean> getFireMode) {
        this.balls = balls;
        this.paddle = paddle;
        this.now = now;
        this.setFireMode = setFireMode;
        this.getFireMode = getFireMode;
    }
}
}
