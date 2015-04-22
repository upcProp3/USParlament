package es.upc.fib.prop.usParlament.data;

import es.upc.fib.prop.shared13.CommunityAlgorithm;
import es.upc.fib.prop.shared13.CommunitySet;
import es.upc.fib.prop.shared13.GraphOld;

/**
 * Created by ondrej on 17.4.15.
 */
public class CongressManagerImpl implements CongressManager {
	@Override
	public CommunitySet calculateCommunitySet(CommunityAlgorithm algorithm, GraphOld graphOld) {
		return algorithm.compute(graphOld);
	}

	@Override
	public void saveCommunitySet(CommunitySet community) {
		throw new UnsupportedOperationException();
	}

	@Override
	public CommunitySet loadCommunitySetById(Integer id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object compareCommunitySets(CommunitySet first, CommunitySet second) {
		throw new UnsupportedOperationException();
	}
}
