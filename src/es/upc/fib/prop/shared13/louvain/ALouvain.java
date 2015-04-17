package es.upc.fib.prop.shared13.louvain;

/**
 * Created by miquel on 30/03/15.
 */

import es.upc.fib.prop.shared13.*;
import es.upc.fib.prop.usParlament.domain.TEdge;
import es.upc.fib.prop.usParlament.domain.TNode;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Vector;

public class ALouvain
{
    private LouvainGraph og;
    private Vector<LouvainGraph> vg; //It stores the "new" graphs formed in every step of the algorithm
    private LinkedHashMap<LouvainGraph,Set<Community>> comMap;//Get communities from any specified graph
    private LinkedHashMap<Node,Community> node2com;//Get the community a node is in
    private int levels;


    /*
    temporals
    */

    public void setOg(LouvainGraph og)
    {
        this.og = og;
    }

    public void setNode2com(LinkedHashMap<Node, Community> node2com)
    {
        this.node2com = node2com;
    }

    public void setComMap(LinkedHashMap<LouvainGraph, Set<Community>> comMap)
    {
        this.comMap = comMap;
    }
    /*
     */




    public ALouvain()
    {
    }



    public double calculateModularity(LouvainGraph g)
    {
        Set<Edge> alreadyDone = new LinkedHashSet<Edge>();
        double m = g.getTotalWeight();
        System.out.println("Weight: "+m);
        double retorn = 0;
        for(Node i:og.getNodeSet()){
            for(Node j:og.getNodeSet()){
                if((node2com.get(i) == node2com.get(j)) && (og.edgeExists(i,j))
                        && !(alreadyDone.contains(og.getEdge(i,j)))){//If they are on the same community
                    alreadyDone.add(og.getEdge(i,j));
                    double temp = og.getEdge(i,j).getWeight();
                    double temp2 = (og.getNeighborsWeight(i)*og.getNeighborsWeight(j))/(2*m);
                    temp -=temp2;
                    retorn+=temp;
                    System.out.println("("+i+","+j+"): "+temp);
                    //System.out.println("retorn: "+retorn);
                }
            }
        }
        return retorn/(2*m);
    }

    //returns the modularity Delta if we put the node n of the graph og to c
    //c has to be one of the graph communities
    public double modularityDelta(LouvainGraph og,Node n,Community c)
    {
        //It doesnt have a community Community oldCom = node2com.get(n);

        node2com.put(n,c);
        c.addNode(n);

        double m = og.getTotalWeight();
        double sumin = c.getInsideWeight();
        double sumtot = 0;
        for(Node i:og.getNodeSet()){
            sumtot+=og.getNeighborsWeight(i);
        }
        double kiin = c.getNodeInsideWeight(n);
        double ki = og.getNeighborsWeight(n);

        double retorn = (sumin+kiin)/(2*m);
        double temp1 = (sumtot+ki)/(2*m);
        temp1*=temp1;
        retorn -= temp1;

        temp1 = sumin/(2*m);
        double temp2 = sumtot/(2*m);
        temp2 *=temp2;
        double temp3 = ki/(2*m);
        temp3*=temp3;
        retorn -= (temp1-temp2-temp3);

        return retorn;
    }

    public static void main(String[] args)
    {//For testing before implementing everything


        LouvainGraph og = new LouvainGraph();
        Vector<TNode> vn = new Vector<TNode>();
        for(int i = 0;i<6;i++) {
            vn.add(new TNode(i));
            og.addNode(vn.elementAt(i));
        }


        og.addEdge(new TEdge(vn.elementAt(0),vn.elementAt(1),10));
        og.addEdge(new TEdge(vn.elementAt(1),vn.elementAt(2),12));
        og.addEdge(new TEdge(vn.elementAt(0),vn.elementAt(2),9));


        og.addEdge(new TEdge(vn.elementAt(4),vn.elementAt(5),10));


        og.addEdge(new TEdge(vn.elementAt(2),vn.elementAt(3),50));
        og.addEdge(new TEdge(vn.elementAt(0),vn.elementAt(5),60));

        og.addEdge(new TEdge(vn.elementAt(3),vn.elementAt(5),70));

        //community of 0 1 2
        //community of 3
        //community of 4 5
        Community com1 = new Community(og);
        Community com2 = new Community(og);
        Community com3 = new Community(og);

        com1.addNode(vn.elementAt(0));
        com1.addNode(vn.elementAt(1));
        com1.addNode(vn.elementAt(2));

        com2.addNode(vn.elementAt(3));

        com3.addNode(vn.elementAt(4));
        com3.addNode(vn.elementAt(5));

        LinkedHashMap<LouvainGraph,Set<Community>> comMapL = new LinkedHashMap<LouvainGraph, Set<Community>>();
        LinkedHashMap<Node,Community> node2comL = new LinkedHashMap<Node, Community>();

        Set<Community> setcom = new LinkedHashSet<Community>();

        setcom.add(com1);
        setcom.add(com2);
        setcom.add(com3);

        comMapL.put(og,setcom);


        node2comL.put(vn.elementAt(0),com1);
        node2comL.put(vn.elementAt(1),com1);
        node2comL.put(vn.elementAt(2),com1);

        node2comL.put(vn.elementAt(3),com2);

        node2comL.put(vn.elementAt(4),com3);
        node2comL.put(vn.elementAt(5),com3);

        ALouvain a = new ALouvain();

        a.setOg(og);
        a.setComMap(comMapL);
        a.setNode2com(node2comL);

       System.out.println(a.calculateModularity(og));

        node2comL.remove(vn.elementAt(3));
        a.setNode2com(node2comL);

        System.out.println(a.modularityDelta(og,vn.elementAt(3),com1));
        System.out.println(a.calculateModularity(og));


    }


}
