package in.shamit.learn.tsp.io;

import in.shamit.learn.tsp.Edge;
import in.shamit.learn.tsp.Graph;
import in.shamit.learn.tsp.Vertex;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GraphReader {

    public static Graph readFromXML(String filePath)  {
        Document doc = readXML(filePath);
        try{
            Graph ret = new Graph();
            var rootNode = doc.getDocumentElement();
            Element graphNode = (Element)rootNode.getElementsByTagName("graph").item(0);
            var vertexNodes = graphNode.getElementsByTagName("vertex");
              for(int i=0; i<vertexNodes.getLength();i++){
                List<Edge> edges = new ArrayList<>();
                Element vNode = (Element) vertexNodes.item(i) ;
                System.out.println(vNode.getTagName());
                var edgeNodes = vNode.getElementsByTagName("edge");
                int vertex_id = -1;
                for(int j=0;j<edgeNodes.getLength();j++){
                    Element eNode = (Element)edgeNodes.item(j);
                    String destStr = eNode.getTextContent();
                    String costStr = eNode.getAttribute("cost");
                    int cost =  Double.valueOf(costStr).intValue();
                    int dest = Double.valueOf(destStr).intValue();
                    if(cost ==9999 ){
                        vertex_id = dest;
                    }
                    System.out.printf(destStr +":"+cost + ";\t\t");
                    Vertex destVertex = ret.getOrCreateVertex(dest);
                    Edge e = new Edge(null,destVertex,cost);
                    edges.add(e);
                }
                if(vertex_id == -1){
                    throw new RuntimeException("ID could not be identified for Vertex");
                }
                Vertex v = ret.getOrCreateVertex(vertex_id);
                for(var e:edges){
                    e.setOrigin(v);
                }
                v.setEdges(edges);
                System.out.println("Vortex ID "+vertex_id);
            }
            return ret;

        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    private static Document readXML(String filePath) {
        try{
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newDefaultInstance();
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

            // parse XML file
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(filePath));
            return doc;
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }
}
