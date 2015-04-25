package es.upc.fib.prop.shared13.louvain;

import es.upc.fib.prop.shared13.Edge;
import es.upc.fib.prop.shared13.Graph;
import es.upc.fib.prop.shared13.Node;

import java.util.*;

/**
 * Created by miquel on 24/04/15.
 */
public class LouvainGraph extends Graph
{
    private Map<Node,Double> grauNode;
    private Double pes;
    public LouvainGraph(){
        super();
        pes = 0.;
        grauNode = new LinkedHashMap<>();
    }

    public LouvainGraph(Graph g)
    {

        grauNode = new LinkedHashMap<>();

        pes = 0.;
        for(Node n:g.getNodes()){
            grauNode.put(n,0.);
        }


        for(Edge e:g.getEdges()){
            pes+=e.getWeight();
            double temp1 = grauNode.get(e.getNode())+e.getWeight();
            grauNode.put(e.getNode(),temp1);
            double temp2 = grauNode.get(e.getNeighbor(e.getNode())) + e.getWeight();
            grauNode.put(e.getNode(),temp2);
        }
    }

    public Double getPes()
    {
        return pes;
    }
    public Double getWDegree(Node n){return grauNode.get(n);}
    @Override
    public void removeAllNodeEdges(Node n)
    {
        Collection<Edge> conj = super.getAdjacencyList(n);
        for(Edge e:conj){
            double temp = grauNode.get(e.getNeighbor(n))-e.getWeight();
            grauNode.put(e.getNeighbor(n),temp);
            pes-=e.getWeight();
        }
        grauNode.put(n,0.);
        super.removeAllNodeEdges(n);
    }

    @Override
    public boolean removeNode(Node n)
    {
        Collection<Edge> conj = super.getAdjacencyList(n);
        for(Edge e:conj){
            double temp = grauNode.get(e.getNeighbor(n))-e.getWeight();
            grauNode.put(e.getNeighbor(n),temp);
            pes-=e.getWeight();
        }

        grauNode.remove(n);
        return super.removeNode(n);
    }

    @Override
    public boolean removeEdge(Node n1, Node n2)
    {
        Edge e = getEdge(n1,n2);
        double temp1 = grauNode.get(n1)-e.getWeight();
        double temp2 = grauNode.get(n2)-e.getWeight();
        if(n1.equals(n2)){
            temp1-=e.getWeight();
            temp2-=e.getWeight();
        }
        grauNode.put(n1,temp1);
        grauNode.put(n2,temp2);
        pes-=e.getWeight();

        return super.removeEdge(n1, n2);
    }

    @Override
    public boolean addEdge(Edge e)
    {

        pes+=e.getWeight();
        double temp1 = grauNode.get(e.getNode());
        double temp2 = grauNode.get(e.getNeighbor(e.getNode()));
        temp1+=e.getWeight();
        temp2+=e.getWeight();
        if(e.getNode().equals(e.getNeighbor(e.getNode()))){
            temp1+=e.getWeight();
            temp2+=e.getWeight();
        }
        grauNode.put(e.getNode(),temp1);
        grauNode.put(e.getNeighbor(e.getNode()),temp2);
        return super.addEdge(e);
    }

    @Override
    public boolean addNode(Node n)
    {
        grauNode.put(n, 0.);
        return super.addNode(n);
    }

    @Override
    public String toString()
    {
        String s;
        s="Printing Weighted Graph info\n";
        s+="Total weight: "+pes+"\n";
        s+="Node Degrees:\n";
        for(Node n:super.getNodes()){
           s+=(n+": "+grauNode.get(n));
            s+="\n";
        }
        s+=super.toString();
        return s;
    }
}
