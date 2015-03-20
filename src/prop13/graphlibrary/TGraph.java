package prop13.graphlibrary;


import java.util.Map;
import java.util.Set;

/**
 * Created by miquel on 20/03/15.
 */
public final class TGraph extends Graph
    //nomes fer servir amb TNodes i TEdges
{
    public void printGraph()
    {

        for (Map.Entry<Node, Set<Edge>> entry : graph.entrySet()) {
            ((TNode)entry.getKey()).printNode();
            System.out.print(": ");
            for(Edge e : entry.getValue()){
                ((TEdge)e).printEdge();
                System.out.print(", ");
            }
            System.out.println();
        }

    }

}
