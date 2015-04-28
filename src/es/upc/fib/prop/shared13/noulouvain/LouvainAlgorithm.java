package es.upc.fib.prop.shared13.noulouvain;

import es.upc.fib.prop.shared13.Edge;
import es.upc.fib.prop.shared13.Graph;
import es.upc.fib.prop.shared13.Node;
import es.upc.fib.prop.usParlament.driver.TEdge;
import es.upc.fib.prop.usParlament.driver.TNode;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by miquel on 28/04/15.
 */
public class LouvainAlgorithm
{
    Graph original;



    public static void main(String[] args)
    {
        Graph L = new LGraph();
        Node[] nodes = new Node[6];
        for (int i = 0; i < 6; i++) {
            nodes[i] = new TNode(i + 1);
            L.addNode(nodes[i]);
        }

        L.addEdge(new TEdge(nodes[0], nodes[1], 20));
        L.addEdge(new TEdge(nodes[1], nodes[1], 10));
        L.addEdge(new TEdge(nodes[0], nodes[2], 13));
        L.addEdge(new TEdge(nodes[1], nodes[2], 12));

        L.addEdge(new TEdge(nodes[2], nodes[2], 4));

        L.addEdge(new TEdge(nodes[2], nodes[3], 10));//Era 10
        L.addEdge(new TEdge(nodes[3], nodes[4], 16));
        L.addEdge(new TEdge(nodes[3], nodes[5], 15));
        L.addEdge(new TEdge(nodes[4], nodes[5], 18));
        L.addEdge(new TEdge(nodes[5], nodes[5], 8));
        System.out.println(L);
        LouvainAlgorithm l = new LouvainAlgorithm();
        l.testEstat(L);
    }

    public void testEstat(Graph g)
    {
        Estat e = new Estat(g);
    }

    private class Estat
    {
        //Creates the status and initializes it with the data of the graph g
        //And the partition where each node is its own communtiy
        Map<Node,Integer> partition;
        Graph g;
        Map<Integer,Double> inc;//Sum of the weights of the edges inside a community
        Map<Integer,Double> dec;//Sum of the degrees of the nodes inside a community
        Map<Node,Double> ndegree;//Maps each node to its degree (loop increase the degree by 2 times their value)
        Double m;//Graph total weight




        public Estat(Graph ag)
        {
            g = ag;
            m = 0.;
            partition = new LinkedHashMap<>();
            dec = new LinkedHashMap<>();
            inc = new LinkedHashMap<>();
            ndegree = new LinkedHashMap<>();

            //Initialize degrees and weight
            for(Edge e:g.getEdges()){
                m+=e.getWeight();
                Node n1,n2;
                n1 = e.getNode();
                n2 = e.getNeighbor(e.getNode());

                if(!ndegree.containsKey(n1)) ndegree.put(n1,0.);
                if(!ndegree.containsKey(n2)) ndegree.put(n2,0.);

                ndegree.put(n1,ndegree.get(n1)+e.getWeight());
                ndegree.put(n2,ndegree.get(n2)+e.getWeight());
            }
            int npart = 0;
            //Initialize Node degrees
            for(Node n:g.getNodes()){
                partition.put(n,npart);//Create partitions
                dec.put(npart,ndegree.get(n));//Initialize partitions
                //If the node has a loop
                if(g.hasEdge(n,n)){
                    inc.put(npart,g.getEdge(n,n).getWeight());
                }else{
                    inc.put(npart,0.);
                }
                npart++;
            }
            //TODO:delete, for testing
            System.out.println("m:"+m);
            System.out.println("Partition:"+partition);
            System.out.println("NDegree:"+ndegree);
            System.out.println("Inc:"+inc);
            System.out.println("Dec:"+dec);

        }
    }



}
