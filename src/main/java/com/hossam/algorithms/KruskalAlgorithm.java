package com.hossam.algorithms;

import com.hossam.utils.DisjointSet;
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

        System.out.println("Total weight of MST using Kruskal's algorithm: " + totalWeight+"\n");
        System.out.println("Kruskal's algorithm execution time: " + duration + " milliseconds"+"\n");

        return result;
    }
}

