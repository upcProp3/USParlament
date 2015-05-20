package es.upc.fib.prop.usParlament.domain;

import es.upc.fib.prop.shared13.Node;
import es.upc.fib.prop.usParlament.misc.*;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;


/**
 * Created by miquel on 16/05/15.
 */
public class DomainController
{
    /*
        Needs to contain:
        - 1 Graph (congress)
        - 2 Partition (one current and one for comparing purposes)
        Needs to give the ability to consult the graphs

     */
    private Congress currentCongress;
    private ArrayList<Set<Node>> mainPartition;
    private ArrayList<Set<Node>> secondaryPartition;


    //All the consulting functions go here
    //TODO:TEST FUNC
    //Returns an ordered set of arraylists containing the needed info
    //The first position of the arraylist contains the state of the mp
    //The second position of the arraylist contains the district
    //The third contains the name
    public Set<ArrayList<String>> getCurrentMPsLong()
    {
        Set<ArrayList<String>> retorn = new TreeSet<>();
        for(MP mp:currentCongress.getMPs()){
            ArrayList<String> al = new ArrayList<>();
            al.add(mp.getState().toString());
            al.add(Integer.toString(mp.getDistrict()));
            al.add(mp.getFullname());

            for(AttrDefinition ad:currentCongress.getAttrDef()){
                Attribute at = mp.getAttribute(ad);
                if(at != null) al.add(at.getValue().toString());
                else al.add("NO_VALUE");
            }
            retorn.add(al);
        }
        return retorn;
    }


    /**
     * @return It returns an array with the State and District values for each MP at the current congress.
     */
    public JSONArray getShortMPList() {
        JSONObject jList = new JSONObject();
        for (MP mp : currentCongress.getMPs()) {
            JSONObject jMP = new JSONObject();
            JSONString key = new JSONString("State");
            JSONString value = new JSONString(mp.getState().toString());
            jMP.addPair(key, value);
            key.setValue("District");
            value.setValue(String.valueOf(mp.getDistrict()));
            jMP.addPair(key, value);
            key.setValue("List");
            value.setValue(jMP.toString());
            jList.addPair(key, value);
        }
        JSONArray ret = new JSONArray();
        ret.addElement(jList);
        return ret;
    }
}
