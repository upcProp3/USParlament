package es.upc.fib.prop.shared13.louvain;

import es.upc.fib.prop.shared13.*;

/**
 * Created by miquel on 30/03/15.
 */
public class Subgraph extends LouvainGraph
{
    private Graph father;

    /**
     * @pre g is not null
     * @post A graph is created with g set as its father
     * @param g
     */
    public Subgraph(Graph g)
    {
        super();
        father = g;
    }

    /**
     * @pre True
     * @post n is added to the graph, if it is part of the fathergraph
     * @param n
     * If its part of the father's graph, the argument is added to the graph, without adjacencies
    IF IT ALREADY EXISTS ALL OF ITS EDGES WILL BE DELETED, WITHOUT DELETING ITS NEIGHBORS EDGES
     */
    @Override
    public void addNode(Node n)
    {
        if(father.nodeExists(n)) {
            super.addNode(n);
        }else {
            System.out.println("Node not in father");
        }
    }

    /**
     * @pre True
     * @post If e is part of the fathe graph, it is added to the subgraph
     * @param e
     */
    @Override
    public void addEdge(Edge e)
    {
        if(father.edgeExists(e)) {
            super.addEdge(e);
        }else{
            System.out.println("Edge not in father");
        }
    }





}




