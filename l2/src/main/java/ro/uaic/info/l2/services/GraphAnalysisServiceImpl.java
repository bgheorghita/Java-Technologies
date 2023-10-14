package ro.uaic.info.l2.services;

import org.graph4j.Graph;
import org.graph4j.measures.GraphMeasures;
import org.graph4j.metrics.GraphMetrics;
import ro.uaic.info.l2.helpers.DIMACSParser;
import ro.uaic.info.l2.models.Input;
import ro.uaic.info.l2.models.Output;

public class GraphAnalysisServiceImpl implements GraphAnalysisService {
    @Override
    public Output analyzeDIMACSGraph(Input input) {
        Graph<String, String> graph = DIMACSParser.readGraph(input.getGraphFileData());
        return analyzeGraph(graph);
    }

    @Override
    public Output analyzeGraph(Graph graph) {
        GraphMeasures graphMeasures = new GraphMeasures();
        GraphMetrics graphMetrics = new GraphMetrics(graph);

        int numberOfVertices = graph.numVertices();
        long numberOfEdges = graph.numEdges();
        int minDegree = GraphMeasures.minDegree(graph);
        int maxDegree = GraphMeasures.maxDegree(graph);
        double avgDegree = graphMeasures.avgDegree(graph);
        double radius = graphMetrics.radius();
        double diameter = graphMetrics.diameter();

        return Output.builder()
                .withOrder(numberOfVertices)
                .withSize(numberOfEdges)
                .withMinDegree(minDegree)
                .withMaxDegree(maxDegree)
                .withAvgDegree(avgDegree)
                .withRadius(radius)
                .withDiameter(diameter)
                .build();
    }
}
