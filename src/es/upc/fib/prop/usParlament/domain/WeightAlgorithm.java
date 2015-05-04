package es.upc.fib.prop.usParlament.domain;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by alex on 14/4/15.
 * contributions of ondrej, aleixsacrest.
 */

public class WeightAlgorithm {

    private Congress c;

    /**
     * @param c A congress where we want ot apply the algorithm
     * Default constructor
     */
    public WeightAlgorithm(Congress c) {
        this.c = c;
    }

    /**
     * @pre m1 and m2 are two MPs of the congress C
     * @post Returns the list of common attributes between m1 and m2
     * @param m1 An MP of the congress
     * @param m2 An MP of the congress
     * @return set of common attributes between m1 and m2
     */
    public Set<Attribute> getCommonAttributes(MP m1, MP m2) {
        Set<Attribute> ret = new HashSet<>();
        ret.addAll(m1.getAttributes());
        ret.retainAll(m2.getAttributes());
        return ret;
    }

    /**
     * @pre m1 and m2 are two MPs of the congress C, CA is the set of common attributes between them
     * @post The weight between m1 and m2 is added to C.
     * @param m1 An MP of the congress
     * @param m2 An MP of the congress
     * @param CA The Set of attributes m1 and m2 have in common
     * The weight between m1 and m2 is added to C.
     */
    public void computeWeight(MP m1, MP m2, Set<Attribute> CA) {
        int w = 0; //0 = no relationship
        for (Attribute a : CA) {
            int imp = a.getDefinition().getImportance();
            if(imp == 1) w+=1;
            if(imp == 2) w+=4;
            if(imp == 3) w+=16;
        }
        if(w>0) {
            if (!c.hasEdge(m1,m2)) c.addEdge(new Relationship(m1,m2,w));
            else c.getEdge(m1,m2).setWeight(c.getEdge(m1,m2).getWeight()+w);
        }
    }

    /**
     * @pre True
     * @post The weights of all the graph are computed, and the edges added
     * The weights of all the graph are computed, and the edges added
     */
    public void computeAllWeights() {
        Collection<MP> mps = c.getMPs();
        for (MP m1 : mps) {
            for (MP m2 : mps) {
                if (m1 != m2 && !c.hasEdge(m1, m2)) {
                    Set<Attribute> ca = getCommonAttributes(m1, m2);
                    if (!ca.isEmpty()) {
                        computeWeight(m1, m2, ca);
                    }
                }
            }
        }
    }


}
