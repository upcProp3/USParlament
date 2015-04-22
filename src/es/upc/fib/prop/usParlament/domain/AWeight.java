package es.upc.fib.prop.usParlament.domain;

import java.util.Set;
import java.util.Vector;

/**
 * Created by alexmirocat on 14/4/15.
 */

public class AWeight {

    private AttributeManager AIList; //Map of attributes' importances.

    private MP m1;
    private MP m2;

    private Set<String> ca = m1.getAttributeList(m2); //Set of common attributes (names).

    /**
     * @pre True
     * @post Returns the value of one MP of the pair
     * @return
     * Returns the value of one MP of the pair
     */
    public MP getM1() { return this.m1; }

    /**
     * @pre True
     * @post Returns the value of one MP of the pair
     * @return
     * Returns the value of one MP of the pair
     */
    public MP getM2() { return this.m2; }

    /**
     * @pre True
     * @post m1 is the value of one MP of the pair
     * @param m1
     * @return
     */
    public void setM1(MP m1) { this.m1 = m1; }

    /**
     * @pre True
     * @post m2 is the value of one MP of the pair
     * @param m2
     * @return
     */
    public void setM2(MP m2) { this.m2 = m2; }

    /**
     * @pre True
     * @post Returns the list of common attributes between two MPs
     * @return
     */
    public Set<String> getCommonAttributes() { return this.ca; }

    /**
     * @pre True
     * @post ca is the actual list of common attributes
     * @param ca
     * ca is the actual list of common attributes
     */
    public void setCommonAttributes(Set<String> ca) { this.ca = ca; }

    /**
     * @pre True
     * @post Returns the weight between two MPs, refactorized by div
     * @param div
     * @return
     * Returns the weight between two MPs, refactorized by div
     */
    public int computeWeight(int div) {
        int w = 0; //0 = no relationship
        for (String a : ca) {
            if (m1.getValue(a).equals(m2.getValue(a))) {
                w += AIList.getImportance(a);
            }
        }
        if (div != 0) {
            int maxW = 3*ca.size(); //maximum grade of relationship
            w /= maxW * div;
        }
        return w;
    }
}
