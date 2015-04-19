package es.upc.fib.prop.usParlament.domain;


import java.util.*;


import es.upc.fib.prop.shared13.Edge;
import es.upc.fib.prop.shared13.Node;
import es.upc.fib.prop.usParlament.domain.TNode;
import es.upc.fib.prop.usParlament.domain.TGraph;
import es.upc.fib.prop.usParlament.domain.TEdge;


/**
 * Created by aleixsacrest on 15/04/15.
 */
public class GraphDriver {

    static TGraph g = new TGraph();
    static Map<Integer, TNode> nodes = new LinkedHashMap<>();

    public static void main(String[] args) {

        System.out.println("This is a Driver for the class Graph");
        Scanner read = new Scanner(System.in);
        System.out.println("What do you want to do? Enter the key and press enter");
        Boolean seguir = true;
        while(seguir) {
            int num;
            System.out.println("1-node management"+'\n'+"2-edge management"+'\n'+"3-exit");
            num = read.nextInt();
            if (num == 1) node_management();  //operations related with nodes
            else if (num == 2) edge_management(); //operations related with edges
            else if (num == 3) seguir = false; //end program
            else System.out.println("Enter a valid number");
        }

    }

    public static void edge_management() {
        int num;
        Scanner read = new Scanner(System.in);
        System.out.println('\n'+"Edge operations:");
        System.out.println("1-Add Edge"+'\n'+"2-Given an edge, verify if it is part of the graph");
        System.out.println("3-Verify if there is an edge between two nodes");
        System.out.println("4-Remove an edge given" + '\n' + "5-Remove the edge between two nodes");
        System.out.println("6-Get the edges adjacent to a node" + '\n' + "7-Get the edge between two nodes");
        System.out.println("Any other key-exit form edge management");
        num = read.nextInt();
        if (num == 1) {
            System.out.println("create the edge, enter id's of the nodes adjacent (they have to be in the graph), and the weight of the edge");
            int id1, id2, w;
            id1 = read.nextInt();
            id2 = read.nextInt();
            w = read.nextInt();
            TNode n1 = nodes.get(id1);
            TNode n2 = nodes.get(id2);
            TEdge e = new TEdge(n1, n2, w);
            g.addEdge(e);
            System.out.println("edge added succesfully!"+'\n');
        }
        else if (num == 2) {
            System.out.println("create the edge, enter id's of the nodes adjacent (they have to be in the graph), and the weight of the edge");
            int id1, id2, w;
            id1 = read.nextInt();
            id2 = read.nextInt();
            w = read.nextInt();
            TNode n1 = nodes.get(id1);
            TNode n2 = nodes.get(id2);
            TEdge e = new TEdge(n1, n2, w);
            if (g.edgeExists(e)) System.out.println("the edge exists");
            else System.out.println("the edge doesn't exist");
            System.out.println();
        }
        else if (num == 3) {
            System.out.println("enter the id of the two nodes");
            int id1, id2, w;
            id1 = read.nextInt();
            id2 = read.nextInt();
            TNode n1 = nodes.get(id1);
            TNode n2 = nodes.get(id2);
            if (g.edgeExists(n1, n2)) System.out.println("the edge exists");
            else System.out.println("the edge doesn't exist");
            System.out.println();
        }
        else if (num == 4) {
            System.out.println("enter the edge(it has to be part of the graph): enter id's of the nodes adjacent, and the weight of the edge");
            int id1, id2, w;
            id1 = read.nextInt();
            id2 = read.nextInt();
            w = read.nextInt();
            TNode n1 = nodes.get(id1);
            TNode n2 = nodes.get(id2);
            TEdge e = new TEdge(n1, n2, w);
            if (g.edgeExists(e)) g.removeEdge(e);
            else System.out.println("The edge is not in the graph");
            System.out.println();
        }
        else if (num == 5) {
            System.out.println("enter the id of the two nodes");
            int id1, id2, w;
            id1 = read.nextInt();
            id2 = read.nextInt();
            TNode n1 = nodes.get(id1);
            TNode n2 = nodes.get(id2);
            if (g.edgeExists(n1, n2)) g.removeEdge(n1, n2);
            System.out.println("edge deleted succesfully!"+'\n');
        }
        else if (num == 6) {
            System.out.println("enter the id of the node");
            int id = read.nextInt();
            TNode n = nodes.get(id);
            if (g.nodeExists(n)) {
                Set<Edge> s = g.nodeEdges(n);
                System.out.println("The edges adjacent to the node "+id+" are:");
                for (Edge a:s) System.out.println("node 1: "+a.getNode()+" node 2: "+a.getNeighbor(a.getNode())+" weight : "+a.getWeight());
            }
            else System.out.println("The node is not in the graph");
            System.out.println();
        }
        else if (num == 7) {
            System.out.println("enter the id of the two nodes");
            int id1, id2, w;
            id1 = read.nextInt();
            id2 = read.nextInt();
            TNode n1 = nodes.get(id1);
            TNode n2 = nodes.get(id2);
            if (g.edgeExists(n1, n2)) {
                System.out.println("The edge has weight: "+g.getEdge(n1, n2).getWeight());
            }
            else System.out.println("There is no edge between nodes "+n1.toString()+" and "+n2.toString());
            System.out.println();
        }
    }

    public static void node_management() {
        int num;
        Scanner read = new Scanner(System.in);
        System.out.println('\n'+"Node operations:");
        System.out.println("1-Add a Node"+'\n'+"2-Verify if a node is contained in the graph");
        System.out.println("3-erase a node (it has to be in the graph previously)");
        System.out.println("4-get all the nodes from the graph"+'\n'+"any other key-exit form node management");
        num = read.nextInt();
        if (num == 1) { //Add a node to the graph
            System.out.println("enter the id of the node you want to add, it is assumed it is not currently part of the graph");
            int id = read.nextInt();
            TNode n = new TNode(id);
            g.addNode(n);
            nodes.put(id, n);
            System.out.println("node added succesfully"+'\n'+'\n');
        }
        else if (num == 2) {
            System.out.println("enter id of the node you want to verify if it is in the graph");
            int id = read.nextInt();
            TNode n = nodes.get(id);
            System.out.print("The node with id " + n.toString());
            if(g.nodeExists(n)) System.out.println(" is in the graph"+'\n');
            else System.out.println(" is not in the graph"+'\n');
        }
        else if (num == 3) {
            System.out.println("enter the id of the node you want to delete, the node has to be contained in the graph");
            int id = read.nextInt();
            TNode n = nodes.get(id);
            if (g.nodeExists(n)) {
                g.removeNode(n);
                System.out.println("node deleted"+'\n');
            }
            else System.out.println("There is not such a node" + '\n');
        }
        else if (num == 4) {
            System.out.println("These are the id's of all nodes of the graph");
            Set<Node> s = g.getNodeSet();
            for (Node n:s) System.out.println(n.toString());
            System.out.println();
        }

    }
}
