package es.upc.fib.prop.usParlament.domain;

import es.upc.fib.prop.shared13.Edge;
import es.upc.fib.prop.shared13.Node;

/**
 * Created by ondrej on 15.4.15.
 */
public class Relationship extends Edge {

	/**
	 * @param m1
	 * @param m2
	 * @param w
	 * @pre True
	 * @post An Edge between the nodes n1 and n2 with weight w is created
	 */
	public Relationship(Node m1, Node m2, int w) {
		super(m1, m2, w);
	}
}
