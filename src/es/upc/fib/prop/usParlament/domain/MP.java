package es.upc.fib.prop.usParlament.domain;

import es.upc.fib.prop.shared13.Node;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by miquel on 7/04/15.
 */
public class MP extends Node {
	private Long id;
    private String fullname;
    private int district;
    private State state;
	//TODO redundant information. AttrDefinition is also in Attribute.
	private Map<AttrDefinition, Attribute> attributes;

    public MP(String fullname,State state,int district)
    {
        this.fullname = fullname;
        this.district = district;
        this.state = state;
	    this.attributes = new HashMap<>();
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public void setId(Integer id) {
		setId((long) id);
	}

	public String getFullname()
    {
        return fullname;
    }

    public void setFullname(String fullname)
    {
        this.fullname = fullname;
    }

    public int getDistrict()
    {
        return district;
    }

    public void setDistrict(int district)
    {
        this.district = district;
    }

    public State getState()
    {
        return state;
    }

    public void setState(State state)
    {
        this.state = state;
    }

	public Set<Attribute> getAttributes() {
		return (Set<Attribute>) attributes.values();
	}

	public void addAttribute(Attribute attr) {
		attributes.put(attr.getDefinition(), attr);
	}

	public void removeAttribute(AttrDefinition def) {
		attributes.remove(def);
	}
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

	// TODO
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
