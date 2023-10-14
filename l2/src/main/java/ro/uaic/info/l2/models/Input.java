package ro.uaic.info.l2.models;

import java.io.InputStream;

public class Input {
    private InputStream graphFileData;

    public Input(InputStream graphFileData) {
        this.graphFileData = graphFileData;
    }

    public InputStream getGraphFileData() {
        return graphFileData;
    }

    public void setGraphFileData(InputStream graphFileData) {
        this.graphFileData = graphFileData;
    }
}
