package fr.info.game.logic.tile;

import fr.info.game.logic.level.Level;

public class Tile {

    public static final int TILE_SIZE = 32;

    private final String textureName;

    Tile(String textureName) {
        this.textureName = textureName;
        TileRegistry.registeredTiles.add(this);
    }

    public String getTextureName() {
        return textureName;
    }

    public void update(Level level, int x, int y) {}

}
