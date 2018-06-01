package fr.info.game.logic.path;

public class Node {

    public final float x;
    public final float y;

    public Node(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public double distanceTo(Node node) {
        return Math.sqrt(Math.pow(node.x - this.x, 2) + Math.pow(node.y - this.y, 2));
    }
}
