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

//TODO: translate all comments to english ( too hard to think directly in english :/)
public class ALouvain
{
    private LouvainGraph og;
    private Vector<LouvainGraph> vg; //Conte els grafs que es van formant a cada fase
    private LinkedHashMap<LouvainGraph,Set<Community>> comMap;//Obtenir comunitats d'un dels grafs
    private LinkedHashMap<Node,Community> node2com;//Obtenir comunitat d'un node
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
        double m = g.getTotalWeight();
        System.out.println("Weight: "+m);
        double retorn = 0;
        for(Node i:og.getNodeSet()){
            for(Node j:og.getNodeSet()){
                if((node2com.get(i) == node2com.get(j)) && (og.edgeExists(i,j))){//If they are on the same community
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


        og.addEdge(new TEdge(vn.elementAt(2),vn.elementAt(3),100));
        og.addEdge(new TEdge(vn.elementAt(0),vn.elementAt(5),200));

        og.addEdge(new TEdge(vn.elementAt(3),vn.elementAt(5),300));

        //comunitat per 0 1 2
        //comunitat per 3
        //comunitat per 4 5
        Community com1 = new Community();
        Community com2 = new Community();
        Community com3 = new Community();

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
    }


}
