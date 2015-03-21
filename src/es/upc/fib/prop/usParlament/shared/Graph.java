package es.upc.fib.prop.usParlament.shared;


/**
 *
 */
public interface Graph {

	/**
	 *
	 * @param n
	 * @return
	 */
    public boolean containsNode(Node n);

	/**
	 *
	 * @param n
	 */
    public void addNode(Node n);

	/**
	 *
	 * @param n
	 */
    public void removeNode(Node n);

	/**
	 *
	 * @param e
	 */
    public void addEdge(Edge e);

	/**
	 *
	 * @param e
	 */
    public void removeEdge(Edge e);
}
