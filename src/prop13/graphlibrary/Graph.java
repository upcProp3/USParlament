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
       In this implementation I will use LinkedHashedMap because I don't care much about the order
       so it uses the entry one. If you are interested in a certain ordering consider using TreeMaps
       (all valid for sets too)
     */
    protected Map<Node,Set<Edge>> graph;

    public Graph()
        /* Creates an empty graph */
    {
        graph = new LinkedHashMap<Node,Set<Edge>>();
    }

    public boolean nodeExists(Node n)
           /*
                 returns true if the node is in the graph, false otherwise
           */
    {
        return graph.containsKey(n);
    }
    public void addNode(Node n)
        /* The argument is added to the graph, without adjacencies
            IF IT ALREADY EXISTS ALL OF ITS EDGES WILL BE DELETED, WITHOUT DELETING ITS NEIGHBORS EDGES
            AS A RESULT THE GRAPH WILL BE IN AN ERRONEOUS STATE. CONSIDER YOURSELF WARNED
         */
    {
        graph.put(n,new LinkedHashSet<Edge>());
    }
    public void deleteNode(Node n)
            /*The node n is deleted, so are all its edges, including its neighbors edges*/
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
        /* If n1 and n2 are part of the graph, an edge between n1 and n2 with weight w is added to the graph
            A reference to the edge is returned
         */
    {
        Node n1 = e.getNode();
        Node n2 = e.getNeighbor(n1);
        graph.get(n1).add(e);
        graph.get(n2).add(e);
    }

    public void deleteEdge(Node n1,Node n2)
        /* If n1 and n2 are part of the graph, an edge between n1 and n2 with weight w is deleted from the graph
            A reference to the edge is returned
         */
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
        /*deletes the edge from the graph*/
    {
        Node n1 = e.getNode();
        Node n2 = e.getNeighbor(n1);
        graph.get(n1).remove(e);
        graph.get(n2).remove(e);
    }

    public Set<Edge> nodeEdges(Node n)
        /*returns the set of edges of that node DO NOT MODIFY IT DIRECTLY*/
    {
        return graph.get(n);
    }

}
