import com.hossam.algorithms.BFSAlgorithm;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.List;

import static com.hossam.algorithms.BFSAlgorithm.bfsAlgorithm;
import static com.hossam.graph.GraphProperties.detectGraphType;
import static com.hossam.Main.*;
import static com.hossam.utils.FileUtils.*;
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
        graph = getGraph(TEST_FILE);
        saveGraphToFile(graph, TEST_FILE, false);

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
        graph = getGraph(TEST_FILE);
        List<String> path = bfsAlgorithm(graph, startNode, endNode, isDirected);
        assertNotNull(path);

        assertEquals(expectedEdgeNumbers, BFSAlgorithm.countEdges(path));
    }
}
