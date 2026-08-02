package org.chocolaty.arknoid.model.entity.powerup;

public class ExpandPaddlePowerUp extends PowerUp {
    private final double factor;
    private final double duration;

    public ExpandPaddlePowerUp(double x, double y, double factor, double duration){
        super(x, y, PowerUpType.EXPAND_PADDLE);
        this.factor = factor;
        this.duration = duration;
    }

    public void apply(PowerUpContext ctx) {
        // paddle tu biet thoi gian ap dung powerup cua no
        ctx.paddle.expandTemporary(factor, duration);
    }
}
