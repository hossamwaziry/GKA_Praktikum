package com.hossam;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

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

    public  static void saveGraphToFile(Graph graph, String inputFilePath, Boolean isDirected) {
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
                    if (isDirected){
                        line = String.format("%s -> %s", node0, node1);
                    }
                    else {
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

}
