package org.chocolaty.arknoid.model.manager;

import org.chocolaty.arknoid.model.entity.Ball;
import org.chocolaty.arknoid.model.entity.Brick;
import org.chocolaty.arknoid.model.entity.BrickType;
import org.chocolaty.arknoid.model.system.ColisionSystem;
import org.chocolaty.arknoid.model.system.LevelLoader;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class BrickManager {
    private final List<Brick> bricks = new ArrayList<>();

    private int totalDestructible = 0;
    private int remainingDestructible = 0;

    public void loadLevel(String path) {
        bricks.clear();
        bricks.addAll(LevelLoader.loadLevel(path));

        totalDestructible = 0 ;
        remainingDestructible = 0;
        for (Brick b : bricks) {
            if (b.getType() != BrickType.INDESTRUCTIBLE) {
                totalDestructible++;
                remainingDestructible++;
            }
        }
    }

    public void updateAll(List<Ball> balls, boolean fireMode, Consumer<Brick> onDestroyed) {
        if (balls == null || balls.isEmpty()) return;

        for (Brick brick : bricks) {
            if (brick.isDestroyed()) continue;

            // kiểm tra với từng bóng
            for (Ball ball : balls) {
                if (brick.isDestroyed()) break; // đã vỡ bởi quả trước trong cùng frame

                if (ColisionSystem.checkBallBrick(ball, brick)) {
                    boolean beforeDestroyed = brick.isDestroyed();

                    if (fireMode && brick.isDestructible()) {
                        brick.damage(999); // phá ngay
                        // xuyên: KHÔNG bounce (resolve với fireMode=true sẽ chỉ nudge nhẹ)
                        ColisionSystem.resolveBallBrick(ball, brick, true);
                    } else {
                        brick.damage(1);
                        ColisionSystem.resolveBallBrick(ball, brick, false);
                    }

                    // nếu vừa chuyển từ chưa vỡ -> vỡ: cập nhật bộ đếm + callback 1 lần
                    if (!beforeDestroyed && brick.isDestroyed()
                            && brick.getType() != BrickType.INDESTRUCTIBLE) {
                        remainingDestructible = Math.max(0, remainingDestructible - 1);
                        if (onDestroyed != null) onDestroyed.accept(brick);
                    }
                }
            }
        }
    }

    public void update(Ball ball, boolean fireMode, Consumer<Brick> onDestroyed) {
        List<Ball> one = new ArrayList<>(1);
        if (ball != null) one.add(ball);
        updateAll(one, fireMode, onDestroyed);
    }

    public void render(GraphicsContext g) {
        for (Brick b : bricks) {
            if (!b.isDestroyed()) b.render(g);
        }
    }

    public int getRemaining() {
        return (int) bricks.stream().filter(b -> !b.isDestroyed()).count();
    }

    public boolean isLevelCleared() {
        return remainingDestructible == 0;
    }

    private void hitBrick(Brick brick) {
        brick.hit();
        if (brick.isDestroyed() && brick.getType() != BrickType.INDESTRUCTIBLE) {
            remainingDestructible--;
        }
    }
}