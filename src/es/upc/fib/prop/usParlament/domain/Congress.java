package es.upc.fib.prop.usParlament.domain;

import es.upc.fib.prop.shared13.Graph;
import es.upc.fib.prop.shared13.Node;

import java.util.*;


/**
 * Created by Ondrej Velisek on 15.4.15.
 * Contributions Aleix Sacrest and Alex Miro.
 */
public class Congress extends Graph {


    class AttrDefComparatorString implements Comparator<AttrDefinition>
    {
        public int compare(AttrDefinition a1,AttrDefinition a2)
        {
            return a1.getName().compareTo(a2.getName());
        }
    }

    private Set<AttrDefinition> s = new TreeSet<>(new AttrDefComparatorString());

    /**
     * A new AttrDefinition is added to the Congress set of AttrDefinitions.
     * @param def AttrDefinition that will be added to the Congress set of AttrDefinitions.
     */
    public void addAttrDef(AttrDefinition def) {this.s.add(def);}

    /**
     * @return The implicit Congress set of AttrDefinitions.
     */
    public Set<AttrDefinition> getAttrDef(){return this.s;}

    /**
     * @param name AttrDefinition's name that will be returned.
     * @return AttrDefinition with name 'name', which should belong to the Congress set of AttrDefinitions.
     * @return If the specified AttrDefinition doesn't belong to it, the returned AttrDefinition will be null.
     */
    public AttrDefinition getAttrDef(String name) {
        for (AttrDefinition def : s) if (def.getName().equals(name)) return def;
        return null;
    }

    /**
     * @param def AttrDefinition that will be checked.
     * @return True iff the implicit Congress set of AttrDefinitions contains 'def'; false otherwise.
     */
    public Boolean hasAttrDef(AttrDefinition def) {
        for (AttrDefinition d : s) if (d.equals(def)) return true;
        return false;
    }

    /**
     * The AttrDefinition 'def' is removed from the implicit Congress set of AttrDefinitions.
     * @param def AttrDefinition that will be removed.
     */
    public void removeAttrDef(AttrDefinition def) {def.setImportance(0);}

    /**
     * @return String form of the implicit Congress set of AttrDefinitions.
     */
    public String printAttrDefList() {
        String ret = "Attribute definition list\n";
        for (AttrDefinition def : s) {
            ret += def.toString();
        }
        return ret;
    }

    /**
     * @return The implicit Congress collection of MPs.
     */
	public Collection<MP> getMPs() {
		Collection<MP> mps = new HashSet<>();
		for (Node n : getNodes()) {
			mps.add((MP) n);
		}
		return mps;
	}

    /**
     *
     * @return The string form of a Congress: the string of each MP and the list of relationship strength between them.
     */
    public String toString()
    {
       return "US Congressman list:\n"
               + this.getNodes()+
               "Relationship strenght\n"
               +this.getEdges();
    }

    /**
     * @param s State of the MP that will be returned.
     * @param d District number of the MP that will be returned.
     * @return The MP with State 's' and district 'd', which should belong to the implicit Congress.
     * @return If the specified MP doesn't belong to it, the returned MP will be null.
     */
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
