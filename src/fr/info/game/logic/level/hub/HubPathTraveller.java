package fr.info.game.logic.level.hub;

import fr.info.game.logic.entity.Entity;
import fr.info.game.logic.path.PathTraveller;

public class HubPathTraveller extends PathTraveller {

    public HubPathTraveller(Entity entity, HubLevel level, HubLevelMarkerNode start, HubLevelMarkerNode end, float entitySpeed) {
        super(entity, level.hubPath.subPath(start, end), entitySpeed);
    }

    @Override
    public HubLevelMarkerNode getStartNode() {
        return (HubLevelMarkerNode) super.getStartNode();
    }

    @Override
    public HubLevelMarkerNode getEndNode() {
        return (HubLevelMarkerNode) super.getEndNode();
    }
}
