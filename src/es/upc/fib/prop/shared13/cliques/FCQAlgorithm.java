package es.upc.fib.prop.shared13.cliques;

import es.upc.fib.prop.shared13.Algorithm;
import es.upc.fib.prop.shared13.Graph;
import es.upc.fib.prop.shared13.Node;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by miquel on 14/05/15.
 */
public class FCQAlgorithm implements Algorithm
{
    private double threshold;
    private Graph g;
    private ArrayList<Set<Node>> result;

    public FCQAlgorithm(Graph gr,double th)
    {
        threshold = th;
        g = gr;
        result = null;
    }

    @Override
    public ArrayList<Set<Node>> calculate()
    {
        if(result != null) return result;
        FourCliquePercolation fcq = new FourCliquePercolation(g,threshold);
        fcq.execute();
        result = fcq.getCc();
        return result;
    }
}
