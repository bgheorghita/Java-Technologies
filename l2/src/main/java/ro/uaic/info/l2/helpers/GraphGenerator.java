package ro.uaic.info.l2.helpers;

import org.graph4j.Graph;
import org.graph4j.GraphBuilder;

import java.util.Random;

public class GraphGenerator {
    private final static Random random = new Random();

    private GraphGenerator() {}

    public static Graph<String, String> generate(int order, int size) {
        Graph<String, String> graph = GraphBuilder.empty().buildGraph();
        for(int i = 1; i <= order; i++) {
            graph.addVertex(i);
        }

        while(graph.numEdges() < size) {
            int from = random(order);
            int to = random(order);
            graph.addEdge(from, to);
        }

       return graph;
    }

    private static int random(int max) {
        return random.nextInt(max) + 1;
    }
}