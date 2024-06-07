package com.hossam.utils;

import com.hossam.Main;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static com.hossam.graph.GraphProperties.*;

public class FileUtils {

    //        runGraph(filePath1, "a", "h");
//        runGraph(filePath2, "a", "k");
//        runGraph(filePath3, "Hamburg", "Kiel");
//        runGraph(filePath4, "s", "v5");
//        runGraph(filePath5, "v1", "v5");
//        runGraph(filePath6, "1", "9");
//        runGraph(filePath7, "v4", "v8");
//        runGraph(filePath8, "v12", "v16");
//        runGraph(filePath9, "a", "l");
//        runGraph(filePath10, "v3", "v9");
//        runGraph(filePath11, "v1", "v2");
//        runGraph(filePathX, "v355", "v445");

    public static String FILE_PATH1 = "graph01.gka";
    public static String FILE_PATH2 = "graph02.gka";
    public static String FILE_PATH3 = "graph03.gka";
    public static String FILE_PATH4 = "graph04.gka";
    public static String FILE_PATH5 = "graph05.gka";
    public static String FILE_PATH6 = "graph06.gka";
    public static String FILE_PATH7 = "graph07.gka";
    public static String FILE_PATH8 = "graph08.gka";
    public static String FILE_PATH9 = "graph09.gka";
    public static String FILE_PATH10 = "graph10.gka";
    public static String FILE_PATH11 = "graph11.gka";
    public static String FILE_MINUTE = "minute_graph.gka";
    public static String FILE_5_MINUTE = "five_minute_graph.gka";
    public static String FILE_10_MINUTE = "generated_5000.gka";

    public static Graph getGraph(String file) {
        String[] lines = readFileToArray(file);
        assert lines != null;
        boolean isDirected = detectGraphType(lines);
        return initStandardGraph(lines, isDirected);
    }

    public static String[] readFileToArray(String filePath) {
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

    public static void saveGraphToFile(Graph graph, String inputFilePath, Boolean isDirected) {
        File dir = new File("src/main/graphs"); // Speichern von gerichteten Graphen fehlt!
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String filename = new File(inputFilePath).getName();
        String filePath = dir.getPath() + File.separator + filename;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            graph.edges().forEach(edge -> {
                try {
                    String node0 = edge.getNode0().getId();
                    String node1 = edge.getNode1().getId();
                    String line;
                    if (isDirected) {
                        line = String.format("%s -> %s", node0, node1);
                    } else {
                        line = String.format("%s -- %s", node0, node1);
                    }

                    if (edge.hasAttribute("weight")) {
                        line += " : " + edge.getAttribute("weight");
                    }
                    line += ";";
                    writer.write(line);
                    writer.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            for (Node node : graph) {
                if (node.getDegree() == 0) {
                    writer.write(node.getId() + ";");
                    writer.newLine();
                }
            }
            System.out.println("Graph saved to " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void parseLine(Graph graph, String line, boolean isDirected) {
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
}
