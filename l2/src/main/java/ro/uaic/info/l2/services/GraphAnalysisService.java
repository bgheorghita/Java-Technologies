package ro.uaic.info.l2.services;

import org.graph4j.Graph;
import ro.uaic.info.l2.models.Input;
import ro.uaic.info.l2.models.Output;

public interface GraphAnalysisService {
   /**
    * A server-side component responsible with the business-logic of the application: determining the graph properties.
    * @param input
    * @return output object containing the graph properties
    */
   Output analyzeDIMACSGraph(Input input);
   Output analyzeGraph(Graph graph);
}
