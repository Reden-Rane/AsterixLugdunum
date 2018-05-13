package fr.info.game.logic.tile;

import fr.info.game.logic.level.Level;

public class WaterTile extends Tile {

    public WaterTile(Level level, int meta, float x, float y) {
        super(level, "water" + meta, x, y);
    }

}
