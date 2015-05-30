package es.upc.fib.prop.shared13.ncliques;

import es.upc.fib.prop.shared13.Algorithm;
import es.upc.fib.prop.shared13.Graph;
import es.upc.fib.prop.shared13.Node;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by miquel on 14/05/15.
 */
public class NCQAlgorithm implements Algorithm
{
    private Graph g;
    private ArrayList<Set<Node>> result;

    public NCQAlgorithm(Graph gr)
    {
        g = gr;
        result = null;
    }

    @Override
    public ArrayList<Set<Node>> calculate()
    {
        if(result != null) return result;
        NCliquePercolation ncq = new NCliquePercolation(g);
        ncq.execute();
        result = ncq.getCc();
        return result;
    }
}
