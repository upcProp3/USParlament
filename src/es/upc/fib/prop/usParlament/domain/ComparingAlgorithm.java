package es.upc.fib.prop.usParlament.domain;

import es.upc.fib.prop.shared13.Graph;
import es.upc.fib.prop.shared13.Node;
import es.upc.fib.prop.shared13.louvain.LouvainAlgorithm;

import java.util.Map;

/**
 * Created by miquel on 2/05/15.
 */
public class ComparingAlgorithm
{
    Graph graph;
    double mod1,mod2,percentBetter;
    Map<Node,Integer> best,p1,p2;

    public ComparingAlgorithm(Graph g,Map<Node,Integer> part1,Map<Node,Integer> part2)
    {
        graph = g;
        if(!part1.keySet().containsAll(g.getNodes())) throw new IllegalArgumentException("The partition must" +
                "map all nodes to a community");
        mod1 = LouvainAlgorithm.calculateModularity(g,part1);
        mod2 = LouvainAlgorithm.calculateModularity(g,part2);
        p1 = part1;
        p2 = part2;
        if(mod1>mod2){
            best = part1;
            percentBetter = ((mod1/mod2)-1)*100;
        } else if(mod2>mod1){
            best = part2;
            percentBetter = ((mod2/mod1)-1)*100;
        } else {
            percentBetter = 0.;
            best = null;
        }
        System.out.println(mod1+" "+mod2);

    }

    public String toString()
    {
        if(best == null) return "Both partitions are equally good";
        String s ="The best partition is:"+best
                + "\nIt's modularity is:"+bestModularity()
                + "and it is "+percentBetter+"% better than the other one"
                + " which is "+ otherModularity();
        return s;
    }

    public Map<Node,Integer> bestPartition()
    {
        return best;
    }

    public Double bestModularity()
    {
        if(best == p1) return mod1;
        if(best == p2) return mod2;
        else return mod1;//If best is null both modularities are the same
    }
    public Double otherModularity()
    {
        if(best != p1) return mod1;
        if(best != p2) return mod2;
        else return mod1;//will never gappen
    }
    public Double percentBetter()
    {
        return percentBetter;
    }

}
