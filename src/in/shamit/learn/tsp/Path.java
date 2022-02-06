package in.shamit.learn.tsp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Path {
    Path parentPath = null;

    Edge edge = null;

    public Path() {
    }

    public Path(Path parentPath) {
        this.parentPath = parentPath;
    }


    public boolean canIncludeVertexAsDestination(Vertex v) {
        if(edge != null){
            if(v.id == edge.destination.id){
                return false;
            }else{
                if(parentPath!=null){
                    return parentPath.canIncludeVertexAsDestination(v);
                }else{
                    //This is top-most parent. Its origin can't be added as destination
                    if(v.id == edge.origin.id){
                        return false;
                    }
                }
            }
        }else{
            if(parentPath!=null){
                return parentPath.canIncludeVertexAsDestination(v);
            }
        }
        return true;
    }

    public boolean canAddEdge(Edge e) {
        Vertex destination = e.getDestination();
        boolean canInclude = canIncludeVertexAsDestination(e.getDestination());
        return canInclude;
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

    @Override
    public String toString() {
        return "Path{" +
                "edges=" + getEdgesAsString() +
                '}';
    }

    public int getVerticesCount() {
        int count=0;
        if(edge!=null){
            count ++;
        }
        Path curPath = this;
        while(curPath!=null){
            count++;
            curPath = curPath.parentPath;
        }
        return count;
    }
}