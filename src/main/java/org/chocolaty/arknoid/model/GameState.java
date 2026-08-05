package org.chocolaty.arknoid.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class GameState {
    private int lives = GameConst.STARTING_LIVES;
    private int score = 0;
    private int currentLevel = 1;
    // Danh dau "vua thang man" - de GameOverView/WinView (Ngay 9) biet nen hien
    // thong bao Thang hay Thua khi chuyen man hinh. isGameOver() da co san cho
    // truong hop Thua, day la tin hieu con thieu cho truong hop Thang.
    private boolean levelCleared = false;

    public void loseLife() {
        lives = Math.max(0, lives - 1);
    }

    public boolean isGameOver() {
        return lives == 0;
    }

    /** Goi khi BrickManager bao da pha het gach (thang man). */
    public void markLevelCleared() {
        levelCleared = true;
    }

    public boolean isLevelCleared() {
        return levelCleared;
    }

    public void render(GraphicsContext g) {
        g.setFont(Font.font("Arial", 20));
        g.setFill(Color.WHITE);
        g.setTextAlign(TextAlignment.LEFT);
        g.fillText("Lives: " + lives, 100, 30);
        g.setTextAlign(TextAlignment.CENTER);
        g.fillText("Score: " + score, GameConst.SCREEN_WIDTH / 2, 30);
        g.setTextAlign(TextAlignment.RIGHT);
        g.fillText("Level: " + currentLevel, GameConst.SCREEN_WIDTH - 100, 30);
    }

    public int getStarsEarned() {
        return Math.min(lives, GameConst.MAX_STARS);  // Sao = lives con lai
    }

    // Getters/Setters
    public int getLives() { return lives; }
    public int getScore() { return score; }
    public void addScore(int points) { score += points; }
    public int getCurrentLevel() { return currentLevel; }
    public void setCurrentLevel(int level) { currentLevel = level; }
}
