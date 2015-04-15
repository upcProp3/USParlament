package es.upc.fib.prop.shared13.louvain;

import es.upc.fib.prop.shared13.*;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by miquel on 30/03/15.
 */
public class LouvainGraph extends Graph
{
    private Map<Node,Integer> nw;
    int weight;

    public LouvainGraph()
    {
        super();
        weight = 0;
        nw = new LinkedHashMap<Node,Integer>();
    }

    //TODO: add  a way to create a Louvaingraph from a graph

    /**
     * @pre The Node n is not part of the graph
     * @post The Node n is part of the graph
     * @param n
	 * The argument is added to the graph, without adjacencies
	 IF IT ALREADY EXISTS ALL OF ITS EDGES WILL BE DELETED, WITHOUT DELETING ITS NEIGHBORS EDGES
	 AS A RESULT THE GRAPH WILL BE IN AN ERRONEOUS STATE. CONSIDER YOURSELF WARNED
	 */
    @Override
    public void addNode(Node n)
    {
        super.addNode(n);
        nw.put(n,0);
    }

    @Override
    public void removeNode(Node n)
    {
        weight-=nw.get(n);
        nw.remove(n);
        for(Edge e:super.nodeEdges(n)) {
            nw.put(e.getNeighbor(n),nw.get(e.getNeighbor(n))-e.getWeight());
        }
        super.removeNode(n);
    }


    //We update the weights and call the superclass
    @Override
    public void addEdge(Edge e)
    {
        weight+=e.getWeight();
        Node n1 = e.getNode();
        Node n2 = e.getNeighbor(n1);
        nw.put(n1,new Integer(nw.get(n1)+e.getWeight()));
        nw.put(n2,new Integer(nw.get(n2)+e.getWeight()));
        super.addEdge(e);
    }

    @Override
    public void removeEdge(Node n1, Node n2)
    {
        Set<Edge> se1 = super.nodeEdges(n1);
        Set<Edge> se2 = super.nodeEdges(n2);

        for(Edge e:se1){
            if(e.getNeighbor(n1)==n2) {
                nw.put(n1,nw.get(n1)-e.getWeight());
                break;
            }
        }
        for(Edge e:se2){
            if(e.getNeighbor(n2)==n1){
                nw.put(n2,nw.get(n1)-e.getWeight());
                break;
            }
        }
        super.removeEdge(n1, n2);
    }

    @Override
    public void removeEdge(Edge e)
    {
        weight+=e.getWeight();
        Node n1 = e.getNode();
        Node n2 = e.getNeighbor(n1);
        nw.put(n1,new Integer(nw.get(n1)-e.getWeight()));
        nw.put(n2,new Integer(nw.get(n2)-e.getWeight()));
        super.removeEdge(e);
    }
    
    public int getTotalWeight()
    {
        return weight;
    }

    public int getNeighborsWeight(Node n)
    {
        return nw.get(n);
    }
    
    

}
