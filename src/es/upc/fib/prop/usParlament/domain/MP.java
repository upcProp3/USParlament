package es.upc.fib.prop.usParlament.domain;

import es.upc.fib.prop.shared13.Node;

/**
 * Created by miquel on 7/04/15.
 */
public class MP extends Node
{
	private Long id;
    private String fullname;
    private int district;
    private State state;

    public MP(String fullname, State state, int district)
    {
	    this.id = null;
        this.fullname = fullname;
        this.district = district;
        this.state = state;
    }

	public Long getId()
	{
		return id;
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
    public String toString()
    {
        return "Fullname: "+this.fullname+"\nState: "+this.state+"\nDistrict: "+this.district+"\n";
    }
}
