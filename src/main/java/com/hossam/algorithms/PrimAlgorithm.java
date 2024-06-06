package com.hossam.algorithms;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.*;

public class PrimAlgorithm {

    public static List<Edge> primMST(Graph graph, String startNodeId) {
        List<Edge> mst = new ArrayList<>();
        Set<Node> visited = new HashSet<>();
        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingDouble(edge -> edge.getNumber("weight")));
        double totalWeight = 0;

        Node startNode = graph.getNode(startNodeId);
        visited.add(startNode);
        for (Edge edge : startNode.leavingEdges().toList()) {
            pq.add(edge);
        }

        while (!pq.isEmpty()) {
            Edge edge = pq.poll();
            Node node = edge.getOpposite(visited.contains(edge.getNode0()) ? edge.getNode0() : edge.getNode1());

            if (!visited.contains(node)) {
                mst.add(edge);
                totalWeight += edge.getNumber("weight");
                visited.add(node);
                for (Edge adjacentEdge : node.leavingEdges().toList()) {
                    if (!visited.contains(adjacentEdge.getOpposite(node))) {
                        pq.add(adjacentEdge);
                    }
                }
            }
        }

        System.out.println("Total weight of MST using Prim's algorithm: " + totalWeight);
        return mst;
    }
}
