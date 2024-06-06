package com.hossam.algorithms;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;

import java.util.*;

public class KruskalAlgorithm {

    public static List<Edge> kruskalAlgorithm(Graph graph, long durationMillis) {
        long startTime = System.currentTimeMillis();

        List<Edge> result = new ArrayList<>();
        List<Edge> edges = new ArrayList<>();
        double totalWeight = 0;

        for (Edge edge : graph.edges().toList()) {
            edges.add(edge);
        }

        edges.sort(Comparator.comparingDouble(edge -> edge.getNumber("weight")));

        DisjointSet ds = new DisjointSet();
        graph.nodes().forEach(node -> ds.makeSet(node.getId()));


        for (Edge edge : edges) {
            if (System.currentTimeMillis() - startTime > durationMillis) {
                return result;
            }

            String u = edge.getNode0().getId();
            String v = edge.getNode1().getId();
            if (ds.find(u) != ds.find(v)) {
                result.add(edge);
                totalWeight += edge.getNumber("weight");
                ds.union(u, v);
            }
        }

        long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime); // in milliseconds

        System.out.println("Total weight of MST using Kruskal's algorithm: " + totalWeight);
        System.out.println("Kruskal's algorithm execution time: " + duration + " milliseconds");

        return result;
    }
}

class DisjointSet {
    private final Map<String, String> parent = new HashMap<>();
    private final Map<String, Integer> rank = new HashMap<>();

    public void makeSet(String item) {
        parent.put(item, item);
        rank.put(item, 0);
    }

    public String find(String item) {
        if (!parent.get(item).equals(item)) {
            parent.put(item, find(parent.get(item)));
        }
        return parent.get(item);
    }

    public void union(String set1, String set2) {
        String root1 = find(set1);
        String root2 = find(set2);
        if (!root1.equals(root2)) {
            if (rank.get(root1) > rank.get(root2)) {
                parent.put(root2, root1);
            } else if (rank.get(root1) < rank.get(root2)) {
                parent.put(root1, root2);
            } else {
                parent.put(root2, root1);
                rank.put(root1, rank.get(root1) + 1);
            }
        }
    }
}
