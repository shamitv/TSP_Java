package in.shamit.learn.tsp;

import in.shamit.learn.tsp.io.GraphReader;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Traverse {
    public static Logger log = Logger.getLogger("Traversal");
    Path lowestCostPath = null;

    public Path DoTraverse(Graph g, Path p) {

        Vertex start = g.getFirstVertex();
        Vertex lastVertex = (p == null) ? start : p.getLastVertex();

        g.vertices.keySet().parallelStream().forEach(v_id -> {
            Vertex v = g.vertices.get(v_id);

            if (!v.equals(lastVertex)) {
                Path childPath = new Path(p);
                Edge e = lastVertex.getEdgeToVertex(v);
                if (childPath.canAddEdge(e)) {
                    childPath.setEdge(e);
                    DoTraverse(g, childPath);
                } else {
                    if (start.equals(v)) {
                        childPath.setEdge(e);
                        if (isPathComplete(g, childPath)) {
                            evaluatePath(childPath);
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
        if (lowestCostPath == null || (lowestCostPath.getCost() > p.getCost())) {
            lowestCostPath = p;
            log.info("New Path, Cost = " + p.getCost());
            System.out.println(p.getCost() + " " + p);
        }
        if (curCount % 1000_000_00 == 0) {
            logProgress(curCount, p);
        }
    }

    Instant prevInstant = Instant.now();

    private void logProgress(long curCount, Path p) {
        Instant cur = Instant.now();
        String timeElapsed = Duration.between(prevInstant, cur).toSeconds() + "|";
        log.info(formatNumberToBillions(curCount) + "|" + timeElapsed + "C + P |" + p.getCost() + "|" + p);
        prevInstant = cur;
    }

    public String formatNumberToBillions(long number) {
        long million = 1000000L;
        long billion = 1000000000L;
        long trillion = 1000000000000L;
        if ((number >= million) && (number < billion)) {
            float fraction = calculateFraction(number, million);
            return fraction + "M";
        } else if ((number >= billion) && (number < trillion)) {
            float fraction = calculateFraction(number, billion);
            return fraction + "B";
        }else {
            float fraction = calculateFraction(number, billion);
            return fraction + "T";
        }
    }

    public float calculateFraction(long number, long divisor) {
        float truncate = (number * 10L + (divisor / 2L)) / divisor;
        float fraction = (float) truncate * 0.10F;
        return fraction;
    }


    boolean isPathComplete(Graph g, Path p) {
        boolean complete = false;
        Vertex fist = g.getFirstVertex();
        Vertex last = p.getLastVertex();
        if (last.equals(fist)) {
            int verticesInGraph = g.vertices.size();
            int verticesInPath = p.getVerticesCount();
            if ((verticesInPath -1) == verticesInGraph) {
                //Complete path will include origin twice (stat and completes at origin
                complete = true;
            }
        }
        return complete;
    }

    public static void main(String[] args) {
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "[%1$tF %1$tT] [%4$-4s] %5$s %n");

        log.setLevel(Level.INFO);
        String filePath = Config.DATA_FILE;
        log.info("Reading " + filePath);
        Graph g = GraphReader.readFromXML(filePath);

        Traverse t = new Traverse();
        t.DoTraverse(g, null);
        log.info("done");
        System.out.println("Lowest cost " + t.lowestCostPath.getCost());
        System.out.println("Path = "+t.lowestCostPath.toString());
    }
}
