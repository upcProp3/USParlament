package es.upc.fib.prop.shared13.louvain;

import es.upc.fib.prop.shared13.Node;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by miquel on 30/03/15.
 */
public class Community
{
    Set<Node> s;

    public Community()
    {
        s = new LinkedHashSet<Node>();
    }

    public void addNode(Node n)
    {
        s.add(n);
    }

    public boolean nodeExists(Node n)
    {
        return s.contains(n);
    }
}
