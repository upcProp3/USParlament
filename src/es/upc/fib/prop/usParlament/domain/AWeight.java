package es.upc.fib.prop.usParlament.domain;

import java.util.Map;
import java.util.Vector;

/**
 * Created by alexmirocat on 14/4/15.
 */

//TODO: pre & posts

public class AWeight {

    private Vector<Map.Entry<Attribute,Attribute>> ca; //Vector of common attributes.

    public Vector<Map.Entry<Attribute,Attribute>> getCommonAttributes() { return ca; }

    public void setCommonAttributes(Vector<Map.Entry<Attribute,Attribute>> ca) { this.ca = ca; }

    public int computeWeight(int div) {
        int w = 0; //0 = no relationship
        for (Map.Entry<Attribute,Attribute> aa : ca) {
            if (aa.getKey().equals(aa.getValue())) {
                w += aa.getKey().getImportance();
            }
        }
        if (div != 0) {
            int maxW = 3*ca.size(); //maximum grade of relationship
            w /= maxW * div;
        }
        return w;
    }


}
