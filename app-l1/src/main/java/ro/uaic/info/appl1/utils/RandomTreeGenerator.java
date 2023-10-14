package ro.uaic.info.appl1.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RandomTreeGenerator {
    private RandomTreeGenerator() {
    }

    public static int[][] generateRandomTree(int numVertices) {
        // Create an adjacency matrix
        int[][] adjacencyMatrix = new int[numVertices][numVertices];

        // Create a list to hold the vertices
        List<Integer> vertices = new ArrayList<>();
        for (int i = 0; i < numVertices; i++) {
            vertices.add(i);
        }

        // Shuffle the vertices to create a random order
        Collections.shuffle(vertices);

        // Create the tree structure by connecting vertices in a specific order
        for (int i = 1; i < numVertices; i++) {
            int parent = vertices.get(i - 1);
            int child = vertices.get(i);

            // Connect the parent and child vertices by setting the adjacency matrix entries
            adjacencyMatrix[parent][child] = 1;
            adjacencyMatrix[child][parent] = 1;
        }

        return adjacencyMatrix;
    }

}
