package org.chocolaty.arknoid.model.system;

import org.chocolaty.arknoid.model.GameConst;
import org.chocolaty.arknoid.model.entity.Brick;
import org.chocolaty.arknoid.model.entity.BrickType;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class LevelLoader {
    public static List<Brick> loadLevel(String path) {
        List<Brick> bricks = new ArrayList<>();
        double brickWidth = GameConst.BRICK_WIDTH;
        double brickHeight = GameConst.BRICK_HEIGHT;
        double startY = GameConst.BORDER_OFFSET_Y;

        try (var inputStream = LevelLoader.class.getResourceAsStream(path);
             var reader = inputStream != null ? new BufferedReader(new InputStreamReader(inputStream)) : null) {

            if (reader == null) {
                throw new IllegalArgumentException("Level file not found: " + path);
            }

            String line;
            int row = 0;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                for (int col = 0; col < line.length(); col++) {
                    char c = line.charAt(col);
                    BrickType type = switch (c) {
                        case '1' -> BrickType.NORMAL;
                        case '2' -> BrickType.HARD;
                        case '3' -> BrickType.INDESTRUCTIBLE;
                        default -> null;
                    };
                    if (type != null) {
                        double x = col * brickWidth;
                        double y = startY + row * brickHeight;
                        bricks.add(new Brick(x, y, brickWidth, brickHeight, type));
                    }
                }
                row++;
            }
            System.out.println("Level loaded: " + path + " → " + bricks.size() + " bricks");

        } catch (Exception e) {
            System.err.println("FAILED TO LOAD LEVEL: " + path);
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            return createFallbackLevel();
        }
        return bricks;
    }

    private static List<Brick> createFallbackLevel() {
        System.out.println("Using fallback level (5 normal bricks)");
        List<Brick> fallback = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            fallback.add(new Brick(i * 100, 150, 90, 30, BrickType.NORMAL));
        }
        return fallback;
    }
}
