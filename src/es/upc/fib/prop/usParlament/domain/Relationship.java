package es.upc.fib.prop.usParlament.domain;

import es.upc.fib.prop.shared13.Edge;
import es.upc.fib.prop.shared13.Node;

/**
 * Created by Ondrej Velisek on 15.4.15.
 */
public class Relationship extends Edge {

    /**
     * It returns the string form of a Relationship: State&District of the two MPs and the weight value between them.
     * @return
     */
    @Override
    public String toString()
    {
        MP m1,m2;
        m1 = (MP)super.getNode();
        m2 = (MP)super.getNeighbor(getNode());
        return "("+m1.getState()+" "+m1.getDistrict()+","+m2.getState()+" "+m2.getDistrict()+"):"+super.getWeight()+'\n';
    }

    /**
     * A Relationship is created between the MPs 'm1' and 'm2', with the weight 'w'.
	 * @param m1
	 * @param m2
	 * @param w
	 */
	public Relationship(Node m1, Node m2, int w) {
		super(m1, m2, w);
	}
}