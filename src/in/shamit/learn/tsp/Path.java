package in.shamit.learn.tsp;

import java.util.ArrayList;
import java.util.List;

public class Path {
    Path parentPath=null;

    public Path() {
    }

    public Path(Path parentPath) {
        this.parentPath = parentPath;
    }

    List<Edge> edges = new ArrayList<>();
    public boolean doesIncludeVertex(Vertex v){
        for(var e:edges){
            if(e.getDestination().equals(v) ){
                return true;
            }
        }
        if(parentPath != null){
            return parentPath.doesIncludeVertex(v);
        }
        return false;
    }

    public boolean canAddEdge(Edge e){
        for(var e1:edges){
            if(e.getDestination().equals(e1.getDestination()) || e.getDestination().equals(e1.getOrigin())){
                return false;
            }
        }
        if(parentPath != null){
            return parentPath.canAddEdge(e);
        }
        return true;
    }

    boolean doesTerminateAt(Vertex v){
        if(edges.size()==0){
            if(parentPath != null){
                return  parentPath.doesTerminateAt(v);
            }
            return false;
        }else{
            return edges.get(edges.size()-1).equals(v);
        }
    }

    public int getCost(){
        int cost = 0;
        for(var e:edges){
            cost += e.getWeight();
        }
        if(parentPath != null){
            cost += parentPath.getCost();
        }
        return cost;
    }

    public void addEdge(Edge e){
        edges.add(e);
    }


    public Vertex getLastVertex() {
        if(edges.size()==0){
            if(parentPath != null){
                return parentPath.getLastVertex();
            }
            return null;
        }else{
            Edge e = edges.get(edges.size()-1);
            return e.getDestination();
        }
    }

    public int edgeCount() {
        int count = edges.size();
        if(parentPath!=null){
            count +=parentPath.edgeCount();
        }
        return count;
    }

    public int getParentCount(){
        int count = 0;
        if(parentPath!=null){
            count++;
            count += parentPath.getParentCount();
        }
        return count;
    }

    public String getEdgesAsString(){
        StringBuilder ret = new StringBuilder();

        if(parentPath!=null){
            List<String> parentStrs= new ArrayList<>();
            Path parent = this.parentPath;
            while(parent!=null){
                parentStrs.add(parent.getEdgesAsStringExcludeParent());
                parent = parent.parentPath;
            }
            for(int i=parentStrs.size();i>0;i--){
                ret.append(parentStrs.get(i-1)  + "|");
            }
        }
        ret.append(getEdgesAsStringExcludeParent());
        return ret.toString();
    }

    String getEdgesAsStringExcludeParent(){
        StringBuilder ret = new StringBuilder();
        for(var e:edges){
            ret.append(e + " ");
        }
        return ret.toString();
    }

    Path getRootParent(){
        if(parentPath==null){
            return this;
        }else {
            Path ret = parentPath;
            while(ret.parentPath!=null){
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
}
