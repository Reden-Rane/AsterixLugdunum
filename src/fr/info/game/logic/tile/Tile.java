package fr.info.game.logic.tile;

import fr.info.game.logic.level.Level;

public class Tile {

    public static final int TILE_SIZE = 32;

    public final Level level;
    public final float x;
    public final float y;
    public final String tileSpriteName;

    public Tile(Level level, String tileSpriteName, float x, float y) {
        this.level = level;
        this.tileSpriteName = tileSpriteName;
        this.x = x;
        this.y = y;
    }

}
