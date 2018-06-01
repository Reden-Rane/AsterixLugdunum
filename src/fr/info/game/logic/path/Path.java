package fr.info.game.logic.path;

import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class Path {

    private final List<Node> nodes = new ArrayList<>();
    private double pathLength;

    public void appendNode(Node node) {
        this.nodes.add(node);
        this.pathLength = computePathLength();
    }

    public void prependNode(Node node) {
        this.nodes.add(0, node);
        this.pathLength = computePathLength();
    }

    public Node getPathNode(int nodeIndex) {
        if (nodeIndex < 0) {
            nodeIndex = 0;
        } else if (nodeIndex >= nodes.size()) {
            nodeIndex = nodes.size() - 1;
        }
        return nodes.get(nodeIndex);
    }

    private double computePathLength() {
        double l = 0;
        for (int i = 1; i < nodes.size(); i++) {
            l += nodes.get(i - 1).distanceTo(nodes.get(i));
        }
        return l;
    }

    public Vector2f getPositionAtLength(double length) {
        double l = 0;

        for (int i = 1; i < nodes.size(); i++) {
            double segmentLength = nodes.get(i - 1).distanceTo(nodes.get(i));

            if (l + segmentLength >= length) {
                float p = (float) ((length - l) / segmentLength);
                float x = nodes.get(i - 1).x * (1 - p) + nodes.get(i).x * p;
                float y = nodes.get(i - 1).y * (1 - p) + nodes.get(i).y * p;
                return new Vector2f(x, y);
            } else {
                l += segmentLength;
            }
        }

        return new Vector2f(nodes.get(nodes.size() - 1).x, nodes.get(nodes.size() - 1).y);
    }

    public double getPathLength() {
        return pathLength;
    }

    public Vector2f getPositionAtProgress(float progress) {
        return getPositionAtLength(getPathLength() * progress);
    }

    public Path subPath(Node from, Node to) {
        return subPath(this.nodes.indexOf(from), this.nodes.indexOf(to));
    }

    public Path subPath(int from, int to) {
        Path subPath = new Path();
        for (int i = Math.min(from, to); i <= Math.max(from, to); i++) {
            if(from < to) {
                subPath.appendNode(this.nodes.get(i));
            } else {
                subPath.prependNode(this.nodes.get(i));
            }
        }
        return subPath;
    }

    public int getNodesCount() {
        return this.nodes.size();
    }
}
