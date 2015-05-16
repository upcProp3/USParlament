package es.upc.fib.prop.usParlament.domain;

import es.upc.fib.prop.shared13.Graph;
import es.upc.fib.prop.shared13.Node;

import java.util.ArrayList;
import java.util.Comparator;
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
            retorn.add(al);



        }
        return retorn;
    }

    //Returns an ordered set of arraylists containing the needed info
    //The first position of the arraylist contains the state of the mp
    //The second position of the arraylist contains the district
    //The third contains the name
    public Set<ArrayList<String>> getCurrentMPsShort()
    {
        Set<ArrayList<String>> retorn = new TreeSet<>();
        for(MP mp:currentCongress.getMPs()){
            ArrayList<String> al = new ArrayList<>();
            al.add(mp.getState().toString());
            al.add(Integer.toString(mp.getDistrict()));
            al.add(mp.getFullname());
            retorn.add(al);
        }
        return retorn;
    }
}
