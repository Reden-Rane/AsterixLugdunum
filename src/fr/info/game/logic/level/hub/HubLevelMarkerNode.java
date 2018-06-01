package fr.info.game.logic.level.hub;

import fr.info.game.logic.level.Level;
import fr.info.game.logic.path.Node;

public class HubLevelMarkerNode extends Node {

    public final Level level;

    public HubLevelMarkerNode(Level level, float x, float y) {
        super(x, y);
        this.level = level;
    }

}
