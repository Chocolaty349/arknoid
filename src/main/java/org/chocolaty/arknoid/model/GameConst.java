package org.chocolaty.arknoid.model;

public final class GameConst {
    private GameConst(){}

    // screen
    public static final double SCREEN_WIDTH = 900;
    public static final double SCREEN_HEIGHT = 650;
    //    public static final double SCREEN_BORDER = 1.0;
    public static final double BORDER_OFFSET_X = 0.06 * SCREEN_WIDTH;
    public static final double BORDER_OFFSET_Y = 0.22 * SCREEN_HEIGHT;

    //ball
    public static final double BALL_RADIUS = 15;
    public static final String BALL_NORMAL_IMAGE = "org/chocolaty/arknoid/image/ball_normal.png";
    public static final String BALL_FIRE_IMAGE = "org/chocolaty/arknoid/image/FireBall.png";

    public static final double BRICK_WIDTH = 80;
    public static final double BRICK_HEIGHT = 40;

    // va chạm collision
    public static final double PADDLE_BOUNCE_MAX_ANGLE = 60;
    public static final double COLLISION_PUSH_OUT = 0.01;

    // thông số game
    public static final int STARTING_LIVES = 3;
    public static final double BALL_RESET_DELAY = 0.5;
    public static final int MAX_LEVELS = 4;
    public static final int MAX_STARS = 3;
    public static final double LEVEL_FRAME_SIZE = 200;
}
