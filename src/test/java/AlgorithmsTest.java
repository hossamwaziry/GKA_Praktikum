
import com.hossam.utils.DisjointSet;
import com.hossam.algorithms.KruskalAlgorithm;
import com.hossam.algorithms.PrimAlgorithm;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import org.junit.jupiter.api.Test;

import java.util.List;

import static com.hossam.graph.GraphGenerator.generateGraph;
import static com.hossam.graph.GraphProperties.initMSTGraph;
import static com.hossam.utils.FileUtils.FILE_MINUTE;
import static com.hossam.utils.FileUtils.getGraph;
import static org.junit.jupiter.api.Assertions.*;


public class AlgorithmsTest {

    private static final int NUM_NODES = 500;
    private static final int NUM_EDGES = (NUM_NODES * (NUM_NODES - 1)) / 2;
    private static final long TIME_LIMIT_MS = 10000;

    @Test
    public void testKruskalAlgorithm() {
        Graph testGraph = generateGraph(NUM_NODES,NUM_EDGES);
        List<Edge> mst = KruskalAlgorithm.kruskalAlgorithm(testGraph, TIME_LIMIT_MS);
        assertTrue(mst != null && !mst.isEmpty(), "MST should not be null or empty");
        assertEquals(testGraph.getNodeCount() - 1, mst.size(), "Number of edges in MST is incorrect");
    }

    @Test
    public void testPrimAlgorithm() {
        Graph graph = generateGraph(NUM_NODES, NUM_EDGES);
        List<Edge> mst = PrimAlgorithm.primAlgorithm(graph, "v0", TIME_LIMIT_MS);
        assertTrue(mst != null && !mst.isEmpty(), "MST should not be null or empty");
        assertEquals(graph.getNodeCount() - 1, mst.size(), "Number of edges in MST is incorrect");
    }

    @Test
    public void testAlgorithmsAgainstEachOther() {
        Graph graph = generateGraph(NUM_NODES, NUM_EDGES);

        List<Edge> mstKruskal = KruskalAlgorithm.kruskalAlgorithm(graph, TIME_LIMIT_MS);
        List<Edge> mstPrim = PrimAlgorithm.primAlgorithm(graph, "v0", TIME_LIMIT_MS);

        assertTrue(mstKruskal != null && !mstKruskal.isEmpty(), "MST from Kruskal's algorithm should not be null or empty");
        assertTrue(mstPrim != null && !mstPrim.isEmpty(), "MST from Prim's algorithm should not be null or empty");

        assertEquals(graph.getNodeCount() - 1, mstKruskal.size(), "Number of edges in MST is incorrect");
        assertEquals(graph.getNodeCount() - 1, mstPrim.size(), "Number of edges in MST is incorrect");

        // Compare the total weights of the MSTs
        double totalWeightKruskal = mstKruskal.stream().mapToDouble(e -> e.getNumber("weight")).sum();
        double totalWeightPrim = mstPrim.stream().mapToDouble(e -> e.getNumber("weight")).sum();

        assertEquals(totalWeightKruskal, totalWeightPrim, "Total weights of MSTs should be equal");
    }
}
