package prop13.graphlibrary;

import java.util.*;

/**
 * Created by miquel on 20/03/15.
 */
public abstract class Graph
{
    /*
        As you can see the graph is created using the interfaces Map and Set
        (if you don't know what a java interface is google it), this way if you
        need to keep the edges or the nodes in a certain order you can use
        the type of Map/Set (See java.utils documentation) according to your needs,
        In this implementation I will use LinkedHashedMap and LinkedHashedSet so it
        keeps the entry order (easier to debug if we have a little consistency)

        How to use this class:
            -Add nodes and edges (to add an edge the nodes that form it must be pat of the graph).
            -Get a set's neighbors by getting it's set of edges and using Edge.getNeighbor
            -Delete Edges and Nodes if you need to modify the graph




        If you want to change the type of map used, overload the constructors
        If you want to change the type of set used, overload the addNode function
     */
    protected Map<Node,Set<Edge>> graph;

    public Graph()
            //Pre:True
            //Post:An empty graph is created
    {
        graph = new LinkedHashMap<Node,Set<Edge>>();
    }

    public boolean nodeExists(Node n)
            //Pre:True
            //Post: Return true if the node is part of the graph, false otherwise
    {
        return graph.containsKey(n);
    }
    public void addNode(Node n)
            //Pre: The node is NOT part of the graph
            //Post: The node is added to the graph, without any edge connected to i
            // If the node already exists its set of edges will be deleted but they will
            // not be deleted from its neighbor's, therefore the graph will become unusable.
            // It is not checked for speed's sake, if you want safety you can overload it.
    {
        graph.put(n,new LinkedHashSet<Edge>());
    }
    public void deleteNode(Node n)
            //Pre: The node n is part of the graph (I DO NOT KNOW IF IT WORKS WHEN n IS NOT PART OF THE GRAPH)
            //Post: The node n is not part of the graph anymore
    {
        //First we gotta delete all the neighbors edges
        Set<Edge> es = graph.get(n);
        //We look at each Edge and delete it from its neighbor's set
        for(Edge e : es){
            graph.get(e.getNeighbor(n)).remove(e);
        }
        //Finally, we remove the node from the graph (its edges implicitly)
        graph.remove(n);

    }

    public void addEdge(Edge e)
            //Pre: The edge e is not part of the graph AND there is NOT any edge between the nodes that
            // form e
            //Post: The edge e is added between n1 and n2
    {
        Node n1 = e.getNode();
        Node n2 = e.getNeighbor(n1);
        graph.get(n1).add(e);
        graph.get(n2).add(e);
    }

    public void deleteEdge(Node n1,Node n2)
            //Pre: n1 and n2 are part of the graph
            //Post: The edge between n1 and n2 is deleted from the graph
    {
        //We need to get the list of edges of both nodes and delete the edge

        Set<Edge> se1 = graph.get(n1);
        Set<Edge> se2 = graph.get(n2);

        for(Edge e:se1){
            if(e.getNeighbor(n1)==n2) {
                se1.remove(e);
                break;
            }
        }
        for(Edge e:se2){
            if(e.getNeighbor(n2)==n1){
                se2.remove(e);
                break;
            }
        }

    }


    public void deleteEdge(Edge e)
            //Pre: The edge e is part of the graph
            //Post: The edge e is deleted from the graph
    {
        Node n1 = e.getNode();
        Node n2 = e.getNeighbor(n1);
        graph.get(n1).remove(e);
        graph.get(n2).remove(e);
    }

    public Set<Edge> nodeEdges(Node n)
            //Pre: The node n is part of the graph
            //Post: The set of edges of the node n is returned
    {
        return graph.get(n);
    }

}
