package fr.info.game.logic.level.campus;

import fr.info.game.logic.level.GameLevel;
import fr.info.game.logic.tile.Tile;

public class CampusLevel extends GameLevel {

    public CampusLevel() {
        super("Le campus");
    }

    @Override
    public Tile[][] generateTerrain() {
        return new Tile[0][];
    }
}
