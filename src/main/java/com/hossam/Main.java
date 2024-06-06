package com.hossam;

import com.hossam.algorithms.BFSAlgorithm;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.layout.springbox.implementations.SpringBox;
import org.graphstream.ui.view.Viewer;
import java.util.*;

import static com.hossam.FileUtils.readFileToArray;
import static com.hossam.algorithms.KruskalAlgorithm.kruskalMST;
import static com.hossam.algorithms.PrimAlgorithm.primMST;

public class Main {
    public static void main(String[] args) {

        String filePath1 = "graph01.gka";
        String filePath2 = "graph02.gka";
        String filePath3 = "graph03.gka";
        String filePath4 = "graph04.gka";
        String filePath5 = "graph05.gka";
        String filePath6 = "graph06.gka";
        String filePath7 = "graph07.gka";
        String filePath8 = "graph08.gka";
        String filePath9 = "graph09.gka";
        String filePath10 = "graph10.gka";
        String filePath11 = "graph11.gka";

//        runGraph(filePath1, "a", "h");
//        runGraph(filePath2, "a", "k");
        runGraph(filePath3,"Hamburg", "Kiel");
//        runGraph(filePath4, "s", "v5");
//        runGraph(filePath5, "v1", "v5");
//        runGraph(filePath6, "1", "9");
//        runGraph(filePath7, "v4", "v8");
//        runGraph(filePath8, "v12", "v16");
//        runGraph(filePath9, "a", "l");
//        runGraph(filePath10, "v3", "v9");
//        runGraph(filePath11, "v1", "v2");
    }

    private static void runGraph(String filePath, String startNode, String endNode) {

        String[] lines = readFileToArray(filePath);

        boolean isDirected = detectGraphType(lines);

        Graph graph = createGraphFromLines(lines, isDirected);

//        Viewer viewer = graph.display();
//        viewer.enableAutoLayout(new SpringBox());

//        saveGraphToFile(graph, filePath,isDirected);


//         Run Kruskal's algorithm
//        List<Edge> mst = kruskalMST(graph);
//
//        if (!mst.isEmpty()) {
//            System.out.println("Minimum Spanning Tree found:");
//            for (Edge edge : mst) {
//                System.out.println(edge.getNode0().getId() + " -- " + edge.getNode1().getId() + " : " + edge.getAttribute("weight"));
//            }
//            // Create and display MST graph
//            Graph mstGraph = createMSTGraph(mst, isDirected);
//            Viewer mstViewer = mstGraph.display();
//            mstViewer.enableAutoLayout(new SpringBox());
//            highlightEdges(mst);
//        } else {
//            System.out.println("No MST found.");
//        }

//        BFSAlgorithm bfsAlgorithm = new BFSAlgorithm();
//        List<String> path = bfsAlgorithm.runBFSAlgorithm(graph, startNode, endNode, isDirected);
//        int edgeCount = bfsAlgorithm.countEdges(path);
//
//        if (!path.isEmpty()) {
//            System.out.println("Shortest path from " + startNode + " to " + endNode + ": " + String.join(" -> ", path));
//            System.out.println("Number of edges required: " + edgeCount);
//
//            highlightShortestPath(graph, path, isDirected);
//        } else {
//            System.out.println("No path found from " + startNode + " to " + endNode);
//
//        }

        // Run Prim's algorithm

        List<Edge> mstPrim = primMST(graph, startNode);

        if (!mstPrim.isEmpty()) {
            System.out.println("Minimum Spanning Tree using Prim's algorithm:");
            for (Edge edge : mstPrim) {
                System.out.println(edge.getNode0().getId() + " -- " + edge.getNode1().getId() + " : " + edge.getAttribute("weight"));
            }

            // Create and display MST graph
            Graph mstGraphPrim = createMSTGraph(mstPrim, isDirected);
            Viewer mstViewerPrim = mstGraphPrim.display();
            mstViewerPrim.enableAutoLayout(new SpringBox());
        } else {
            System.out.println("No MST found using Prim's algorithm.");
        }
    }

    private static Graph createMSTGraph(List<Edge> mstEdges, boolean isDirected) {
        Graph mstGraph = isDirected ? new SingleGraph("MST_DirectedGraph") : new MultiGraph("MST_UndirectedGraph");
        mstGraph.setAttribute("ui.stylesheet", styleSheet);
        mstGraph.setAttribute("ui.quality");
        mstGraph.setAttribute("ui.antialias");
        System.setProperty("org.graphstream.ui", "swing");

        for (Edge edge : mstEdges) {
            String node1 = edge.getNode0().getId();
            String node2 = edge.getNode1().getId();
            ensureNodeExists(mstGraph, node1);
            ensureNodeExists(mstGraph, node2);

            String edgeId = isDirected ? node1 + "->" + node2 : node1 + "--" + node2;
            Edge newEdge = mstGraph.addEdge(edgeId, node1, node2, isDirected);
            newEdge.setAttribute("weight", edge.getAttribute("weight"));
            newEdge.setAttribute("ui.label", edge.getAttribute("weight"));
        }

        return mstGraph;
    }
    public static boolean detectGraphType(String[] lines) {
        for (String line : lines) {
            if (line.contains("->")) {
                return true; // Directed graph
            } else if (line.contains("--")) {
                return false; // Undirected graph
            }
        }
        return false;
    }

    public static Graph createGraphFromLines(String[] lines, boolean isDirected) {
        Graph graph = isDirected ? new SingleGraph("DirectedGraph") : new MultiGraph("UndirectedGraph");
        graph.setAttribute("ui.stylesheet", styleSheet);
        graph.setAttribute("ui.quality");
        graph.setAttribute("ui.antialias");
        System.setProperty("org.graphstream.ui", "swing");

        for (String line : lines) {
            parseLine(graph, line, isDirected);
        }
        return graph;
    }

    private static void parseLine(Graph graph, String line, boolean isDirected) {
        String[] parts = line.split(";");
        String delimiter = isDirected ? "->" : "--";

        for (String part : parts) {
            part = part.trim();
            if (part.isEmpty()) continue;

            String[] edges = part.split(delimiter);
            for (int i = 0; i < edges.length - 1; i++) {
                String node1 = edges[i].trim().split("\\s+")[0];
                String node2 = edges[i + 1].trim().split("\\s+")[0];

                ensureNodeExists(graph, node1);
                ensureNodeExists(graph, node2);

                String edgeId = node1 + delimiter + node2;
                Edge edge;
                if (isDirected) {
                    edge = graph.addEdge(edgeId, node1, node2, true); // true for directed edge
                } else {
                    edge = graph.addEdge(edgeId, node1, node2, false); // false for undirected edge
                }

                String additionalInfo = edges[i + 1].trim();
                if (additionalInfo.contains("(") && additionalInfo.contains(")")) {
                    String edgeName = additionalInfo.substring(additionalInfo.indexOf('(') + 1, additionalInfo.indexOf(')')).trim();
                    edge.setAttribute("ui.label", edgeName);
                } else if (additionalInfo.contains(":")) {
                    String weightStr = additionalInfo.substring(additionalInfo.indexOf(':') + 1).trim();
                    if (!weightStr.isEmpty()) {
                        edge.setAttribute("weight", weightStr);
                        edge.setAttribute("ui.label", weightStr);
                    }
                }
            }
        }
    }

    private static void ensureNodeExists(Graph graph, String nodeName) {
        if (graph.getNode(nodeName) == null) {
            Node node = graph.addNode(nodeName);
            node.setAttribute("ui.label", nodeName);
        }
    }


    private static void highlightShortestPath(Graph graph, List<String> path, boolean isDirected) {
        for (int i = 0; i < path.size() - 1; i++) {
            String node1 = path.get(i);
            String node2 = path.get(i + 1);
            String edgeId = isDirected ? node1 + "->" + node2 : node1 + "--" + node2;

            Edge edge = graph.getEdge(edgeId);
            if (edge == null && !isDirected) {
                edge = graph.getEdge(node2 + "--" + node1);
            }
            if (edge != null) {
                edge.setAttribute("ui.style", styleSheetEdges);
            }
        }
    }
    private static void highlightEdges(List<Edge> edges) {
        for (Edge edge : edges) {
            edge.setAttribute("ui.style", styleSheetEdges);
        }
    }
    protected static String styleSheet =
            "node {" +
                    "   text-color: white;" +
                    "   text-style: bold;" +
                    "   text-size: 14px;" +
                    "   text-background-mode: rounded-box;" +
                    "   text-background-color: rgba(182,34,79, 200);" +
                    "   text-padding: 5px;" +
                    "   text-offset: 0px, 2px;" +
                    "   shape: circle;" +
                    "   size: 25px;" +
                    "   text-alignment: above;" +
                    "   text-offset: 0px, -12px;" +
                    "   fill-color: rgba(182,34,79, 100);" +
                    "   stroke-color: rgba(182,34,79, 100);" +
                    "   stroke-mode: plain;" +
                    "   stroke-width: 4px;" +
                    "   shadow-mode: gradient-radial;" +
                    "   shadow-width: 4px;" +
                    "   shadow-color: #999, white;" +
                    "   shadow-offset: 0px, -1px;" +
                    "   z-index: 1;" +
                    "}" +
                    "edge {" +
//                    "   shape: cubic-curve;" +
                    "   size: 3px;" +
                    "   fill-color: rgba(52,68,91, 150);" +
                    "   text-color: rgba(52,68,91, 200);" +
                    "   text-style: bold;" +
                    "   text-background-mode: rounded-box;" +
                    "   text-background-color: rgba(208,245,91, 250);" +
                    "   text-padding: 2px;" +
                    "   text-size: 14px;" +
//                    "   stroke-mode: plain;" +
//                    "   stroke-color: rgba(52,68,91, 150);" +
//                    "   stroke-width: 1px;" +
                    "   z-index: 0;" +
                    "}";

    protected static String styleSheetEdges =
//                    "   shape: cubic-curve;" +
            "   size: 3px;" +
                    "   fill-color: rgba(0,255,204, 200);" +
                    "   stroke-mode: plain;" +
                    "   stroke-color: rgba(0,255,204, 200);" +
                    "   stroke-width: 1px;" +
                    "   z-index: 0;";
}

