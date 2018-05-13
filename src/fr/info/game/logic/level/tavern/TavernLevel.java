package fr.info.game.logic.level.tavern;

import fr.info.game.logic.level.GameLevel;
import fr.info.game.logic.tile.Tile;

public class TavernLevel extends GameLevel {

    public TavernLevel() {
        super("La taverne");
    }

    @Override
    public Tile[][] generateTerrain() {
        return new Tile[0][];
    }
}
