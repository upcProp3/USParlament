package es.upc.fib.prop.shared13.louvain;

import es.upc.fib.prop.shared13.Edge;
import es.upc.fib.prop.shared13.Graph;
import es.upc.fib.prop.shared13.Node;
import es.upc.fib.prop.usParlament.driver.TEdge;
import es.upc.fib.prop.usParlament.driver.TNode;

import java.util.*;

public class LouvainAlgorithm
{
    Graph original;
    Map<Node,Set<Node>> com2sons;
    ArrayList<Set<Node>> bestPartition;


    public LouvainAlgorithm(Graph g)
    {
        original = g;
        com2sons = new LinkedHashMap<>();
        bestPartition = null;
    }

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


        Map<Node,Integer> particio = new LinkedHashMap<>();

        particio.put(nodes[0],0);
        particio.put(nodes[1],0);
        particio.put(nodes[2],1);
        particio.put(nodes[3],2);
        particio.put(nodes[4],2);
        particio.put(nodes[5],2);

        Graph go = new LGraph();
        go.addNode(nodes[0]);
        go.addNode(nodes[1]);
        go.addEdge(new LEdge(nodes[0],nodes[1],300));
        go.addEdge(new LEdge(nodes[0],nodes[0],100));
        go.addEdge(new LEdge(nodes[1],nodes[1],200));

        System.out.println(go);
        LouvainAlgorithm l = new LouvainAlgorithm(go);
        l.calculate();
    }

    //Calculates the modularity of a partition of the graph g
    static public Double calculateModularity(Graph g,Map<Node,Integer> part)
    {
        Map<Node,Double> ndegree = new LinkedHashMap<>();
        Map<Integer,Double> inc = new LinkedHashMap<>();
        Map<Integer,Double> dec = new LinkedHashMap<>();
        Double m;

        m = 0.;

        for(Integer i:part.values()){
            inc.put(i,0.);
            dec.put(i,0.);
        }

        //Initialize degrees and weight
        for(Edge e:g.getEdges()){
            m+=e.getWeight();
            Node n1,n2;
            n2 = e.getNode();
            n1 = e.getNeighbor(e.getNode());

            if(!ndegree.containsKey(n1)) ndegree.put(n1,0.);
            if(!ndegree.containsKey(n2)) ndegree.put(n2,0.);

            ndegree.put(n1,ndegree.get(n1)+e.getWeight());
            ndegree.put(n2,ndegree.get(n2)+e.getWeight());

            Integer com1,com2;
            com1 = part.get(n1);
            com2 = part.get(n2);
            if(com1.equals(com2)){
                inc.put(com1,inc.get(com1)+e.getWeight());
            }
        }

        for(Node n:g.getNodes()){
            Integer com = part.get(n);
            dec.put(com,dec.get(com)+ndegree.get(n));
        }

        double mod = 0.;
        LinkedHashSet<Integer> s = new LinkedHashSet<>();
        s.addAll(part.values());
        for(Integer i:s){
            mod+= (inc.get(i)/m)-(java.lang.Math.pow(dec.get(i)/(2*m),2));
        }
        return mod;
    }

    public ArrayList<Set<Node>> calculate()
    {
        if(bestPartition != null) return bestPartition;
        Graph current = original;
        Estat currentState;
        boolean gain = true;
        while(gain) {//While there is gain
            gain = false;
            currentState = new Estat(current);//Initialization
            boolean localgain = true;
            //Start of first phase, get best partition
            while(localgain) {//while there is local gain
                localgain = false;
                double bMod = currentState.modularity();
                for (Node n : current.getNodes()) {
                    Integer com = currentState.getCommunity(n);
                    for (Node neigh : currentState.getNeighborsDiffCommuntity(n)) {
                        currentState.changeCommunity(n, currentState.getCommunity(neigh));
                        double mod = currentState.modularity();
                        if (mod > bMod) {//New best partition
                            localgain = true;
                            gain = true;
                            bMod = mod;
                            com = currentState.getCommunity(neigh);

                        } else {
                            currentState.changeCommunity(n, com);
                        }
                    }
                }
            }
            //Start of second phase, create new graph
            //if(gain){//If there was a gain
                current = calculateNewGraph(current,currentState);
            //}
        }
        bestPartition = new ArrayList<>();
        for(Node n:current.getNodes()){
            bestPartition.add(getComNodes(n));
        }
        return bestPartition;
    }

    private Set<Node> getComNodes(Node n)
    {
        Set<Node> retorn = new LinkedHashSet<>();
        for(Node s:com2sons.get(n)){
            if(!com2sons.containsKey(s)){
                retorn.add(s);
            }else {
                retorn.addAll(getComNodes(s));
            }
        }
        return retorn;
    }

    private Graph calculateNewGraph(Graph g,Estat state)
    {
        Graph nou = new LGraph();
        Map<Integer,CNode> lnk = new LinkedHashMap<>();

        //Create link between integers and communities
        for(Integer i:state.getCommunities()){
            lnk.put(i,new CNode());
        }
        //Add all the new nodes to the graph
        for(Node n:lnk.values()){
            nou.addNode(n);
        }
        //Make com2sons calculations
        for(Node n:g.getNodes()){
            Node father = lnk.get(state.getCommunity(n));
            if(!com2sons.containsKey(father)) com2sons.put(father,new LinkedHashSet<Node>());
            com2sons.get(father).add(n);
        }
        //End of com2sons calculations

        for(Edge e:g.getEdges()){
            CNode cn1,cn2;
            //We get the Communtiy node that corresponds to each of the nodes
            cn1 = lnk.get(state.getCommunity(e.getNeighbor(e.getNode())));
            cn2 = lnk.get(state.getCommunity(e.getNode()));

            Double pes = e.getWeight();
            if(!nou.hasEdge(cn1, cn2)){
                nou.addEdge(new LEdge(cn1, cn2, pes));
            }else{
                Edge ee = nou.getEdge(cn1,cn2);
                ee.setWeight(ee.getWeight() + e.getWeight());
            }
        }
        return nou;
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
                n2 = e.getNode();
                n1 = e.getNeighbor(e.getNode());

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

        }

        public Set<Integer> getCommunities()
        {
            Set<Integer> s = new LinkedHashSet<>();
            s.addAll(partition.values());
            return s;
        }

        public String toString()
        {
            return "m:"+m
            +"\nPartition:"+partition
            +"\nNDegree:"+ndegree
            +"\nInc:"+inc
            +"\nDec:"+dec
            +"\nModularity:" +this.modularity();
        }

        public Integer getCommunity(Node n)
        {
            return partition.get(n);
        }

        public void changeCommunity(Node n,Integer newcom)
        {//n can not be inside i communtiy
            Integer com = partition.get(n);
            for(Edge e:g.getAdjacencyList(n)){//For all the edges of n
                Node m = e.getNeighbor(n);
                Integer neighbcom = partition.get(m);
                //Removal operations
                if(com.equals(neighbcom)){
                    inc.put(com, inc.get(com) - e.getWeight());
                }
                //End of removal operations
                //Addition operations
                if(newcom.equals(neighbcom) || n.equals(m)){
                    inc.put(newcom,inc.get(newcom)+e.getWeight());
                }
            }
            //degree removal
            dec.put(com, dec.get(com) - ndegree.get(n));
            //degree addition
            dec.put(newcom,dec.get(newcom)+ndegree.get(n));
            //Change communtiy
            partition.put(n,newcom);
        }

        public Set<Node> getNeighborsDiffCommuntity(Node n)
        {
            Set<Node> ret = new LinkedHashSet<>();
            for(Edge e:g.getAdjacencyList(n)){
                Node m = e.getNeighbor(n);
                if(!partition.get(n).equals(partition.get(m))) ret.add(m);
            }
            return ret;
        }

        public Double modularity()
        {
            double mod = 0.;
            LinkedHashSet<Integer> s = new LinkedHashSet<>();
            s.addAll(partition.values());
            for(Integer i:s){
                mod+= (inc.get(i)/m)-(java.lang.Math.pow(dec.get(i)/(2*m),2));
            }
            return mod;
        }
    }
}
