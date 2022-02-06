package in.shamit.learn.tsp;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class Graph {
 Map<Integer,Vertex> vertices = new HashMap<>();

 Vertex firstVertex = null;
 public Vertex getOrCreateVertex(int id){
  if(vertices.containsKey(id)){
   return vertices.get(id);
  }else{
   Vertex v = new Vertex(id);
   if(firstVertex ==null){
    firstVertex = v;
   }
   vertices.put(id,v);
   return v;
  }
 }

 public Collection<Vertex> getVerticesAsSet() {
  return vertices.values();
 }

 public void setVertices(Map<Integer, Vertex> vertices) {
  this.vertices = vertices;
 }

 public Vertex getFirstVertex() {
  return firstVertex;
 }


}
