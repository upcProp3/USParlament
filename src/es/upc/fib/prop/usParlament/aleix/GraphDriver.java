package es.upc.fib.prop.usParlament.aleix;


import java.lang.System;
import java.util.*;




/**
 * Created by aleixsacrest on 15/04/15.
 */
public class GraphDriver {

    static tGraph g = new tGraph();

    public static void main(String[] args) {

        System.out.println("This is a Driver for the class Graph");
        Scanner read = new Scanner(System.in);
        System.out.println("What do you want to do? Enter the key and press Enter");
        Boolean seguir = true;
        while(seguir) {
            int num;
            System.out.println("1-node management"+'\n'+"2-edge management"+'\n'+"3-Show the whole graph"+'\n'+"4-exit");
            num = read.nextInt();
            if (num == 1) node_management();  //operations related with nodes
            else if (num == 2) edge_management(); //operations related with edges
            else if (num == 3) {System.out.println(g+"\n");} //TODO: g.toString() is ignored
            else if (num == 4) seguir = false; //end program
            else System.out.println("Enter a valid number");
        }

    }

    public static void edge_management() {
        int num;
        Scanner read = new Scanner(System.in);
        System.out.println('\n'+"Edge operations:");
        System.out.println("1-Get the total number of edges"+'\n'+"2-Get the number of valid edges of the graph");
        System.out.println("3-Show all edges"+'\n'+"4-Show all valid edges");
        System.out.println("5-Add Edge"+'\n'+"6-Get the edge between two nodes");
        System.out.println("7-Verify if there is an edge between two nodes"+'\n'+"8-Verify if there is a valid one");
        System.out.println("9-Remove an edge between two nodes" + '\n' + "10-Remove all edges from a node");
        System.out.println("11-Get the edges adjacent to a node" + '\n' + "12-Get the valid edges adjacent to a node" + '\n' + "13-Get the edge between two nodes");
        System.out.println("14-invalidate all edges"+'\n'+"Any other key-exit form edge management");
        num = read.nextInt();
        if (num == 1) {//edge total count
            System.out.println("The total number of edges is "+g.getEdgeCount()+'\n');
        }
        else if (num == 2) {//valid edge count
            System.out.println("The valid number of edges is "+g.getValidEdgeCount()+'\n'); //TODO: error
        }
        else if (num == 3) {//all edges
            Collection<Edge> s = g.getEdges();
            for (Edge e:s) System.out.println(e.toString());
            System.out.println();
        }
        else if (num == 4) {//valid edges
            Collection<Edge> s = g.getValidEdges();
            for (Edge e:s) System.out.println(e.toString());
            System.out.println();
        }
        else if (num == 5) {//add edge
            System.out.println("create the edge, enter id's of the nodes adjacent (they have to be in the graph), and the weight of the edge");
            int id1, id2, w;
            id1 = read.nextInt();
            id2 = read.nextInt();
            w = read.nextInt();
            tNode n1 = new tNode(id1);
            tNode n2 = new tNode(id2);
            tEdge e = new tEdge(n1, n2, w, true);
            g.addEdge(e);
            System.out.println("edge added successfully!"+'\n');
        }
        else if (num == 6) {//Shows the edge between two nodes
            System.out.println("enter the id of the two nodes");
            int id1, id2;
            id1 = read.nextInt();
            id2 = read.nextInt();
            tNode n1 = new tNode(id1);
            tNode n2 = new tNode(id2);
            if (g.hasEdge(n1, n2)) {
                System.out.println(g.getEdge(n1, n2).toString());
            }
            else System.out.println("There is no edge between nodes "+n1.toString()+" and "+n2.toString());
            System.out.println();
        }
        else if (num == 7) {//tells if there's an edge between two nodes
            System.out.println("enter the id of the two nodes");
            int id1, id2;
            id1 = read.nextInt();
            id2 = read.nextInt();
            tNode n1 = new tNode(id1);
            tNode n2 = new tNode(id2);
            if (g.hasEdge(n1, n2)) System.out.println("the edge exists");
            else System.out.println("the edge doesn't exist");
            System.out.println();
        }
        else if (num == 8) {//tells if there's a valid edge between two nodes
            System.out.println("enter the id of the two nodes");
            int id1, id2;
            id1 = read.nextInt();
            id2 = read.nextInt();
            tNode n1 = new tNode(id1);
            tNode n2 = new tNode(id2);
            if (g.hasValidEdge(n1, n2)) System.out.println("the edge exists and is valid");
            else if (g.hasEdge(n1, n2)) System.out.println("the edge exists, but is not valid");
            else System.out.println("the edge doesn't exist");
            System.out.println();
        }
        else if (num == 9) {//Delete an edge
            System.out.println("enter the nodes adjacent to the edge");
            int id1, id2;
            id1 = read.nextInt();
            id2 = read.nextInt();
            tNode n1 = new tNode(id1);
            tNode n2 = new tNode(id2);
            if (g.hasEdge(n1, n2)) g.removeEdge(n1, n2);
            else System.out.println("The edge was not in the graph");
            System.out.println("Edge erased successfully"+'\n');
        }
        else if (num == 10) {//remove all edges from a node
            System.out.println("enter the id of the node");
            int id = read.nextInt();
            tNode n = new tNode(id);
            g.removeAllNodeEdges(n);
            System.out.println("successful"+'\n');
        }
        else if (num == 11) {//Adjacency List
            System.out.println("enter the id of the node");
            int id = read.nextInt();
            tNode n = new tNode(id);
            if (g.hasNode(n)) {
                Collection<Edge> s = g.getAdjacencyList(n);
                System.out.println("The edges adjacent to the node " + id + " are:");
                for (Edge a:s) System.out.println(a.toString());
            }
            else System.out.println("The node is not in the graph");
            System.out.println();
        }
        else if (num == 12) {//Adjacency List of ValidEdges
            System.out.println("enter the id of the node");
            int id = read.nextInt();
            tNode n = new tNode(id);
            if (g.hasNode(n)) {
                Collection<Edge> s = g.getValidAdjacencyList(n);
                System.out.println("The valid edges adjacent to the node "+id+" are:");
                for (Edge a:s) System.out.println(a.toString());
            }
            else System.out.println("The node is not in the graph");
            System.out.println();
        }
        else if (num == 13) {//Get edge between two nodes
            System.out.println("enter the nodes adjacent to the edge");
            int id1, id2;
            id1 = read.nextInt();
            id2 = read.nextInt();
            tNode n1 = new tNode(id1);
            tNode n2 = new tNode(id2);
            if (g.hasEdge(n1, n1)) System.out.println(g.getEdge(n1, n2).toString());
            else System.out.println("There is not such an edge");
            System.out.println();
        }
        else if (num == 14) {//invalidate all edges
            g.invalidateAllEdges();
            System.out.println("successful"+'\n');
        }
    }

    public static void node_management() {
        int num;
        Scanner read = new Scanner(System.in);
        System.out.println('\n'+"Node operations:");
        System.out.println("1-Get the order of the graph (number of nodes)"+'\n'+"2-Get the degree of a node");
        System.out.println("3-Get degree of a node, only counting valid edges");
        System.out.println("4-Add a Node"+'\n'+"5-Verify if a node is contained in the graph");
        System.out.println("6-erase a node (it has to be in the graph previously)");
        System.out.println("7-get all the nodes from the graph"+'\n'+"any other key-exit form node management");
        num = read.nextInt();
        if (num == 1) {
            System.out.print("The order of the graph is " + g.getOrder() + '\n');
        }
        else if (num == 2) {
            System.out.println("Enter the id of the node, whose degree you want to know");
            int id = read.nextInt();
            tNode n = new tNode(id);
            g.getNodeDegree(n);
            System.out.println('\n');
        }
        else if (num == 3) {
            System.out.println("Enter the id of the node, whose degree you want to know");
            int id = read.nextInt();
            tNode n = new tNode(id);
            g.getValidNodeDegree(n);
            System.out.println('\n');
        }
        else if (num == 4) { //Add a node to the graph
            System.out.println("enter the id of the node you want to add, it is assumed it is not currently part of the graph");
            int id = read.nextInt();
            tNode n = new tNode(id);
            g.addNode(n);
            System.out.println("node added succesfully"+'\n'+'\n');
        }
        else if (num == 5) {
            System.out.println("enter id of the node you want to verify if it is in the graph");
            int id = read.nextInt();
            tNode n = new tNode(id);
            System.out.print("The node with id " + n.toString());
            if(g.hasNode(n)) System.out.println(" is in the graph"+'\n');
            else System.out.println(" is not in the graph"+'\n');
        }
        else if (num == 6) {
            System.out.println("enter the id of the node you want to delete, the node has to be contained in the graph");
            int id = read.nextInt();
            tNode n = new tNode(id);
            if (g.hasNode(n)) {
                g.removeNode(n);
                System.out.println("node deleted"+'\n');
            }
            else System.out.println("There is not such a node" + '\n');
        }
        else if (num == 7) {
            System.out.println("These are the id's of all nodes of the graph");
            Collection<Node> s = g.getNodes();
            for (Node n:s) System.out.println(n.toString());
            System.out.println();
        }

    }
}
