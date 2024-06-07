package com.hossam.graph;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.layout.springbox.implementations.SpringBox;
import org.graphstream.ui.view.Viewer;

import java.util.List;

import static com.hossam.graph.GraphStyle.styleSheet;
import static com.hossam.graph.GraphStyle.styleSheetEdges;
import static com.hossam.utils.FileUtils.parseLine;

public class GraphProperties {


    public static void displayGraph(Graph graph) {
        Viewer viewer = graph.display();
        viewer.enableAutoLayout(new SpringBox());
    }

    public static Graph initMSTGraph(List<Edge> mstEdges) {
        Graph mstGraph = new MultiGraph("MST_UndirectedGraph");
        mstGraph.setAttribute("ui.stylesheet", styleSheet);
        mstGraph.setAttribute("ui.quality");
        mstGraph.setAttribute("ui.antialias");
        System.setProperty("org.graphstream.ui", "swing");

        for (Edge edge : mstEdges) {
            String node1 = edge.getNode0().getId();
            String node2 = edge.getNode1().getId();
            ensureNodeExists(mstGraph, node1);
            ensureNodeExists(mstGraph, node2);

            String edgeId = node1 + "--" + node2;
            Edge newEdge = mstGraph.addEdge(edgeId, node1, node2, false);
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

    public static Graph initStandardGraph(String[] lines, boolean isDirected) {
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

    public static void ensureNodeExists(Graph graph, String nodeName) {
        if (graph.getNode(nodeName) == null) {
            Node node = graph.addNode(nodeName);
            node.setAttribute("ui.label", nodeName);
        }
    }

    public static void highlightShortestPath(Graph graph, List<String> path, boolean isDirected) {
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

    public static void highlightEdges(List<Edge> edges) {
        for (Edge edge : edges) {
            edge.setAttribute("ui.style", styleSheetEdges);
        }
    }

}
