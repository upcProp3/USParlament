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

        System.out.println(alg.Modularity(particio));
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

    public double Modularity(Partition<Node,Integer> c)
    {
        Double pesT = lg.getPes();
        LinkedHashMap<Integer,Double> inc = new LinkedHashMap<>();
        LinkedHashMap<Integer,Double> deg = new LinkedHashMap<>();
        for(Integer i:c.values()){
            inc.put(i,0.);
            deg.put(i,0.);
        }


        for(Node n:lg.getNodes()){
            Integer com = (Integer)c.get(n);
            deg.put(com,deg.get(com)+lg.getWDegree(n));
            for(Edge e:lg.getAdjacencyList(n)){
                Double pes = e.getWeight();
                Node neighbor = e.getNeighbor(n);
                if(com == c.get(neighbor)){
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


}
