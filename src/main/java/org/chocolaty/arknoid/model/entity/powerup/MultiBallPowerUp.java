package org.chocolaty.arknoid.model.entity.powerup;

import org.chocolaty.arknoid.model.entity.Ball;

import java.util.ArrayList;
import java.util.Random;

public class MultiBallPowerUp extends PowerUp{
    private static final Random RNG = new Random();

    public MultiBallPowerUp(double x, double y) {
        super(x, y, PowerUpType.MULTI_BALL);
    }

    @Override
    public void apply(PowerUpContext ctx) {
        // ap dung powerup bang powerupcontext
        ArrayList<Ball> allBalls = new ArrayList<>(ctx.balls.getBalls());
        if (allBalls.isEmpty())
            return;

        // duyet tung qua bong, nhan 3 lan
        for (Ball src_ball : allBalls)
            ctx.balls.spawnExtraBalls(src_ball, 2);
    }
}
