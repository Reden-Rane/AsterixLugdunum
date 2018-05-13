package fr.info.game.logic.tile;

import fr.info.game.logic.level.Level;

public class LilypadTile extends Tile {

    public LilypadTile(Level level, int meta, float x, float y) {
        super(level, "lilypad" + meta, x, y);
    }

}
