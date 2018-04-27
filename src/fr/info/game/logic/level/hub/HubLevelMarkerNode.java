package fr.info.game.logic.level.hub;

import fr.info.game.logic.level.Level;
import fr.info.game.logic.path.PathNode;

public class HubLevelMarkerNode extends PathNode {

    public final Level level;

    public HubLevelMarkerNode(Level level, int x, int y) {
        super(x, y);
        this.level = level;
    }

}
