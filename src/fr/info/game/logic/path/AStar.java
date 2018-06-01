package fr.info.game.logic.path;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AStar {
/*
    public static Path computePathTo(int startX, int startY, int endX, int endY) {

        NodeAStar start = new NodeAStar(startX, startY, 0, 0);
        NodeAStar end = new NodeAStar(endX, endY);

        List<NodeAStar> openSet = new ArrayList<>();
        List<NodeAStar> closedSet = new ArrayList<>();

        openSet.add(start);

        HashMap<NodeAStar, NodeAStar> cameFrom = new HashMap<>();
        HashMap<NodeAStar, Integer> costs = createIntegerMap(nodes, Integer.MAX_VALUE);
        HashMap<NodeAStar, Integer> heurisitics = createIntegerMap(nodes, Integer.MAX_VALUE);

        costs.put(start, 0);
        heurisitics.put(start, heuristicCostEstimate(start, end));

        while(!openSet.isEmpty()) {
            NodeAStar u = openSet.remove(0);
            if(u.x == endX && u.y == endY) {
                //Reconstitution chemin
                return ;
            }



        }



    }*/

    private static int heuristicCostEstimate(Node origin, Node goal) {
        return 0;
    }

    private static HashMap<Node, Integer> createIntegerMap(Node[][] nodes, int defaultValue) {
        HashMap<Node, Integer> map = new HashMap<>();

        for(Node[] n1 : nodes) {
            for(Node n2 : n1) {
                map.put(n2, defaultValue);
            }
        }
        return map;
    }

}
