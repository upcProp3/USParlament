package es.upc.fib.prop.usParlament.driver;


import es.upc.fib.prop.shared13.Node;

/**
 * Created by albert on 16/04/15.
 */
public class TNode extends Node {
    private final int key;

    public TNode(int key) {
        this.key = key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != getClass()) return false;
        return ((TNode) o).getKey() == this.key;
    }


    @Override
    public int hashCode() {
        //return Integer.hashCode(key);
        return 3;
    }

    @Override
    public String toString() {
        return Integer.toString(key);
    }

    @Override
    public int compareTo(Node n) {
        return ((TNode) n).getKey() - this.key;
    }

    public int getKey() {
        return key;
    }
}