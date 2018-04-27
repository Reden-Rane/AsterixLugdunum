package fr.info.game.logic.level.hub;

import fr.info.game.logic.path.Path;
import fr.info.game.logic.path.PathNode;

import java.util.ArrayList;
import java.util.List;

public class HubPath extends Path {

    private final List<HubLevelMarkerNode> levelMarkers = new ArrayList<>();

    @Override
    public void appendNode(PathNode node) {
        super.appendNode(node);
        if (node instanceof HubLevelMarkerNode) {
            levelMarkers.add((HubLevelMarkerNode) node);
        }
    }

    @Override
    public void prependNode(PathNode node) {
        super.prependNode(node);
        if (node instanceof HubLevelMarkerNode) {
            levelMarkers.add(0, (HubLevelMarkerNode) node);
        }
    }

    public int getLevelMarkerIndex(HubLevelMarkerNode node) {
        return levelMarkers.indexOf(node);
    }

    public HubLevelMarkerNode getNextLevelMarker(HubLevelMarkerNode currentNode) {
        if (getLevelMarkerIndex(currentNode) + 1 >= this.levelMarkers.size()) {
            return null;
        } else {
            return this.levelMarkers.get(getLevelMarkerIndex(currentNode) + 1);
        }
    }

    public HubLevelMarkerNode getPreviousLevelMarker(HubLevelMarkerNode currentNode) {
        if (getLevelMarkerIndex(currentNode) - 1 < 0) {
            return null;
        } else {
            return this.levelMarkers.get(getLevelMarkerIndex(currentNode) - 1);
        }
    }


    public List<HubLevelMarkerNode> getLevelMarkers() {
        return levelMarkers;
    }
}
