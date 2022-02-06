package in.shamit.learn.tsp.io;

import in.shamit.learn.tsp.Config;
import in.shamit.learn.tsp.Graph;

public class ReadTest {
    public static void main(String[] args) {
        String filePath = Config.DATA_FILE;
        Graph g = GraphReader.readFromXML(filePath);
    }
}
