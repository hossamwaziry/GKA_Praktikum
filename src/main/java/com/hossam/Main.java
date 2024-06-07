package com.hossam;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;

import java.util.*;

import static com.hossam.algorithms.BFSAlgorithm.bfsAlgorithm;
import static com.hossam.algorithms.BFSAlgorithm.countEdges;
import static com.hossam.algorithms.PrimAlgorithm.primAlgorithm;
import static com.hossam.graph.GraphProperties.*;
import static com.hossam.algorithms.KruskalAlgorithm.kruskalAlgorithm;
import static com.hossam.utils.FileUtils.*;

public class Main {

    public static void main(String[] args) {


        runKruskalAlgorithm(getGraph(FILE_5_MINUTE),10000);
//        runPrimAlgorithm(getGraph(FILE_MINUTE),"v372",1000);

//        runPrimAlgorithm(getGraph(FILE_PATH4), "s",100);

//        runBfsAlgorithm(getGraph(FILE_PATH3), "Hamburg", "Kiel", false);
    }


    private static void runKruskalAlgorithm(Graph graph, long durationTime) {
        List<Edge> mst = kruskalAlgorithm(graph, durationTime);
        if (!mst.isEmpty()) {
            System.out.println("Minimum Spanning Tree using Kruskal's algorithm: \n");
            for (Edge edge : mst) {
                System.out.println(edge.getNode0().getId() + " -- " + edge.getNode1().getId() + " : " + edge.getAttribute("weight"));
            }
            Graph mstKruskalGraph = initMSTGraph(mst);
            displayGraph(mstKruskalGraph);
        } else {
            System.out.println("No MST found.");
        }
    }

    private static void runPrimAlgorithm(Graph graph, String startNode, long durationTime) {
        List<Edge> mstPrim = primAlgorithm(graph, startNode, durationTime);
        if (!mstPrim.isEmpty()) {
            System.out.println("Minimum Spanning Tree using Prim's algorithm: \n");
            for (Edge edge : mstPrim) {
                System.out.println(edge.getNode0().getId() + " -- " + edge.getNode1().getId() + " : " + edge.getAttribute("weight"));
            }
            Graph mstPrimGraph = initMSTGraph(mstPrim);
            displayGraph(mstPrimGraph);
        } else {
            System.out.println("No MST found using Prim's algorithm.");
        }
    }

    private static void runBfsAlgorithm(Graph graph, String startNode, String endNode, boolean isDirected) {
        List<String> path = bfsAlgorithm(graph, startNode, endNode, isDirected);
        int edgeCount = countEdges(path);
        if (!path.isEmpty()) {
            System.out.println("Shortest path from " + startNode + " to " + endNode + ": " + String.join(" -> ", path));
            System.out.println("Number of edges required: " + edgeCount);
            displayGraph(graph);
            highlightShortestPath(graph, path, isDirected);
        } else {
            System.out.println("No path found from " + startNode + " to " + endNode);
        }
    }
}

