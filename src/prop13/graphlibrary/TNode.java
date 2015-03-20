package prop13.graphlibrary;

/**
 * version of node for testing purposes
 * Created by miquel on 20/03/15.
 */
public final class TNode extends Node
{
     private int test;

    public TNode(int i)
    {
        test = i;
    }
    public void printNode()
    {
        System.out.print(test);
    }

}
