package es.upc.fib.prop.usParlament.data;

import es.upc.fib.prop.shared13.CommunityAlgorithm;
import es.upc.fib.prop.shared13.CommunitySet;
import es.upc.fib.prop.shared13.GraphOld;

/**
 * Created by ondrej on 17.4.15.
 */
public interface CongressManager {

	public CommunitySet calculateCommunitySet(CommunityAlgorithm algorithm, GraphOld graphOld);

	public void saveCommunitySet(CommunitySet community);

	public CommunitySet loadCommunitySetById(Integer id);

	/* TODO Create suitable class for return. Object is not good return type. Or remove whole function and compute it at frontend. */
	public Object compareCommunitySets(CommunitySet first, CommunitySet second);
}
