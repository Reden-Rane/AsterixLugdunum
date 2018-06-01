package fr.info.game.logic.path;

import java.util.Comparator;

public class NodeAStar extends Node implements Comparator<NodeAStar> {

    public int cost;
    public int heurisitic;

    public NodeAStar(float x, float y) {
        super(x, y);
    }

    public NodeAStar(float x, float y, int cost, int heurisitic) {
        super(x, y);
        this.cost = cost;
        this.heurisitic = heurisitic;
    }

    @Override
    public int compare(NodeAStar n1, NodeAStar n2) {
        //TODO Check the comparison

        if(n1.heurisitic < n2.heurisitic) {
            return 1;
        } else if(n1.heurisitic == n2.heurisitic) {
            return 0;
        }
        return -1;
    }
}
