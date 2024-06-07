package com.hossam.graph;

import java.util.*;

import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;

import static com.hossam.utils.FileUtils.saveGraphToFile;

public class GraphGenerator {

    public static void main(String[] args) {
        int numNodes = 5000;
        int numEdges = (numNodes * (numNodes - 1)) / 2;

        Graph generatedGraph= generateGraph(numNodes,numEdges);
        saveGraphToFile(generatedGraph,"five_minute_graph.gka",false);
    }

    public static Graph generateGraph(int numNodes, int numEdges) {
        Graph graph = new MultiGraph("GeneratedGraph");
        Random rand = new Random();

        for (int i = 0; i < numNodes; i++) {
            graph.addNode("v" + i).setAttribute("ui.label", "v" + i);
        }

        Set<String> existingEdges = new HashSet<>();
        int edgeCount = 0;

        while (edgeCount < numEdges) {
            int u = rand.nextInt(numNodes);
            int v = rand.nextInt(numNodes);
            if (u != v) {
                String edgeId = "v" + u + "--v" + v;
                String reverseEdgeId = "v" + v + "--v" + u;
                if (!existingEdges.contains(edgeId) && !existingEdges.contains(reverseEdgeId)) {
                    int weight = rand.nextInt(100) + 1;
                    Edge edge = graph.addEdge(edgeId, "v" + u, "v" + v);
                    edge.setAttribute("weight", weight);
                    edge.setAttribute("ui.label", weight);
                    existingEdges.add(edgeId);
                    edgeCount++;
                }
            }
        }

        return graph;
    }
}

