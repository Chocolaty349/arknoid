package org.chocolaty.arknoid.sound;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.net.URL;

/**
 * Quan ly nhac nen (BGM) toan game - singleton dung chung cho moi man hinh
 * (Menu, Guide, Game...) de nhac khong bi restart khi chuyen scene.
 * Copy 100% tu docs_temp/BgmManager.java, giu nguyen ten class/logic de cac
 * Controller khac (MenuController, GuideController - Nguoi 3 se code sau) goi
 * dung y het ban goc, chi doi package sang org.chocolaty.arknoid.sound.
 */
public class BgmManager {
    public enum Track { NONE, MENU, GAME }

    private static BgmManager instance;
    private MediaPlayer player;
    private double volume = 0.6;
    private boolean muted = false;
    private Track current = Track.NONE;

    private BgmManager() {}

    public static BgmManager get() {
        return (instance == null) ? (instance = new BgmManager()) : instance;
    }

    public void playMenu() {
        playIfNeeded("/org/chocolaty/arknoid/audio/menu_theme.mp3", Track.MENU);
    }

    public void playGame() {
        playIfNeeded("/org/chocolaty/arknoid/audio/game_theme.mp3", Track.GAME);
    }

    /** Chi phat neu KHAC track hien tai (tranh restart khi chuyen scene). */
    private void playIfNeeded(String path, Track t) {
        if (player != null && current == t) return; // dang phat dung bai -> bo qua
        stopInternal();
        URL url = getClass().getResource(path);
        if (url == null) {
            System.err.println("[BgmManager] Missing: " + path);
            current = Track.NONE;
            return;
        }
        player = new MediaPlayer(new Media(url.toExternalForm()));
        player.setCycleCount(MediaPlayer.INDEFINITE);
        player.setVolume(muted ? 0.0 : volume);
        player.play();
        current = t;
    }

    /** Dung han (dung khi Quit). */
    public void stop() {
        stopInternal();
        current = Track.NONE;
    }

    private void stopInternal() {
        if (player != null) {
            player.stop();
            player.dispose();
            player = null;
        }
    }

    public Track getCurrent() { return current; }
    public boolean isPlaying() { return player != null; }

    public void setMuted(boolean m) {
        muted = m;
        if (player != null) player.setVolume(m ? 0.0 : volume);
    }

    public boolean isMuted() { return muted; }

    public void setVolume(double v) {
        volume = Math.max(0, Math.min(1, v));
        if (player != null && !muted) player.setVolume(volume);
    }

    public double getVolume() { return volume; }
}
