package in.shamit.learn.tsp;

import in.shamit.learn.tsp.io.GraphReader;

import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Traverse {
    public static Logger log = Logger.getLogger("Traversal");
    Path lowestCostPath = null;
    public Path DoTraverse(Graph g,Path p){
        if(p==null){
            p = new Path();
        }

        Vertex start = g.getFirstVertex();
        Vertex lastVertex = p.getLastVertex();
        if(lastVertex == null){
            lastVertex=start;
        }
        final Vertex lv = lastVertex;
        Path finalP = p;



        g.vertices.keySet().parallelStream().forEach(v_id->{
            Vertex v = g.vertices.get(v_id);
            if(!finalP.doesIncludeVertex(v) ){
                if(!v.equals(lv)){
                    Path childPath = new Path(finalP);
                    Edge e = lv.getEdgeToVertex(v);
                    if(childPath.canAddEdge(e)){
                        childPath.addEdge(e);
                        DoTraverse(g,childPath);
                    }else{
                        if(start.equals(v)){
                            if(isPathComplete(g,childPath,v)){
                                childPath.addEdge(e);
                                evaluatePath(childPath);
                            }
                        }
                    }
                }
            }
        });
        return null;
    }
    AtomicLong pathCount = new AtomicLong(0);
    void evaluatePath(Path p) {
        long curCount = pathCount.incrementAndGet();
        if(lowestCostPath == null || (lowestCostPath.getCost() > p.getCost())){
            lowestCostPath = p;
            log.info("New Path, Cost = " +p.getCost());
            System.out.println(p.getCost() + " " + p);
        }
        if(curCount % 1000_000_00 == 0){
            log.info(curCount + "");
        }
    }

    boolean isPathComplete(Graph g, Path p,Vertex nextVertex){
        boolean complete = false;
        Vertex fist = g.getFirstVertex();
        if(nextVertex.equals(fist)){
            int verticesInGraph = g.vertices.size();
            if(p.edgeCount()==verticesInGraph-1){
                complete = true;
            }
        }
        return complete;
    }

    public static void main(String[] args) {
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "%1$tF %1$tT %4$s %2$s %5$s%6$s%n");

        log.setLevel(Level.INFO);
        String filePath = Config.DATA_FILE;
        log.info("Reading "+filePath);
        Graph g = GraphReader.readFromXML(filePath);

        Traverse t = new Traverse();
        t.DoTraverse(g,null);
        log.info("done");
    }
}
