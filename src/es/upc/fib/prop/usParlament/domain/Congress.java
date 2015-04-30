package es.upc.fib.prop.usParlament.domain;

import es.upc.fib.prop.shared13.Graph;
import es.upc.fib.prop.shared13.Node;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by ondrej on 15.4.15.
 */
public class Congress extends Graph {
	public Collection<MP> getMPs() {
		Collection<MP> mps = new HashSet<>();
		for (Node n : getNodes()) {
			mps.add((MP) n);
		}
		return mps;
	}

    public String toString()
    {
       return "US Congressman list:\n"
               + this.getNodes();
    }

    public MP getMP(State s,int d)
    {
        MP m = new MP("INVALID",s,d);
        for(MP mp:this.getMPs()){
            if(mp.equals(m)){
                return mp;
            }
        }
        return null;
    }
}
