package es.upc.fib.prop.usParlament.domain;

import es.upc.fib.prop.shared13.Node;

import java.util.*;

/**
 * Created by miquel on 7/04/15.
 */

public class MP extends Node
{
    private String fullname;
    private int district;
    private String state;
    private Map<String, Object> atts = new LinkedHashMap<>();

    /**
     * @pre True
     * @post An MP is created
     * @param fullname
     * @param state
     * @param district
     * An MP is created
     */
    public MP(String fullname,String state,int district)
    {
        this.fullname = fullname;
        this.district = district;
        this.state = state;
    }

    /**
     * @pre True
     * @post Returns the full name of the implicit MP
     * @return
     * Returns the full name of the implicit MP*/
    public String getFullname()
    {
        return fullname;
    }

    /**
     * @pre True
     * @post fullname is assigned to the implicit MP
     * @param fullname
     * fullname is assigned to the implicit MP
     */
    public void setFullname(String fullname)
    {
        this.fullname = fullname;
    }

    /**
     * @pre True
     * @post Returns the district of the implicit MP
     * @return
     * Returns the district of the implicit MP
     */
    public int getDistrict()
    {
        return district;
    }

    /**
     * @pre True
     * @post district is assigned to the implicit MP
     * @param district
     * district is assigned to the implicit MP
     */
    public void setDistrict(int district)
    {
        this.district = district;
    }

    /**
     * @pre True
     * @post A new attribute of name 'name' and value 'value'
     * @param name
     * @param value
     * A new attribute of name 'name' and value 'value'
     */
    public void addAttribute(String name, Object value){
        atts.put(name, value);
    }

    /**
     * @pre v is the name of an attibute of the implicit MP
     * @post Returns the value of the 'v' attribute
     * @param v
     * @return
     * Returns the value of the 'v' attribute
     */
    public Object getValue(String v) {
        return atts.get(v);
    }

    /**
     * @pre True
     * @post Returns a set with the names of the attributes of the implicit MP
     * @return
     * Returns a set with the names of the attributes of the implicit MP
     */
    public Set<String> getAttributeList() {
        return atts.keySet();
    }

    /**
     * @pre True
     * @post Returns the state of the implicit MP
     * @return
     * Returns the state of the implicit MP
     */
    public String getState()
    {
        return state;
    }

    /**
     * @pre True
     * @post state is assigned to the implicit MP
     * @param state
     * state is assigned to the implicit MP
     */
    public void setState(String state)
    {
        this.state = state;
    }

    /**
     * @pre mp is an existing MP
     * @post Returns a set with the common attributes between the implicit MP and mp
     * @param mp
     * @return
     * Returns a set with the common attributes between the implicit MP and mp
     */
    public Set<String> getCommonAttributes(MP mp) {
        Set<String> s = atts.keySet();
        Set<String> s2 = mp.getAttributeList();
        Set<String> ret = new HashSet<>();
        for (String i : s) {
            if (s2.contains(i)) ret.add(i);
        }
        return ret;
    }
    @Override
    public String toString() {
        return "Fullname: " + this.fullname + "\nState: " + this.state + "\nDistrict: " + this.district + "\n";
    }
}
