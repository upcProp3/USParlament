package prop13.graphlibrary;

/**
 * Created by miquel on 20/03/15.
 */
public abstract class Edge
{
    private Node n1;
    private Node n2;
    private int weight;

	public Node getN1() {
		return n1;
	}

	public Node getN2() {
		return n2;
	}

	public int getWeight() {
		return weight;
	}

	public void setN1(Node n1) {
		this.n1 = n1;
	}

	public void setN2(Node n2) {
		this.n2 = n2;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

    public Edge(Node m1,Node m2,int w)
    {
        n1 = m1;
        n2 = m2;
        weight = w;
    }

	/**
	 * Given one of :the two nodes of the Edge, it returns the other one.
	 It is implemented like this due to the fact that it is not a directed edge
	 therefore there no "origin" or "destiny" node.
	 * @param n
	 * @return
	 */
    public Node getNeighbor(Node n)
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

	/**
	 *
	 * @return RANDOM node of the edge
	 */
    public Node getNode()
    {
        return n1;
    }


}
