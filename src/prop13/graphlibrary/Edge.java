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
    {
    }

    public Edge(Node m1,Node m2,int w)
    {
        n1 = m1;
        n2 = m2;
        weight = w;
    }

    public Node getNeighbor(Node n)
    /* Given one of :the two nodes of the Edge, it returns the other one.
       It is implemented like this due to the fact that it is not a directed edge
       therefore there no "origin" or "destiny" node.
     */
    {
        if(n==n1){
            return n2;
        }else if(n==n2){
            return n1;
        }else{
            System.out.println("Not part of the edge");
            return null;
        }
    }

    public Node getNode()
    /* returns a RANDOM node of the edge*/
    {
        return n1;
    }

    public int getWeight()
            /* Weight getter */
    {
        return weight;
    }
}
