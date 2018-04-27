package fr.info.game.logic.path;

public class PathNode {

    public final int x;
    public final int y;

    public PathNode(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public double distanceTo(PathNode pathNode) {
        return Math.sqrt(Math.pow(pathNode.x - this.x, 2) + Math.pow(pathNode.y - this.y, 2));
    }
}
