package es.upc.fib.prop.shared13.louvain;

import es.upc.fib.prop.shared13.Node;

import java.util.Set;

/**
 * Created by Miquel Jubert on 25/04/15.
 */
public class CNode extends Node
{//Nodes with unique identifier
    static private Integer nextID = 0;
    private Integer id;
    private Set<Node> sons;

    public Integer getId()
    {
        return id;
    }

    public CNode()
    {
        id = CNode.nextID++;
    }

    @Override
    public boolean equals(Object o)
    {
        if(! o.getClass().equals(this.getClass())) return false;
        return ((CNode)o).id.equals(this.id);
    }

    @Override
    public int hashCode()
    {
        return id.hashCode();
    }

    @Override
    public String toString()
    {
        return "c"+id.toString();
    }

    @Override
    public int compareTo(Node n)
    {
        return ((CNode)n).getId()-this.id;
    }
}
