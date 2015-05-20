package es.upc.fib.prop.usParlament.domain;

import es.upc.fib.prop.shared13.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Miquel Jubert on 7/04/15.
 * Contributions of Alex Miro and Ondrej Velisek.
 */
public class MP extends Node {
    private String fullname;
    private int district;
    private State state;
	private Map<AttrDefinition, Attribute> attributes;


    public Attribute getAttribute(AttrDefinition ad) { return attributes.get(ad);}


	/**
	 * An MP is created with name 'fullname', state 'state' and district 'district'.
	 * @param fullname String representing the full name of the generated MP.
	 * @param state State representing the state of the generated MP.
	 * @param district Integer representing the district number of the generated MP.
	 */
    public MP(String fullname,State state,int district)
    {
        this.fullname = fullname;
        this.district = district;
        this.state = state;
	    this.attributes = new HashMap<>();
    }

	/**
	 * @return The implicit MP full name.
	 */
	public String getFullname()
    {
        return fullname;
    }

	/**
	 * The full name of the implicit MP is set to 'fullname'.
	 * @param fullname String representing the full name of the implicit MP to be set.
	 */
    public void setFullname(String fullname)
    {
        this.fullname = fullname;
    }

	/**
	 * @return The implicit MP district number.
	 */
    public int getDistrict()
    {
        return district;
    }

	/**
	 * The district number of the implicit MP is set to 'district'.
	 * @param district Integer representing the district number of the implicit MP to be set.
	 */
    public void setDistrict(int district)
    {
        this.district = district;
    }

	/**
	 * @return The implicit MP State.
	 */
    public State getState()
    {
        return state;
    }

	/**
	 * The State of the implicit MP is set to 'state'.
	 * @param state State representing the state of the implicit MP.
	 */
    public void setState(State state)
    {
        this.state = state;
    }

	/**
	 * @return The implicit MP collection of attributes.

	 */
	public List<Attribute> getAttributes() {
		return new ArrayList<Attribute>(attributes.values());
	}

	/**
	 * 'attr' is added to the implicit MP collection of attributes.
	 * @param attr Attribute representing the Attribute of the implicit MP to be added.
	 */
	public void addAttribute(Attribute attr) {
		attributes.put(attr.getDefinition(), attr);
	}

	/**
	 * @param attr Attribute representing the Attribute of the implicit MP.
	 * @return True iff the implicit MP has a defined value for the attribute 'attr'; false otherwise.
	 */
    public boolean hasAttribute(AttrDefinition attr)
    {
        return attributes.containsKey(attr);
    }

	/**
	 * It removes the value of 'def' for the implicit MP.
	 * @param def AttrDefinition representing the Attribute of the implicit MP to be removed.
	 */
	public void removeAttribute(AttrDefinition def) {
		attributes.remove(def);
	}

	/**
	 * It removes the value of 'attr' for the implicit MP.
	 * @param attr Attribute representing the Attribute of the implicit MP to be removed.
	 */
	public void removeAttribute(Attribute attr) {
		removeAttribute(attr.getDefinition());
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof MP)) {
			return false;
		}
		if (this == o) {
			return true;
		}
		MP mp = (MP) o;
		return (mp.getState() == this.state)&&(mp.getDistrict() == this.district);
	}
	
	@Override
	public int hashCode() {
        int hash = district+state.hashCode();
		return hash;
	}


	@Override
    public String toString()
    {
	    String r = "\nFullname: "+this.fullname+"\nState: "+this.state+"\nDistrict: "+this.district;
	    r += "\nattributes: [ ";
	    for (Attribute attr : attributes.values()) {
		    r += attr + ", ";
	    }
	    r += "]\n";
        return r;
    }

	@Override
	public int compareTo(Node n) {
		if (this.equals(n)) {
			return 0;
		}
		if (!(n instanceof MP)) {
			throw new ClassCastException("compareTo method is defined only for MP class.");
		}
		MP mp = (MP) n;
		return this.fullname.compareTo(mp.fullname);
	}
}
