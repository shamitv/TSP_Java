package in.shamit.learn.tsp;

import java.util.ArrayList;
import java.util.List;

public class Vertex {
    public List<Edge> edges = new ArrayList<>();
    int id;

    @Override
    public String toString() {
        return "Vertex{" +
                "#edges=" + edges.size() +
                ", id=" + id +
                '}';
    }

    public Vertex(int id) {
        this.id = id;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vertex)) return false;

        Vertex vertex = (Vertex) o;

        return getId() == vertex.getId();
    }

    @Override
    public int hashCode() {
        return getId();
    }

    public Edge getEdgeToVertex(Vertex v) {
        for(var e:edges){
            if(e.getDestination().equals(v)){
                return e;
            }
        }
        return null;
    }
}
