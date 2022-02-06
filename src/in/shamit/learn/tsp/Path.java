package in.shamit.learn.tsp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Path {
    Path parentPath = null;
    Set<Vertex> vertices = new HashSet<>();
    Edge edge = null;

    public Path() {
    }

    public Path(Path parentPath) {
        this.parentPath = parentPath;
        if (parentPath != null) {
            vertices.addAll(parentPath.getVertices());
        }
    }


    public boolean doesIncludeVertex(Vertex v) {
        return vertices.contains(v);
    }

    public boolean canAddEdge(Edge e) {
        return !vertices.contains(e.getDestination());
    }


    public int getCost() {
        int cost = 0;
        if (edge != null) {
            cost += edge.getWeight();
        }
        if (parentPath != null) {
            cost += parentPath.getCost();
        }
        return cost;
    }

    public void setEdge(Edge e) {
        if (edge == null) {
            edge = e;
            vertices.add(e.getOrigin());
            vertices.add(e.getDestination());
        } else {
            throw new RuntimeException("Edge is Read only. Can't be modified once set");
        }

    }


    public Vertex getLastVertex() {
        if (edge == null) {
            if (parentPath != null) {
                return parentPath.getLastVertex();
            }
            return null;
        } else {
            return edge.getDestination();
        }
    }

    public int edgeCount() {
        int count = 0;
        if (edge != null) {
            count++;
        }
        if (parentPath != null) {
            count += parentPath.edgeCount();
        }
        return count;
    }

    public int getParentCount() {
        int count = 0;
        if (parentPath != null) {
            count++;
            count += parentPath.getParentCount();
        }
        return count;
    }

    public String getEdgesAsString() {
        StringBuilder ret = new StringBuilder();

        if (parentPath != null) {
            List<String> parentStrs = new ArrayList<>();
            Path parent = this.parentPath;
            while (parent != null) {
                parentStrs.add(parent.getEdgesAsStringExcludeParent());
                parent = parent.parentPath;
            }
            for (int i = parentStrs.size(); i > 0; i--) {
                ret.append(parentStrs.get(i - 1) + "|");
            }
        }
        ret.append(getEdgesAsStringExcludeParent());
        return ret.toString();
    }

    String getEdgesAsStringExcludeParent() {
        StringBuilder ret = new StringBuilder();
        if (edge != null) {
            ret.append(edge + " ");
        }
        return ret.toString();
    }

    Path getRootParent() {
        if (parentPath == null) {
            return this;
        } else {
            Path ret = parentPath;
            while (ret.parentPath != null) {
                ret = ret.parentPath;
            }
            return ret;
        }
    }

    public Set<Vertex> getVertices() {
        return vertices;
    }

    @Override
    public String toString() {
        return "Path{" +
                "edges=" + getEdgesAsString() +
                '}';
    }
}