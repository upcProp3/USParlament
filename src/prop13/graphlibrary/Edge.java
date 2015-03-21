package prop13.graphlibrary;

/**
 * Created by miquel on 20/03/15.
 */
public abstract class Edge
{
    protected Node n1;
    protected Node n2;
    protected int weight;

    public Edge()
            //It shouldn't ever be used
    {
    }

    public Edge(Node m1,Node m2,int w)
            //Pre: True
            //Post: Creates an edge between m1 and m2 with weight w
    {
        n1 = m1;
        n2 = m2;
        weight = w;
    }

    public Node getNeighbor(Node n)
            //Pre: n is one of the nodes of the edge
            //Post: The other node from the edge is returned
    {
        if(n==n1){
            return n2;
        }else if(n==n2){
            return n1;
        }
        return null;
    }

    public Node getNode()
            //Pre: True
            //Post: Returns one of the nodes of the edges. It will always return the same one.
    {
        return n1;
    }

    public int getWeight()
            //Pre: True
            //Post: Returns the edge's weight
    {
        return weight;
    }
}
