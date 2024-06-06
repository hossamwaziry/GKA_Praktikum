package com.hossam.algorithms;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import java.util.*;

public class BFSAlgorithm {

    public List<String> runBFSAlgorithm(Graph graph, String startNode, String endNode, boolean isDirected) {
        Map<String, String> parent = new HashMap<>();
        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        queue.add(startNode);
        visited.add(startNode);
        parent.put(startNode, null);

        while (!queue.isEmpty()) {
            String currentNode = queue.poll();

            if (currentNode.equals(endNode)) {
                return constructPath(parent, endNode);
            }

            Iterator<Node> neighbors = isDirected ?
                    graph.getNode(currentNode).leavingEdges().map(Edge::getTargetNode).iterator() :
                    graph.getNode(currentNode).neighborNodes().iterator();

            while (neighbors.hasNext()) {
                Node neighbor = neighbors.next();
                if (!visited.contains(neighbor.getId())) {
                    visited.add(neighbor.getId());
                    parent.put(neighbor.getId(), currentNode);
                    queue.add(neighbor.getId());
                }
            }
        }
        return Collections.emptyList();
    }

    private List<String> constructPath(Map<String, String> parent, String endNode) {
        List<String> path = new ArrayList<>();
        for (String at = endNode; at != null; at = parent.get(at)) {
            path.add(at);
        }
        Collections.reverse(path);
        return path;
    }

    public int countEdges(List<String> path) {
        return path.size() - 1;
    }
}

