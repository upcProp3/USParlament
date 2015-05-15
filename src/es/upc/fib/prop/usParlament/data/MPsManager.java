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
	public void createMP(MP mp) throws ServiceFailureException;

	/**
	 * Find {@link MP} with given ID
	 * @param id ID of the MP to be found
	 * @return MP if one with this id exists, null otherwise
	 */
	public MP findMPByID(Long id) throws ServiceFailureException;

	/**
	 * Find all saved {@link MP}
	 * @return List of all MPs
	 */
	public List<MP> findAllMPs() throws ServiceFailureException;

	/**
	 * If there is saved {@link MP} x such that x.equals(mp) delete it
	 * @param mp  MP to be deleted
	 */
	public void deleteMP(MP mp) throws ServiceFailureException;

}
