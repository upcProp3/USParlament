package es.upc.fib.prop.shared13.louvain;

import es.upc.fib.prop.shared13.*;
import es.upc.fib.prop.usParlament.driver.TEdge;
import es.upc.fib.prop.usParlament.driver.TNode;
import sun.awt.image.ImageWatched;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by miquel on 25/04/15.
 */
public class LouvainAlgorithm extends CommunityAlgorithm
{


    public static void main(String[] args)
    {
        LouvainGraph L = new LouvainGraph();
        Node[] nodes = new Node[6];
        for(int i = 0;i<6;i++){
            nodes[i] = new TNode(i+1);
            L.addNode(nodes[i]);
        }

        L.addEdge(new TEdge(nodes[0],nodes[1],20));
        L.addEdge(new TEdge(nodes[1],nodes[1],10));
        L.addEdge(new TEdge(nodes[0],nodes[2],13));
        L.addEdge(new TEdge(nodes[1],nodes[2],12));

        L.addEdge(new TEdge(nodes[2],nodes[2],4));

        L.addEdge(new TEdge(nodes[2],nodes[3],10));
        L.addEdge(new TEdge(nodes[3],nodes[4],16));
        L.addEdge(new TEdge(nodes[3],nodes[5],15));
        L.addEdge(new TEdge(nodes[4],nodes[5],18));
        L.addEdge(new TEdge(nodes[5],nodes[5],8));
        System.out.println(L);
        LouvainAlgorithm alg = new LouvainAlgorithm(L);

        Partition<Node,Integer> particio = new Partition<>();

        particio.put(nodes[0],0);
        particio.put(nodes[1],0);
        particio.put(nodes[2],0);
        particio.put(nodes[3],2);
        particio.put(nodes[4],2);
        particio.put(nodes[5],2);

        System.out.println(alg.Modularity(particio,L));

        System.out.println("PROVA FUNCIO nou Graph");
        System.out.println("PARTICIO: "+particio);
        LouvainGraph nou = alg.nouGraph(particio,L);
        System.out.println(nou);

        Partition<Node,Integer> particio2 = new Partition<>();
        int i = 0;
        for(Node n:nou.getNodes()){
            particio2.put(n,i++);
        }
        System.out.println(particio2);
        System.out.println(alg.Modularity(particio2,nou));
    }

    private LouvainGraph lg;

    private class Status
    {

    }

    public CommunitySet compute(Graph g){return null;};

    public LouvainAlgorithm(LouvainGraph g)
    {
        lg = g;
    }

    public double Modularity(Partition<Node,Integer> c,LouvainGraph lg1)
    {
        Double pesT = lg1.getPes();
        LinkedHashMap<Integer,Double> inc = new LinkedHashMap<>();
        LinkedHashMap<Integer,Double> deg = new LinkedHashMap<>();
        for(Integer i:c.values()){
            inc.put(i,0.);
            deg.put(i,0.);
        }


        for(Node n:lg1.getNodes()){
            Integer com = (Integer)c.get(n);
            deg.put(com,deg.get(com)+lg1.getWDegree(n));
            for(Edge e:lg1.getAdjacencyList(n)){
                Double pes = e.getWeight();
                Node neighbor = e.getNeighbor(n);
                if(com.equals(c.get(neighbor))){
                    if(n.equals(neighbor)) {
                        inc.put(com, inc.get(com) + pes);
                    }else{
                        inc.put(com, inc.get(com) + pes/2);
                    }
                }
            }
        }
        double mod = 0.;
        for(Integer com:inc.keySet()){
            mod+=(inc.get(com)/pesT - Math.pow(deg.get(com)/(2*pesT),2.));
        }
        return mod;
    }

    private LouvainGraph nouGraph(Partition<Node,Integer> c,LouvainGraph lgraph)
    {//TODO:the recaculate weight is an ugly af solution
        LouvainGraph nou = new LouvainGraph();
        LinkedHashMap<Integer,LCommunity> lnk = new LinkedHashMap<>();//pas de numeros a comuntiats reals
        for(Integer i:c.values()){
            lnk.put(i, new LCommunity());
        }//Nou graf amb els nodes, cal calular les arestes

        for(Node n:lnk.values()){
            nou.addNode(n);
        }

        for(Edge e:lgraph.getEdges()){
            LCommunity com1,com2;
            com1 = lnk.get(c.get(e.getNode()));
            com2 = lnk.get(c.get(e.getNeighbor(e.getNode())));
            Double pes = e.getWeight();
            if(!nou.hasEdge(com1,com2)){
                nou.addEdge(new TEdge(com1,com2,pes));//TODO: change TEDGE for smthn else
            }else{
                Edge ee = nou.getEdge(com1,com2);
                ee.setWeight(ee.getWeight()+e.getWeight());
            }

        }
        nou.recalculate();
        return nou;

    }


}
