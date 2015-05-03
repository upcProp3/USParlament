package es.upc.fib.prop.usParlament.domain;

import es.upc.fib.prop.shared13.Graph;
import es.upc.fib.prop.shared13.Node;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by ondrej on 15.4.15.
 * contributions aleixsacrest, alex
 */
public class Congress extends Graph {
    private static Set<AttrDefinition> s = new HashSet<>();

    public void addAttrDef(AttrDefinition def) {this.s.add(def);}

    public Set<AttrDefinition> getAttrDef(){return this.s;}

    public AttrDefinition getAttrDef(String name) {
        for (AttrDefinition def : s) if (def.getName().equals(name)) return def;
        return null;
    }

    public Boolean hasAttrDef(AttrDefinition def) {
        for (AttrDefinition d : s) if (d.equals(def)) return true;
        return false;
    }

    public void removeAttrDef(AttrDefinition def) {def.setImportance(0);}

    public String printAttrDefList() {
        String ret = "Attribute definition list\n";
        for (AttrDefinition def : s) {
            ret += def.toString();
        }
        return ret;
    }

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
               + this.getNodes()+
               "Relationship strenght\n"
               +this.getEdges();
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
