package es.upc.fib.prop.usParlament.domain;

import es.upc.fib.prop.shared13.Node;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Miquel Jubert on 7/04/15.
 * Contributions of Alex Miro and Ondrej Velisek.
 */
public class MP extends Node {
	private Long id;
    private String fullname;
    private int district;
    private State state;
	private Map<AttrDefinition, Attribute> attributes;

	/**
	 * An MP is created with name 'fullname', state 'state' and district 'district'.
	 * @param fullname
	 * @param state
	 * @param district
	 */
    public MP(String fullname,State state,int district)
    {
        this.fullname = fullname;
        this.district = district;
        this.state = state;
	    this.attributes = new HashMap<>();
    }

	/**
	 * It returns the implicit MP identification number.
	 * @return
	 */
	public Long getId() {
		return id;
	}

	/**
	 * The identification number of the implicit MP is set to 'id'.
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * The identification number of the implicit MP is set to 'id'.
	 * @param id
	 */
	public void setId(Integer id) {
		setId((long) id);
	}

	/**
	 * It returns the implicit MP full name.
	 * @return
	 */
	public String getFullname()
    {
        return fullname;
    }

	/**
	 * The full name of the implicit MP is set to 'fullname'.
	 * @param fullname
	 */
    public void setFullname(String fullname)
    {
        this.fullname = fullname;
    }

	/**
	 * It returns the implicit MP district number.
	 * @return
	 */
    public int getDistrict()
    {
        return district;
    }

	/**
	 * The district number of the implicit MP is set to 'district'.
	 * @param district
	 */
    public void setDistrict(int district)
    {
        this.district = district;
    }

	/**
	 * It returns the implicit MP State.
	 * @return
	 */
    public State getState()
    {
        return state;
    }

	/**
	 * The State of the implicit MP is set to 'state'.
	 * @param state
	 */
    public void setState(State state)
    {
        this.state = state;
    }

	/**
	 * It returns the implicit MP collection of attributes.
	 * @return
	 */
	public Collection<Attribute> getAttributes() {
		return attributes.values();
	}

	/**
	 * 'attr' is added to the implicit MP collection of attributes.
	 * @param attr
	 */
	public void addAttribute(Attribute attr) {
		attributes.put(attr.getDefinition(), attr);
	}

	/**
	 * It returns true iff the implicit MP has a defined value for the attribute 'attr'; false otherwise.
	 * @param attr
	 * @return
	 */
    public boolean hasAttribute(AttrDefinition attr)
    {
        return attributes.containsKey(attr);
    }

	/**
	 * It removes the value of 'def' for the implicit MP.
	 * @param def
	 */
	public void removeAttribute(AttrDefinition def) {
		attributes.remove(def);
	}

	/**
	 * It removes the value of 'attr' for the implicit MP.
	 * @param attr
	 */
	public void removeAttribute(Attribute attr) {
		removeAttribute(attr.getDefinition());
	}

	/**
	 * It returns true iff 'o' is equal to the implicit MP (same values for State and District).
	 * @param o object to compare with this.
	 * @return
	 */
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

	/**
	 * It returns the hash code of the implicit MP.
	 * @return
	 */
	@Override
	public int hashCode() {
        int hash = district+state.hashCode();
		return hash;
	}

	/**
	 * It returns the string form of an MP: full name, State, district and list of attributes.
	 * @return
	 */
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

	/**
	 * Comparison between two MPs.
	 * @param n the node to compare.
	 * @return
	 */
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
