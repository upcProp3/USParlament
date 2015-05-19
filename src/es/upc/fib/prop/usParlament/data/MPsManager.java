package es.upc.fib.prop.usParlament.data;

import es.upc.fib.prop.usParlament.domain.MP;

import java.util.List;

/**
 * Created by ondrej on 15.5.15.
 */
public interface MPsManager {


	/**
	 * Creates {@link MP} in the DB
	 * @param mp MP to be deleted
	 */
	public void createMP(MP mp);

	/**
	 * Find {@link MP} with given ID
	 * @param id ID of the MP to be found
	 * @return MP if one with this id exists, null otherwise
	 */
	public MP findMPByID(Long id);

	/**
	 * Find all saved {@link MP}
	 * @return List of all MPs
	 */
	public List<MP> findAllMPs();

	/**
	 * If there is saved {@link MP} x such that x.equals(mp) delete it
	 * @param mp  MP to be deleted
	 */
	public void deleteMP(MP mp);

	/**
	 * update information of {@link MP} in db
	 * @param mp  updated mp
	 */
	public void updateMP(MP mp);

}
