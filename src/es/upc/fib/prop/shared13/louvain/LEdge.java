package es.upc.fib.prop.shared13.louvain;

import es.upc.fib.prop.shared13.Edge;
import es.upc.fib.prop.shared13.Node;

/**
 * Created by Miquel Jubert on 29/04/15.
 */
public class LEdge extends Edge
{
    //It doesn't need to do anything special
    public LEdge(Node m1, Node m2, double w, boolean valid) {
        super(m1, m2, w, valid);
    }
    public LEdge(Node m1, Node m2, double w) {
        super(m1, m2, w);
    }
}
