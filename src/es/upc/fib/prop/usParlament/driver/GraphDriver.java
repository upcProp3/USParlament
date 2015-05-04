package es.upc.fib.prop.usParlament.driver;


import es.upc.fib.prop.shared13.Edge;
import es.upc.fib.prop.shared13.Node;

import java.lang.System;
import java.util.*;




/**
 * Created by aleixsacrest on 15/04/15.
 */
public class GraphDriver {

    static TGraph g = new TGraph();

    public static void main(String[] args) {

        System.out.println("This is a Driver for the class Graph");
        Scanner read = new Scanner(System.in);
        System.out.println("What do you want to do? Enter the key and press Enter");
        Boolean seguir = true;
        while(seguir) {
            int num;
            System.out.println("1-node management"+'\n'+"2-edge management"+'\n'+"3-Show the whole graph"+'\n'+"4-exit");
            num = read.nextInt();
            switch (num) {
                case 1:
                    node_management(); //operations related with nodes
                      break;
                case 2:
                    edge_management(); //operations related with edges
                    break;
                case 3:
                    System.out.println(g);
                    System.out.println('\n');
                    break;
                case 4:
                    seguir = false; //end program
                    break;
                default:
                    System.out.println("Enter a valid number");
                    break;
            }
        }

    }

    public static void edge_management() {
        boolean edgeManagement = true;
        while (edgeManagement) {
            int num;
            Scanner read = new Scanner(System.in);
            System.out.println('\n' + "Edge operations:"+'\n'+"1-Add Edge"+'\n'+"2-Remove an edge between two nodes"+ '\n' + "3-Remove all edges from a node"+'\n'+
                    "4-invalidate all edges"+'\n'+"5-Get the total number of edges" + '\n' +
                    "6-Get the number of valid edges of the graph"+'\n'+"7-Show all edges" + '\n' + "8-Show all valid edges"
                    +'\n'+"9-Verify if there is an edge between two nodes"+ '\n' +"10-Verify if there is a valid one"+ '\n' + "11-Get the edge between two nodes"
                    +'\n'+"12-Get the edges adjacent to a node" + '\n' + "13-Get the valid edges adjacent to a node"
                     + '\n' + "Any other number-exit form edge management");

            num = read.nextInt();
            Collection<Edge> s;
            int id1, id2, w;
            TNode n1, n2;
            switch (num) {
                case 5://edge total count
                    System.out.println("The total number of edges is " + g.getEdgeCount() + '\n');
                    break;
                case 6://valid edge count
                    System.out.println("The valid number of edges is " + g.getValidEdgeCount() + '\n'); //TODO: error
                    break;
                case 7://all edges
                    s = g.getEdges();
                    for (Edge e : s) System.out.println(e);
                    System.out.println();
                    break;
                case 8://valid edges
                    s = g.getValidEdges();
                    for (Edge e : s) System.out.println(e);
                    System.out.println();
                    break;
                case 1://add edge
                    System.out.println("create the edges, enter id's of the nodes adjacent (they have to be in the graph), and the weight of the edges"+'\n'+"when you're done, hit 0");
                    boolean addEdges = true;
                    while(addEdges) {
                        id1 = read.nextInt();
                        if (id1 == 0) addEdges = false;
                        else {
                            id2 = read.nextInt();
                            w = read.nextInt();
                            n1 = new TNode(id1);
                            n2 = new TNode(id2);
                            TEdge e = new TEdge(n1, n2, w, true);
                            g.addEdge(e);
                            System.out.println("edge added successfully!" + '\n');
                        }
                    }
                    System.out.println();
                    break;
                case 11://Shows the edge between two nodes
                    System.out.println("enter the id of the two nodes");
                    id1 = read.nextInt();
                    id2 = read.nextInt();
                    n1 = new TNode(id1);
                    n2 = new TNode(id2);
                    if (g.hasEdge(n1, n2)) {
                        System.out.println(g.getEdge(n1, n2));
                    } else
                        System.out.println("There is no edge between nodes " + n1 + " and " + n2);
                    System.out.println();
                    break;
                case 9://tells if there's an edge between two nodes
                    System.out.println("enter the id of the two nodes");
                    id1 = read.nextInt();
                    id2 = read.nextInt();
                    n1 = new TNode(id1);
                    n2 = new TNode(id2);
                    if (g.hasEdge(n1, n2)) System.out.println("the edge exists");
                    else System.out.println("the edge doesn't exist");
                    System.out.println();
                    break;
                case 10://tells if there's a valid edge between two nodes
                    System.out.println("enter the id of the two nodes");
                    id1 = read.nextInt();
                    id2 = read.nextInt();
                    n1 = new TNode(id1);
                    n2 = new TNode(id2);
                    if (g.hasValidEdge(n1, n2)) System.out.println("the edge exists and is valid");
                    else if (g.hasEdge(n1, n2))
                        System.out.println("the edge exists, but is not valid");
                    else System.out.println("the edge doesn't exist");
                    System.out.println();
                    break;
                case 2://Delete an edge
                    System.out.println("enter the nodes adjacent to the edge");
                    id1 = read.nextInt();
                    id2 = read.nextInt();
                    n1 = new TNode(id1);
                    n2 = new TNode(id2);
                    if (g.hasEdge(n1, n2)) g.removeEdge(n1, n2);
                    else System.out.println("The edge was not in the graph");
                    System.out.println("Edge erased successfully" + '\n');
                    break;
                case 3://remove all edges from a node
                    System.out.println("enter the id of the node");
                    id1 = read.nextInt();
                    n1 = new TNode(id1);
                    g.removeAllNodeEdges(n1);
                    System.out.println("successful" + '\n');
                    break;
                case 12://Adjacency List
                    System.out.println("enter the id of the node");
                    id1 = read.nextInt();
                    n1 = new TNode(id1);
                    if (g.hasNode(n1)) {
                        s = g.getAdjacencyList(n1);
                        System.out.println("The edges adjacent to the node " + id1 + " are:");
                        for (Edge a : s) System.out.println(a);
                    } else System.out.println("The node is not in the graph");
                    System.out.println();
                    break;
                case 13://Adjacency List of ValidEdges
                    System.out.println("enter the id of the node");
                    id1 = read.nextInt();
                    n1 = new TNode(id1);
                    if (g.hasNode(n1)) {
                        s = g.getValidAdjacencyList(n1);
                        System.out.println("The valid edges adjacent to the node " + id1 + " are:");
                        for (Edge a : s) System.out.println(a);
                    } else System.out.println("The node is not in the graph");
                    System.out.println();
                    break;
                case 4://invalidate all edges
                    g.invalidateAllEdges();
                    System.out.println("successful" + '\n');
                    break;
                default:
                    edgeManagement=false;
                    break;
            }
        }
    }

    public static void node_management() {
        boolean nodeManagement = true;
        while (nodeManagement) {
            int num;
            Scanner read = new Scanner(System.in);
            System.out.println(
                    '\n' + "Node operations:" + '\n'+"1-Add a Node" + '\n' + "2-erase a node (it has to be in the graph previously)"+ '\n' + "3-Get the order of the graph (number of nodes)" +
                            '\n' + "4-Verify if a node is contained in the graph"+'\n' + "5-Get the degree of a node"
                            + '\n' + "6-Get degree of a node, only counting valid edges" +  '\n' + "7-get all the nodes from the graph" +
                            '\n' + "any other key-exit form node management");

            num = read.nextInt();
            int id;
            TNode n;
            switch (num) {
                case 3:
                    System.out.print("The order of the graph is " + g.getOrder() + '\n');
                    break;
                case 5:
                    System.out.println("Enter the id of the node, whose degree you want to know");
                    id = read.nextInt();
                    n = new TNode(id);
                    g.getNodeDegree(n);
                    System.out.println('\n');
                    break;
                case 6:
                    System.out.println("Enter the id of the node, whose degree you want to know");
                    id = read.nextInt();
                    n = new TNode(id);
                    g.getValidNodeDegree(n);
                    System.out.println('\n');
                    break;
                case 1: //Add a node to the graph
                    System.out.println("enter the id of the nodes you want to add, it is assumed they're not currently part of the graph"+'\n'+"when you're done, enter 0 and hit enter");
                    boolean addNodes = true;
                    while (addNodes) {
                        id = read.nextInt();
                        if (id == 0) addNodes = false;
                        else {
                            n = new TNode(id);
                            g.addNode(n);
                            System.out.println("node added successfully" + '\n');
                        }
                    }
                    System.out.println();
                    break;
                case 4:
                    System.out.println("enter id of the node you want to verify if it is in the graph");
                    id = read.nextInt();
                    n = new TNode(id);
                    System.out.print("The node with id " + n);
                    if (g.hasNode(n)) System.out.println(" is in the graph" + '\n');
                    else System.out.println(" is not in the graph" + '\n');
                    break;
                case 2:
                    System.out.println("enter the id of the node you want to delete, the node has to be contained in the graph");
                    id = read.nextInt();
                    n = new TNode(id);
                    if (g.hasNode(n)) {
                        g.removeNode(n);
                        System.out.println("node deleted" + '\n');
                    } else System.out.println("There is not such a node" + '\n');
                    break;
                case 7:
                    System.out.println("These are the id's of all nodes of the graph");
                    Collection<Node> s = g.getNodes();
                    for (Node n1 : s) System.out.println(n1);
                    System.out.println();
                    break;
                default:
                    nodeManagement=false;
                    break;
            }

        }
    }
}
