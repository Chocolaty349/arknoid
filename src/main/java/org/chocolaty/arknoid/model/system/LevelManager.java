package org.chocolaty.arknoid.model.system;

import org.chocolaty.arknoid.model.GameConst;

import java.util.prefs.Preferences;

public class LevelManager {
    private static final Preferences prefs = Preferences.userNodeForPackage(LevelManager.class);
    private static final String STARS_KEY = "level_%d_stars";

    // Lay so sao cua level
    public static int getStars(int level) {
        return prefs.getInt(String.format(STARS_KEY, level), 0);
    }

    // Luu so sao
    public static void saveStars(int level, int stars) {
        int currentStars = getStars(level);
        if (stars > currentStars) {
            prefs.putInt(String.format(STARS_KEY, level), stars);
            System.out.println("Saved " + stars + " stars for level " + level);
        }
    }

    // Kiem tra level unlock (level 1 luon unlock, level n unlock neu level n-1 >=1 sao)
    public static boolean isLevelUnlocked(int level) {
        if (level == 1) return true;
        return getStars(level - 1) >= 1;
    }

    public static void resetAllStars() {
        for (int i = 1; i <= GameConst.MAX_LEVELS; i++) {
            prefs.remove(String.format(STARS_KEY, i));
        }
        System.out.println("All stars reset");
    }
}

