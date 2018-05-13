package fr.info.game.logic.level.gates;

import fr.info.game.logic.level.GameLevel;
import fr.info.game.logic.tile.Tile;

public class GatesLevel extends GameLevel {

    public GatesLevel() {
        super("Les portes");
    }

    @Override
    public Tile[][] generateTerrain() {
        return new Tile[0][];
    }
}
