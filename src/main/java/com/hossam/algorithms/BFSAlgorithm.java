package com.hossam.algorithms;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import java.util.*;

public class BFSAlgorithm {

    // Method to run the BFS algorithm to find the shortest path between startNode and endNode in the graph
    public List<String> runBFSAlgorithm(Graph graph, String startNode, String endNode, boolean isDirected) {
        // Check if startNode and endNode exist in the graph
        if (graph.getNode(startNode) == null || graph.getNode(endNode) == null) {
            return Collections.emptyList(); // Return empty path if either node is not present
        }

        // Map to store the parent of each node to reconstruct the path later
        Map<String, String> parent = new HashMap<>();
        // Queue for BFS traversal
        Queue<String> queue = new LinkedList<>();
        // Set to keep track of visited nodes
        Set<String> visited = new HashSet<>();

        // Initialize BFS with the start node
        queue.add(startNode);
        visited.add(startNode);
        parent.put(startNode, null); // Start node has no parent

        // Perform BFS until the queue is empty
        while (!queue.isEmpty()) {
            // Dequeue the next node to process
            String currentNode = queue.poll();

            // If we reached the end node, construct and return the path
            if (currentNode.equals(endNode)) {
                return constructPath(parent, endNode);
            }

            // Get the neighbors of the current node
            Iterator<Node> neighbors = isDirected ?
                    // For directed graphs, consider only outgoing edges
                    graph.getNode(currentNode).leavingEdges().map(Edge::getTargetNode).iterator() :
                    // For undirected graphs, consider all neighboring nodes
                    graph.getNode(currentNode).neighborNodes().iterator();

            // Process each neighbor
            while (neighbors.hasNext()) {
                Node neighbor = neighbors.next();
                // If the neighbor has not been visited, mark it as visited and enqueue it
                if (!visited.contains(neighbor.getId())) {
                    visited.add(neighbor.getId());
                    parent.put(neighbor.getId(), currentNode); // Record the parent to reconstruct the path
                    queue.add(neighbor.getId());
                }
            }
        }
        return Collections.emptyList(); // Return empty list if no path is found
    }

    // Helper method to construct the path from the parent map
    private List<String> constructPath(Map<String, String> parent, String endNode) {
        List<String> path = new ArrayList<>();
        // Start from the end node and trace back to the start node
        for (String at = endNode; at != null; at = parent.get(at)) {
            path.add(at); // Add each node to the path
        }
        Collections.reverse(path); // Reverse the path to get it from start to end
        return path;
    }

    // Method to count the number of edges in the path
    public int countEdges(List<String> path) {
        return path.size() - 1; // Number of edges is the number of nodes in the path minus one
    }
}
