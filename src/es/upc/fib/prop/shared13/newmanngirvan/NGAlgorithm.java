package es.upc.fib.prop.shared13.newmanngirvan;

import es.upc.fib.prop.shared13.Algorithm;
import es.upc.fib.prop.shared13.Graph;
import es.upc.fib.prop.shared13.Node;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by miquel on 14/05/15.
 */
public class NGAlgorithm implements Algorithm
{
    private NewmanGirvan ng;
    private ArrayList<Set<Node>> result;
    private Graph g;
    public int ncom;

    public NGAlgorithm(Graph gr,int num)
    {
        g = gr;
        ncom = num;
        ng = new NewmanGirvan();
        result = null;
    }

    public ArrayList<Set<Node>> calculate()
    {
        result = ng.runAlgorithm(g,ncom);
        return result;
    }

}
