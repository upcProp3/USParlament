package es.upc.fib.prop.usParlament.domain;

import es.upc.fib.prop.shared13.Node;

/**
 * Created by miquel on 7/04/15.
 */
public class MP extends Node
{
    private String fullname;
    private int district;
    private String state;

    public MP(String fullname,String state,int district)
    {
        this.fullname = fullname;
        this.district = district;
        this.state = state;
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

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    @Override
    public String toString()
    {
        return "Fullname: "+this.fullname+"\nState: "+this.state+"\nDistrict: "+this.district+"\n";
    }
}
