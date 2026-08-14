package org.chocolaty.arknoid.model.manager;

import javafx.scene.canvas.GraphicsContext;
import org.chocolaty.arknoid.model.GameConst;
import org.chocolaty.arknoid.model.entity.Ball;
import org.chocolaty.arknoid.model.entity.Paddle;
import org.chocolaty.arknoid.model.system.ColisionSystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BallManager {
    private final List<Ball> balls = new ArrayList<>();
    private final double screenWidth, screenHeight;

    public BallManager(Ball initialBall, double screenWidth, double screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        if (initialBall != null)
            balls.add(initialBall);
    }

    // cap nhat tat ca bong
    public void update(double dt, Paddle paddle) {
        for (Ball b : balls) {
            if (b.isSticky())
                b.setPosition(paddle.getX() + paddle.getWidth() / 2,paddle.getY() - b.getRadius() - 2);
            else {
                b.update(dt, paddle.getX(), paddle.getY());
                ColisionSystem.checkBallPaddle(b, paddle);
                ColisionSystem.checkBallWalls(b, screenWidth, screenHeight);
            }
        }
    }

    /** ve cac bong */
    public void render(GraphicsContext g){
        for (Ball b : balls)
                b.render(g);
    }

    /**nha bong dang dinh */
    public void release(){
        for (Ball b : balls)
            if(b.isSticky())
                b.release();
    }

    public Ball getBall(){
        return balls.isEmpty() ? null : balls.getFirst();
    }

    public List<Ball> getBalls(){
        return Collections.unmodifiableList(balls);
    }

    public void spawnExtraBalls(Ball ball, int count) {
        if (ball == null || count <= 0) return;

        double baseVx = ball.getVx();
        double baseVy = ball.getVy();
        double speed = Math.hypot(baseVx, baseVy);
        if (speed == 0) {
            baseVy = -GameConst.BALL_DEFAULT_SPEED;
            speed = Math.abs(baseVy);
        }

        double maxJitterDeg = 15.0;
        for (int i = 0; i < count; i++) {
            Ball b = new Ball(ball.getX(), ball.getY(), ball.getRadius(), GameConst.BALL_NORMAL_IMAGE);

            double jitter = Math.toRadians((-maxJitterDeg) + Math.random() * (2 * maxJitterDeg));
            double[] nv = rotate(baseVx, baseVy, jitter); // xoay bong -> bong moi bay theo huong khac, khong trung bong cu

            // chuan hoa giu nguyen toc do
            double s = Math.hypot(nv[0], nv[1]);
            if (s > 0) { nv[0] = nv[0] * speed / s; nv[1] = nv[1] * speed / s; }

            // >>> bay ngay, khong sticky:
            b.launch(nv[0], nv[1]);

            // ap dung firemode neu co
            b.setFireMode(ball.isFireMode());

            balls.add(b);
        }

    }
        private static double[] rotate(double vx, double vy, double angleRad) {
        /** [ cosθ  -sinθ ] [nx]
            [ sinθ   cosθ ] [ny]*/
            double c = Math.cos(angleRad);
            double s = Math.sin(angleRad);
            double nx = vx * c - vy * s;
            double ny = vx * s + vy * c;
            return new double[]{nx, ny};
        }

        public int removeFallenBallsExceptLast(double screenHeight) {
            int removed = 0;
            for (int i = balls.size() - 1; i >= 0; i--) {
                Ball b = balls.get(i);
                if (ColisionSystem.isBallLost(b, screenHeight)) {
                    if (balls.size() > 1) {
                        balls.remove(i); // xoá các quả rơi lẻ
                        removed++;
                    }
                }
            }
            return removed;
        }

        public boolean isLastBallLost(double screenHeight) {
            if (balls.isEmpty())
                return true;
            if (balls.size() > 1)
                return false;
            Ball last = balls.get(0);
            return ColisionSystem.isBallLost(last, screenHeight);
        }

        public void resetMainBallToPaddle(double paddleX, double paddleY) {
            if (balls.isEmpty()) return;
            Ball last = balls.get(0);
            last.reset(paddleX, paddleY);
        }

        public boolean anySticky() {
            for (Ball b: balls) {
                if (b.isSticky()) {
                    return true;
                }
            }
            return false;
        }

}
