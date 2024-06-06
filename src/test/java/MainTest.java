import com.hossam.algorithms.BFSAlgorithm;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.List;

import static com.hossam.FileUtils.readFileToArray;
import static com.hossam.FileUtils.saveGraphToFile;
import static com.hossam.Main.*;
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
        String[] lines = readFileToArray(TEST_FILE);
        assertNotNull(lines);
        assertTrue(lines.length > 0);
    }

    @Test
    public void testSaveGraphToFile() {

        String[] lines = readFileToArray(TEST_FILE);
        boolean isDirected = detectGraphType(lines);
        graph =createGraphFromLines(lines, isDirected);
        saveGraphToFile(graph, TEST_FILE,false);

        File savedFile = new File(OUTPUT_DIR, new File(TEST_FILE).getName());
        assertTrue(savedFile.exists());
    }

    @Test
    public void testBFS() {
        String[] lines = readFileToArray(TEST_FILE);
        String startNode = "Hamburg";
        String endNode = "Husum";
        int expectedEdgeNumbers = 4;

        boolean isDirected = detectGraphType(lines);
        graph = createGraphFromLines(lines, isDirected);
        BFSAlgorithm bfsAlgorithm= new BFSAlgorithm();
        List<String> path = bfsAlgorithm.runBFSAlgorithm(graph, startNode, endNode, isDirected);
        assertNotNull(path);

        assertEquals(expectedEdgeNumbers, bfsAlgorithm.countEdges(path));
    }
}
