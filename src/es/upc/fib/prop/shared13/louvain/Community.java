package es.upc.fib.prop.shared13.louvain;

import es.upc.fib.prop.shared13.Edge;
import es.upc.fib.prop.shared13.Graph;
import es.upc.fib.prop.shared13.Node;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by miquel on 30/03/15.
 */
public class Community
{
    Subgraph s;

    public Community(LouvainGraph g)
    {
        s = new Subgraph(g);
    }

    //Also adds all the edges between nodes of the community
    public void addNode(Node n)
    {
        s.addNode(n);
        for(Edge e:s.nodeEdges(n)){
            if(s.nodeExists(e.getNeighbor(n))){
                s.addEdge(e);
            }
        }
    }

    public double getInsideWeight()
    {
        return s.getTotalWeight();
    }

    public double getNodeInsideWeight(Node n)
    {
        return s.getNeighborsWeight(n);
    }

    public void removeNode(Node n)
    {
        s.removeNode(n);
    }

    public boolean nodeExists(Node n)
    {
        return s.nodeExists(n);
    }

}
