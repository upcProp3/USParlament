package es.upc.fib.prop.usParlament.domain;

import es.upc.fib.prop.shared13.Edge;
import es.upc.fib.prop.shared13.Node;

/**
 * Created by miquel on 20/03/15.
 */
public final class TEdge extends Edge
{
	public TEdge(Node m1, Node m2, int w) {
		super(m1, m2, w);
	}

	@Override
	public String toString() {
		return "(" + getNode() + "," + getNeighbor(getNode()) + ")";
	}
}