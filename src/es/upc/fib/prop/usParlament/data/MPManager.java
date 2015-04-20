package es.upc.fib.prop.usParlament.data;

import es.upc.fib.prop.usParlament.domain.MP;

import java.util.List;

/**
 * Created by ondrej on 17.4.15.
 */
public interface MPManager {

	public void createMP(MP mp);

	public List<MP> getAllMPs();

	public MP getMPbyId(Long id);

	public void deleteMP(MP mp);

}
