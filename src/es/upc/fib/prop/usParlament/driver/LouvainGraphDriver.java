package es.upc.fib.prop.usParlament.driver;

import es.upc.fib.prop.shared13.Edge;
import es.upc.fib.prop.shared13.Graph;
import es.upc.fib.prop.shared13.Node;
import es.upc.fib.prop.shared13.louvain.LouvainGraph;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.util.Collection;
import java.util.Scanner;

/**
 * Created by miquel on 24/04/15.
 */
public class LouvainGraphDriver
{
    static Graph g = new LouvainGraph();

    public static void main(String[] args) {

        TNode test1 = new TNode(1);
        TNode test2 = new TNode(1);
        System.out.println(test1==test2);



        System.out.println("This is a Driver for the class Graph");
        Scanner read = new Scanner(System.in);
        System.out.println("What do you want to do? Enter the key and press Enter");
        Boolean seguir = true;
        while(seguir) {
            int num;
            System.out.println("1- afegir node\n2- afegir aresta\n3- imprimir graf\n" +
                    "4- borrar aresta\n5- borrar node\n6- borrar totes arestes");
            num = read.nextInt();
            if(num==1){
                System.out.println("Introdueix numero identificador");
                g.addNode(new TNode(read.nextInt()));
            }else if(num==2){
                System.out.println("Introdueix 2 numero i el pes");
                g.addEdge(new TEdge(new TNode(read.nextInt()),new TNode(read.nextInt()),read.nextDouble()));
            }else if(num==3){
                System.out.println(g);
            }else if(num==4){
                System.out.println("Introdueix els 2 extrems");
                g.removeEdge(new TNode(read.nextInt()),new TNode(read.nextInt()));
            }else if(num==5){
                System.out.println("Introdueix la id");
                g.removeNode(new TNode(read.nextInt()));
            }else if(num == 6){
                System.out.println("Introdueix la id");
                g.removeAllNodeEdges(new TNode(read.nextInt()));
            }else if(num==0){
                return;
            }
        }

    }

}
