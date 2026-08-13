package org.chocolaty.arknoid.model;

public final class GameConst {
    private GameConst(){}
  
    //screen
    public static final double SCREEN_WIDTH = 900;
    public static final double SCREEN_HEIGHT = 650;
    public static final double BORDER_OFFSET_X = 0.06 * SCREEN_WIDTH;
    public static final double BORDER_OFFSET_Y = 0.22 * SCREEN_HEIGHT;

    //ball
    public static final double BALL_RADIUS = 15;
    public static final String BALL_NORMAL_IMAGE = "org/chocolaty/arknoid/images/ball_normal.png";

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
  
    public static final String BALL_FIRE_IMAGE =  "org/chocolaty/arknoid/images/FireBall.png";

    public static final String PADDLE_IMAGE = "org/chocolaty/arknoid/images/paddle.png";
    public static final String BRICK_1_IMAGE = "org/chocolaty/arknoid/images/bricks/brick1.png";
    public static final String BRICK_2_IMAGE = "org/chocolaty/arknoid/images/bricks/brick2.png";
    public static final String BRICK_3_IMAGE = "org/chocolaty/arknoid/images/bricks/brick3.png";
    public static final String LEVEL_1_TXT = "org/chocolaty/arknoid/levels/level1.txt";
    public static final String LEVEL_2_TXT = "org/chocolaty/arknoid/levels/level2.txt";
    public static final String LEVEL_3_TXT = "org/chocolaty/arknoid/levels/level3.txt";
    public static final String LEVEL_4_TXT = "org/chocolaty/arknoid/levels/level4.txt";
    public static final String POWERUP_FIRE_IMAGE = "org/chocolaty/arknoid/images/powerups/FireBallPowerUp.png";
    public static final String POWERUP_EXPAND_IMAGE = "org/chocolaty/arknoid/images/powerups/ExpandPaddlePowerUp.png";
    public static final String POWERUP_MULTI_IMAGE = "org/chocolaty/arknoid/images/powerups/MultiBallPowerUp.png";
    public static final String LEVEL_STAR_IMAGE = "org/chocolaty/arknoid/images/menu/star.png";
    public static final String LEVEL_STAREMT_IMAGE = "org/chocolaty/arknoid/images/menu/star_empty.png";
    public static final String LEVEL_BG_IMAGE = "org/chocolaty/arknoid/images/menu/levelselection_bg.png";
}
