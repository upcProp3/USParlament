package es.upc.fib.prop.usParlament.domain;

import es.upc.fib.prop.shared13.Graph;
import es.upc.fib.prop.shared13.Node;
import es.upc.fib.prop.shared13.louvain.LouvainAlgorithm;

import java.util.Map;

/**
 * Created by Miquel Jubert on 2/05/15.
 */
public class ComparingAlgorithm {
    Graph graph;
    double mod1, mod2;
    Map<Node, Integer> best, p1, p2;

    /**
     * A ComparingAlgorithm instance is created with graph 'g', p1 'part1', p2 'part2'.
     * Their modularities are also computed.
     *
     * @param g     The graph where you want to apply the algorithm
     * @param part1 A partition of  graph g
     * @param part2 A partition of a graph g
     */
    public ComparingAlgorithm(Graph g, Map<Node, Integer> part1, Map<Node, Integer> part2) {
        graph = g;
        if (!part1.keySet().containsAll(g.getNodes()) || !part2.keySet().containsAll(g.getNodes()))
            throw new IllegalArgumentException("The partition must" +
                    "map all nodes to a community");
        mod1 = LouvainAlgorithm.calculateModularity(g, part1);
        mod2 = LouvainAlgorithm.calculateModularity(g, part2);
        p1 = part1;
        p2 = part2;
        if (mod1 > mod2) {
            best = part1;
        } else if (mod2 > mod1) {
            best = part2;
        } else {
            best = null;
        }

    }

    /**
     * @return The string form of a comparison. Possible outputs are: "Both partitions are equally good" or
     * "The best partition is:" (with its values).
     */
    public String toString() {
        if (best == null) return "Both partitions are equally good";
        String s = "The best partition is:" + best
                + "\nIt's modularity is:" + bestModularity()
                + " and the modularity of the other partition "
                + "is " + otherModularity();
        return s;
    }

    /**
     * @return The implicit ComparingAlgorithm best partition.
     */
    public Map<Node, Integer> bestPartition() {
        return best;
    }

    /**
     * @return The implicit ComparingAlgorithm best modularity value.
     */
    public Double bestModularity() {
        if (best == p1) return mod1;
        if (best == p2) return mod2;
        else return mod1;//If best is null both modularities are the same
    }

    /**
     * @return The implicit ComparingAlgorithm other modularity value (the one which is not the best).
     */
    public Double otherModularity() {
        if (best != p1) return mod1;
        if (best != p2) return mod2;
        else return mod1;//will never happen
    }
}
