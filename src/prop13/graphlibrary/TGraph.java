package prop13.graphlibrary;


import java.util.Map;
import java.util.Set;

/**
 * Created by miquel on 20/03/15.
 */
public final class TGraph extends Graph {

	@Override
	public String toString() {
		String str = "TGraph{";
		for (Map.Entry<Node, Set<Edge>> entry : getGraph().entrySet()) {
			str += entry.getKey() + ": ";
			for(Edge e : entry.getValue()){
				str += e + ", ";
			}
			str += "\n";
		}
		return str + "}";
	}

}
