package es.upc.fib.prop.usParlament.domain;

import es.upc.fib.prop.shared13.Node;

/**
 * Created by miquel on 7/04/15.
 */
public class MP extends Node {
	private Long id;
    private String fullname;
    private int district;
    private State state;

    public MP(String fullname,State state,int district)
    {
        this.fullname = fullname;
        this.district = district;
        this.state = state;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof MP)) {
			return false;
		}
		if (this == o) {
			return true;
		}
		MP mp = (MP) o;
		return (mp.id == this.id);
	}

	// TODO
	@Override
	public int hashCode() {
		int hash = 53 * 5 + (this.id == null ? 0 : this.id.hashCode());
		return hash;
	}

	@Override
    public String toString()
    {
        return "Fullname: "+this.fullname+"\nState: "+this.state+"\nDistrict: "+this.district+"\n";
    }

	// TODO
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
