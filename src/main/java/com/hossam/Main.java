package com.hossam;

import com.hossam.algorithms.BFSAlgorithm;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.graph.implementations.MultiGraph;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.hossam.FileUtils.saveGraphToFile;

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

        processFile(filePath3);
//        processFile(filePath2);
//        processFile(filePath3);
//        processFile(filePath4);
//        processFile(filePath5);
//        processFile(filePath6);
//        processFile(filePath7);
//        processFile(filePath8);
//        processFile(filePath9);

    }

    private static void processFile(String filePath) {

        String[] lines = readFileToArray(filePath);

        if (lines != null) {

            boolean isDirected = detectGraphType(lines);
            boolean isWeighted = detectGraphWeights(lines);
            Graph graph = createGraphFromLines(lines, isDirected, isWeighted);
            graph.display();
            saveGraphToFile(graph, filePath);

            String startNode = "Hamburg";
            String endNode = "Husum";

            BFSAlgorithm bfsAlgorithm = new BFSAlgorithm();
            List<String> path = bfsAlgorithm.runBFSAlgorithm(graph, startNode, endNode, isDirected);
            int edgeCount = bfsAlgorithm.countEdges(path);

            if (!path.isEmpty()) {
                System.out.println("Shortest path from " + startNode + " to " + endNode + ": " + String.join(" -> ", path));
                System.out.println("Number of edges required: " + edgeCount);
            } else {
                System.out.println("No path found from " + startNode + " to " + endNode);
            }
        }
    }

    static String[] readFileToArray(String filePath) {
        List<String> lines = new ArrayList<>();
        try (InputStream is = Main.class.getClassLoader().getResourceAsStream(filePath)) {
            assert is != null;
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                String line;
                while ((line = br.readLine()) != null) {
                    lines.add(line.trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return lines.toArray(new String[0]);
    }

    static boolean detectGraphType(String[] lines) {
        for (String line : lines) {
            if (line.contains("->")) {
                return true; // Directed graph
            } else if (line.contains("--")) {
                return false; // Undirected graph
            }
        }
        // Default to undirected if no edges are found
        return false;
    }

    static boolean detectGraphWeights(String[] lines) {
        Pattern pattern = Pattern.compile(".*:\\s*\\d+\\s*;");
        for (String line : lines) {
            if (pattern.matcher(line).matches()) {
                return true; // Weighted graph
            }
        }
        // Default to unweighted if no weights are found
        return false;
    }

    static Graph createGraphFromLines(String[] lines, boolean isDirected, boolean isWeighted) {
        Graph graph = isDirected ? new SingleGraph("DirectedGraph") : new MultiGraph("UndirectedGraph");
        graph.setAttribute("ui.stylesheet", styleSheet);
        graph.setAttribute("ui.quality");
        graph.setAttribute("ui.antialias");

        System.setProperty("org.graphstream.ui", "swing");

        for (String line : lines) {
            parseLine(graph, line, isDirected, isWeighted);
        }
        return graph;
    }

    private static void parseLine(Graph graph, String line, boolean isDirected, boolean isWeighted) {
        if (line.isEmpty() || line.equals(";")) {
            return;
        }
        String delimiter = isDirected ? "->" : "--";
        String edgePattern = String.format("(.+?)\\s*%s\\s*(.+?)(?:\\s*\\((.+?)\\))?(?:\\s*:\\s*(\\d+))?\\s*;", delimiter);
        Pattern pattern = Pattern.compile(edgePattern);
        Matcher matcher = pattern.matcher(line);

        if (matcher.matches()) {
            String node1 = matcher.group(1).trim();
            String node2 = matcher.group(2).trim();
            String edgeName = matcher.group(3) != null ? matcher.group(3).trim() : null;
            String weightStr = matcher.group(4) != null ? matcher.group(4).trim() : null;
            double weight = weightStr != null ? Double.parseDouble(weightStr) : 1.0;

            ensureNodeExists(graph, node1);
            ensureNodeExists(graph, node2);

            String edgeId = node1 + delimiter + node2 + (edgeName != null ? "(" + edgeName + ")" : "");
            Edge edge;
            if (isDirected) {
                edge = graph.addEdge(edgeId, node1, node2, true); // true for directed edge
            } else {
                edge = graph.addEdge(edgeId, node1, node2, false); // false for undirected edge
            }
            if (isWeighted && weightStr != null) {
                edge.setAttribute("weight", weight);
                edge.setAttribute("ui.label", weight);
            } else if (edgeName != null) {
                edge.setAttribute("ui.label", edgeName);
            }
        }
    }

    private static void ensureNodeExists(Graph graph, String nodeName) {
        if (graph.getNode(nodeName) == null) {
            Node node = graph.addNode(nodeName);
            node.setAttribute("ui.label", nodeName);
        }
    }
    protected static String styleSheet =
            "node {" +
                    "   text-color: white;" +
                    "   text-style: bold;" +
                    "   text-size: 12px;" +
                    "   text-background-mode: rounded-box;" +
                    "   text-background-color: rgba(182,34,79, 230);" +
                    "   text-padding: 5px;" +
                    "   text-offset: 0px, 2px;" +
                    "   shape: circle;" +
                    "   size: 25px;" +
                    "   text-alignment: above;" +
                    "   text-offset: 0px, -12px;" +
                    "   fill-color: rgba(182,34,79, 100);" +
                    "   stroke-color: rgba(182,34,79, 225);" +
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
                    "   size: 2px;" +
                    "   fill-color: rgba(52,68,91, 150);" +
                    "   text-color: rgba(52,68,91, 200);" +
                    "   text-style: bold;" +
                    "   text-background-mode: rounded-box;" +
                    "   text-background-color: rgba(208,245,91, 250);" +
                    "   text-padding: 2px;" +
                    "   text-size: 10px;" +
//                    "   stroke-mode: plain;" +
//                    "   stroke-color: rgba(52,68,91, 150);" +
//                    "   stroke-width: 1px;" +
                    "   z-index: 0;" +
                    "}";
}
