package es.upc.fib.prop.usParlament.domain;

import es.upc.fib.prop.shared13.Graph;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by ondrej on 15.4.15.
 * contributions of alex.
 */
public class Congress extends Graph {

    private Collection<MP> MPs;

    public Collection<MP> getMPs() { return MPs; }

    public Congress() {
        MPs = new HashSet<>();
    }

    public MP getMP(State state, int district) {
        for (MP m : MPs) {
            if (m.getDistrict()==district && m.getState().equals(state)) return m;
        }
        return null;
    }

    public void deleteMP(MP m) {
        MPs.remove(m);
    }

}
