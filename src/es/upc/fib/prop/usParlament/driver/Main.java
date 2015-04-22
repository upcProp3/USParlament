package es.upc.fib.prop.usParlament.driver;

import es.upc.fib.prop.usParlament.driver.TEdge;
import es.upc.fib.prop.usParlament.driver.TGraph;
import es.upc.fib.prop.usParlament.driver.TNode;

//In a future replace all this entries for import java.util.*
//I am doing it this way to keep track of the used structures
//so it will be easier to search for documentation.
import java.util.Vector;

/**
 * Created by miquel on 20/03/15.
 */
public class Main
{
    public static void main(String[] args)
            /*es.upc.fib.prop.usParlament.driver.Main with various testing code, temporary*/
    {
        System.out.println("Hello World!");


        //GetNeighbor Testing and functionality showcase
        //e1 = (n1,n2)
        //e2 = (n2,n3)
        /*
        Node n1 = new Node();
        Node n2 = new Node();
        Node n3 = new Node();
        Edge e1 = new Edge(n1,n2,1);
        Edge e2 = new Edge(n2,n3,1);

        System.out.println("n1: "+n1);
        System.out.println("n2: "+n2);
        System.out.println("n3: "+n3);
        System.out.println("e1 n1 neighbor: "+e1.getNeighbor(n1));
        System.out.println("e1 n2 neighbor: "+e1.getNeighbor(n2));
        System.out.println("e2 n2 neighbor: "+e2.getNeighbor(n2));
        System.out.println("e2 n3 neighbor: "+e2.getNeighbor(n3));
        */
        //End of GetNeighbor testing

        /*
        //Graph testing using TNode and TEdge
        TGraph g = new TGraph();
        Vector<TNode> vn = new Vector<TNode>();
        for(int i = 0;i<4;i++) {
            vn.add(new TNode(i));
            g.addNode(vn.elementAt(i));
        }
        System.out.println("FIRST");
        System.out.println(g);
        g.addEdge(new TEdge(vn.elementAt(0),vn.elementAt(1),1));
        g.addEdge(new TEdge(vn.elementAt(0),vn.elementAt(2),2));
        g.addEdge(new TEdge(vn.elementAt(0),vn.elementAt(3),3));
        g.addEdge(new TEdge(vn.elementAt(1),vn.elementAt(3),4));
        g.addEdge(new TEdge(vn.elementAt(2),vn.elementAt(3),5));
        System.out.println("SECOND");
	    System.out.println(g);
        g.removeEdge(vn.elementAt(0), vn.elementAt(1));
        g.removeEdge(vn.elementAt(2), vn.elementAt(0));
        System.out.println("THIRD");
	    System.out.println(g);
        System.out.println("FOURTH");
        g.removeNode(vn.elementAt(3));
        System.out.println(g);

        System.out.println("END OF GRAPH TESTING");
        System.out.println(g.totalWeight());*/


        //Modularity test

        TGraph g = new TGraph();
        Vector<TNode> vn = new Vector<TNode>();
        for(int i = 0;i<4;i++) {
            vn.add(new TNode(i));
            g.addNode(vn.elementAt(i));
        }

        g.addEdge(new TEdge(vn.elementAt(0),vn.elementAt(1),1));
        g.addEdge(new TEdge(vn.elementAt(1),vn.elementAt(5),3));
        g.addEdge(new TEdge(vn.elementAt(0),vn.elementAt(5),1));
        g.addEdge(new TEdge(vn.elementAt(0),vn.elementAt(3),10));
        g.addEdge(new TEdge(vn.elementAt(1),vn.elementAt(2),2));
        g.addEdge(new TEdge(vn.elementAt(4),vn.elementAt(5),8));
        g.addEdge(new TEdge(vn.elementAt(4),vn.elementAt(3),3));
        g.addEdge(new TEdge(vn.elementAt(2),vn.elementAt(3),9));







    }
}
