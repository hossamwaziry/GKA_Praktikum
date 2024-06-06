package com.hossam.algorithms;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.*;

public class KruskalAlgorithm {

    public static List<Edge> kruskalMST(Graph graph) {
        List<Edge> result = new ArrayList<>();
        List<Edge> edges = new ArrayList<>();
        for (Edge edge : graph.edges().toList()) {
            edges.add(edge);
        }

        edges.sort(new EdgeComparator());

        DisjointSet ds = new DisjointSet();

        for (Node node : graph.nodes().toList()) {
            ds.makeSet(node.getId());
        }

        for (Edge edge : edges) {
            String u = edge.getNode0().getId();
            String v = edge.getNode1().getId();

            if (ds.find(u) != ds.find(v)) {
                result.add(edge);
                ds.union(u, v);
            }
        }

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

        if (root1.equals(root2)) {
            return;
        }

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

class  EdgeComparator implements Comparator<Edge> {
    public int compare(Edge edge1, Edge edge2) {
        return Double.compare(edge1.getNumber("weight"), edge2.getNumber("weight"));
    }
}
