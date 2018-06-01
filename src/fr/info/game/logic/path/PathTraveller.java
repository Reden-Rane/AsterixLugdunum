package fr.info.game.logic.path;

import fr.info.game.logic.entity.Entity;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class PathTraveller {

    /**
     * The entity that travels the path
     */
    private Entity entity;
    /**
     * The path to travel onto
     */
    private final Path path;

    private int stepCounter;
    private final List<Vector2f> steps = new ArrayList<>();
    private boolean finished;

    public PathTraveller(Entity entity, Path path, float entitySpeed) {
        this.entity = entity;
        this.path = path;
        int nSteps = (int) Math.ceil(path.getPathLength() / entitySpeed);

        for (int i = 0; i <= nSteps; i++) {
            Vector2f pos = path.getPositionAtLength(entitySpeed * i);
            steps.add(pos);
        }
    }

    public void update() {
        if (!isFinished()) {
            Vector2f pos = steps.get(stepCounter);
            entity.moveTo(pos.x, pos.y);
            stepCounter++;

            if (stepCounter >= steps.size()) {
                this.finished = true;
            }
        }
    }

    public Node getStartNode() {
        return this.path.getPathNode(0);
    }

    public Node getEndNode() {
        return this.path.getPathNode(this.path.getNodesCount() - 1);
    }

    public boolean isFinished() {
        return finished;
    }
}
