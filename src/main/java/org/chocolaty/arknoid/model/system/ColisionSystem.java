package org.chocolaty.arknoid.model.system;

import org.chocolaty.arknoid.model.GameConst;
import org.chocolaty.arknoid.model.entity.Ball;
import org.chocolaty.arknoid.model.entity.Paddle;
import org.chocolaty.arknoid.model.entity.Brick;

public class ColisionSystem {

    public static void checkBallPaddle(Ball ball, Paddle paddle) {
        double px = paddle.getX(), py = paddle.getY();
        double pw = paddle.getWidth(), ph = paddle.getHeight();
        double r = ball.getRadius();

        double cx = clamp(ball.getX(), px, px + pw);
        double cy = clamp(ball.getY(), py, py + ph);

        double dx = ball.getX() - cx;
        double dy = ball.getY() - cy;

        if (dx*dx + dy*dy <= r*r && ball.getVy() > 0) {
            ball.setY(py - r - GameConst.COLLISION_PUSH_OUT);

            double t = ((ball.getX() - px) / pw) * 2 - 1; // trái: -1, giữa: 0, phải: 1
            double speed = Math.hypot(ball.getVx(), ball.getVy());
            double maxAngle = Math.toRadians(60); // nảy lệch tối đa 60°
            double angle = t * maxAngle;
            ball.setVelocity(speed * Math.sin(angle), -Math.abs(speed * Math.cos(angle)));
        }
    }

    public static void checkBallWalls(Ball ball, double width, double height) {
        double r = ball.getRadius();

        if (ball.getX() - r <= GameConst.BORDER_OFFSET_X) {
            ball.setX(GameConst.BORDER_OFFSET_X + r + GameConst.COLLISION_PUSH_OUT);
            ball.setVelocity(-ball.getVx(), ball.getVy());
        }
        if (ball.getX() + r >= width - GameConst.BORDER_OFFSET_X) {
            ball.setX(width - GameConst.BORDER_OFFSET_X - r - GameConst.COLLISION_PUSH_OUT);
            ball.setVelocity(-ball.getVx(), ball.getVy());
        }
        if (ball.getY() - r <= GameConst.BORDER_OFFSET_Y) {
            ball.setY(GameConst.BORDER_OFFSET_Y + r + GameConst.COLLISION_PUSH_OUT);
            ball.setVelocity(ball.getVx(), -ball.getVy());
        }
    }

    public static boolean isBallLost(Ball ball, double height) {
        return ball.getY() + ball.getRadius() >= height;
    }

    public static boolean checkBallBrick(Ball ball, Brick brick) {
        double bx = brick.getX(), by = brick.getY();
        double bw = brick.getWidth(), bh = brick.getHeight();
        double r = ball.getRadius();

        double cx = clamp(ball.getX(), bx, bx + bw);
        double cy = clamp(ball.getY(), by, by + bh);

        double dx = ball.getX() - cx;
        double dy = ball.getY() - cy;

        if (dx * dx + dy * dy <= r * r) {
            return true;
        }
        return false;
    }

    public static void resolveBallBrick(Ball ball, Brick brick, boolean fireMode) {
        if (fireMode && brick.isDestructible()) {
            // đẩy nhẹ theo hướng chuyển động để tránh kẹt sát mép khối
            final double nudge = GameConst.COLLISION_PUSH_OUT * 2;
            double sp = Math.hypot(ball.getVx(), ball.getVy());
            if (sp > 0) {
                ball.setPosition(ball.getX() + nudge * ball.getVx() / sp,
                        ball.getY() + nudge * ball.getVy() / sp);
            }
            return;
        }

        double bx = brick.getX(), by = brick.getY();
        double bw = brick.getWidth(), bh = brick.getHeight();
        double r = ball.getRadius();

        double cx = clamp(ball.getX(), bx, bx + bw);
        double cy = clamp(ball.getY(), by, by + bh);
        double dx = ball.getX() - cx;
        double dy = ball.getY() - cy;

        // Ngang nếu |dx| > |dy|, ngược lại dọc (xử lý góc!)
        if (Math.abs(dx) > Math.abs(dy)) {
            ball.setVelocity(-ball.getVx(), ball.getVy());  // Lật X
        } else {
            ball.setVelocity(ball.getVx(), -ball.getVy());  // Lật Y
        }

        // Tránh kẹt (1% chồng lấn an toàn)
        double dist = Math.hypot(dx, dy);
        if (dist > 0) {
            double push = r / dist * 1.01;
            ball.setPosition(cx + dx * push, cy + dy * push);
        }
    }

    public static void resolveBallBrick(Ball ball, Brick brick) {
        resolveBallBrick(ball, brick, false);
    }

    private static double clamp(double v, double min, double max) {
        return Math.max(min, Math.min(max, v));
    }
}
