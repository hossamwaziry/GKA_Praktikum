package com.hossam;

import com.hossam.algorithms.BFSAlgorithm;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.List;

import static com.hossam.FileUtils.saveGraphToFile;
import static com.hossam.Main.detectGraphType;
import static com.hossam.Main.detectGraphWeights;
import static java.nio.file.Files.readAllBytes;
import static org.junit.jupiter.api.Assertions.*;

public class MainTest {

    private static final String TEST_FILE = "graph03.gka";
    private static final String OUTPUT_DIR = "src/main/graphs";

    private Graph graph;

    @BeforeEach
    public void setUp() {
        graph = new SingleGraph("TestGraph");
    }

    @Test
    public void testReadFile() {
        String[] lines = Main.readFileToArray(TEST_FILE);
        assertNotNull(lines);
        assertTrue(lines.length > 0);
    }

    @Test
    public void testSaveGraphToFile() {

        String[] lines = Main.readFileToArray(TEST_FILE);
        boolean isDirected = detectGraphType(lines);
        boolean isWeighted = detectGraphWeights(lines);
        graph = Main.createGraphFromLines(lines, isDirected, isWeighted);
        saveGraphToFile(graph, TEST_FILE);

        File savedFile = new File(OUTPUT_DIR, new File(TEST_FILE).getName());
        assertTrue(savedFile.exists());
    }

    @Test
    public void testBFS() {
        String[] lines = Main.readFileToArray(TEST_FILE);
        String startNode = "Hamburg";
        String endNode = "Husum";
        int expectedEdgeNumbers = 5;

        boolean isDirected = detectGraphType(lines);
        boolean isWeighted = detectGraphWeights(lines);
        graph = Main.createGraphFromLines(lines, isDirected, isWeighted);
        BFSAlgorithm bfsAlgorithm= new BFSAlgorithm();
        List<String> path = bfsAlgorithm.runBFSAlgorithm(graph, startNode, endNode, isDirected);
        assertNotNull(path);

        assertEquals(expectedEdgeNumbers, bfsAlgorithm.countEdges(path));
    }
}
