package prop13.graphlibrary;

import java.util.*;

/**
 * Created by miquel on 20/03/15.
 */
public abstract class Graph
{
    /**
     * As you can see the graph is created using the interfaces Map and Set
       (if you don't know what a java interface is google it), this way if you
       need to keep the edges or the nodes in a certain order you can use
       the type of Map/Set (See java.utils documentation) according to your needs,
       In this implementation I will use LinkedHashedMap because I don't care much about the order
       so it uses the entry one. If you are interested in a certain ordering consider using TreeMaps
       (all valid for sets too)
     */
    private Map<Node,Set<Edge>> graph;

	public Map<Node, Set<Edge>> getGraph() {
		return graph;
	}

	/**
	 * Creates an empty graph
	 */
    public Graph()
    {
        graph = new LinkedHashMap<Node,Set<Edge>>();
    }

	/**
	 * returns true if the node is in the graph, false otherwise
	 * @param n
	 * @return
	 */
    public boolean nodeExists(Node n) {
        return graph.containsKey(n);
    }

	/**
	 * The argument is added to the graph, without adjacencies
	 IF IT ALREADY EXISTS ALL OF ITS EDGES WILL BE DELETED, WITHOUT DELETING ITS NEIGHBORS EDGES
	 AS A RESULT THE GRAPH WILL BE IN AN ERRONEOUS STATE. CONSIDER YOURSELF WARNED
	 * @param n
	 */
    public void addNode(Node n) {
        graph.put(n,new LinkedHashSet<Edge>());
    }

	/**
	 * The node n is deleted, so are all its edges, including its neighbors edges
	 * @param n
	 */
    public void deleteNode(Node n) {
        //First we gotta delete all the neighbors edges
        Set<Edge> es = graph.get(n);
        //We look at each Edge and delete it from its neighbor's set
        for(Edge e : es){
            graph.get(e.getNeighbor(n)).remove(e);
        }
        //Finally, we remove the node from the graph (its edges implicitly)
        graph.remove(n);

    }

	/**
	 * If n1 and n2 are part of the graph, an edge between n1 and n2 with weight w is added to the graph
	 A reference to the edge is returned
	 * @param e
	 */
    public void addEdge(Edge e) {
        Node n1 = e.getNode();
        Node n2 = e.getNeighbor(n1);
        graph.get(n1).add(e);
        graph.get(n2).add(e);
    }

	/**
	 * If n1 and n2 are part of the graph, an edge between n1 and n2 with weight w is deleted from the graph
	 A reference to the edge is returned
	 * @param n1
	 * @param n2
	 */
    public void deleteEdge(Node n1,Node n2) {
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


	/**
	 * deletes the edge from the graph
	 * @param e
	 */
    public void deleteEdge(Edge e)
    {
        Node n1 = e.getNode();
        Node n2 = e.getNeighbor(n1);
        graph.get(n1).remove(e);
        graph.get(n2).remove(e);
    }

	/**
	 * returns the set of edges of that node DO NOT MODIFY IT DIRECTLY
	 * @param n
	 * @return
	 */
    public Set<Edge> nodeEdges(Node n)
    {
        return graph.get(n);
    }

}
