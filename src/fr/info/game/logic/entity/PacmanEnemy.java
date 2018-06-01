package fr.info.game.logic.entity;

import fr.info.game.logic.entity.pacman.PacmanEntity;
import fr.info.game.logic.level.tavern.TavernLevel;

public class PacmanEnemy extends PacmanEntity {

    public PacmanEnemy(TavernLevel tavernLevel, float width, float height) {
        super(tavernLevel, width, height);
    }
}
