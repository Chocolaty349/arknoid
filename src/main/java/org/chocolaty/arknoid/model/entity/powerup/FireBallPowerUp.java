package org.chocolaty.arknoid.model.entity.powerup;

public class FireBallPowerUp extends PowerUp {
    private final double duration;

    public FireBallPowerUp(double x, double y, double durationSec) {
        super(x, y, PowerUpType.FIRE_BALL);
        this.duration = durationSec;
    }

    @Override
    public void apply(PowerUpContext ctx) {
        ctx.setFireMode.accept(true);
        // thoi gian hieu luc do powerupmanager theo doi bang PowerUpManager.BallManager
    }

    public double getDuration() {
        return duration;
    }
}
