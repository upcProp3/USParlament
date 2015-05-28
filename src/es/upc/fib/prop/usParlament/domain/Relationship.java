package es.upc.fib.prop.usParlament.domain;

import es.upc.fib.prop.shared13.Edge;
import es.upc.fib.prop.shared13.Node;

/**
 * Created by Ondrej Velisek on 15.4.15.
 */
public class Relationship extends Edge implements Comparable<Relationship> {

    @Override
    public String toString()
    {
        MP m1,m2;
        m1 = (MP)super.getNode();
        m2 = (MP)super.getNeighbor(getNode());
        return "("+m1.getState()+" "+m1.getDistrict()+","+m2.getState()+" "+m2.getDistrict()+"):"+super.getWeight()+'\n';
    }

    /**
	 * @param m1 The first MP of the relation
	 * @param m2 The second MP of the relation
	 * @param w Weight of the relation between the MPs m1 and m2
	 */
	public Relationship(Node m1, Node m2, int w) {
		super(m1, m2, w);
	}

    @Override
    public int compareTo(Relationship relationship) {
        if (this.equals(relationship)) {
            return 0;
        }
        MP my1 = (MP)getNode();
        MP my2 = (MP)getNeighbor(my1);
        MP its1 = (MP)relationship.getNode();
        MP its2 = (MP)relationship.getNeighbor(its1);

        int comp1 = my1.compareTo(its1);
        if (comp1 != 0) {
            return comp1;
        }
        return my2.compareTo(its2);
    }
}
