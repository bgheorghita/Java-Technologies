package ro.uaic.info.l2.helpers;

import org.graph4j.Graph;
import org.graph4j.GraphBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DIMACSParser {
    private DIMACSParser() {
    }

    public static Graph<String, String> readGraph(InputStream is) {
        Graph<String, String> graph = GraphBuilder.empty().buildGraph();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.startsWith("p")) {
                    String[] parts = line.split("\\s+");
                    int nodes = Integer.parseInt(parts[2]);
                    for (int i = 1; i <= nodes; i++) {
                        graph.addVertex(i);
                    }
                } else if (line.startsWith("e")) {
                    String[] parts = line.split("\\s+");
                    String sourceVertex = parts[1];
                    String targetVertex = parts[2];
                    graph.addEdge(Integer.parseInt(sourceVertex), Integer.parseInt(targetVertex));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return graph;
    }
}
