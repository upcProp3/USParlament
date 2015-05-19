package es.upc.fib.prop.usParlament.data;

import es.upc.fib.prop.usParlament.domain.AttrDefinition;
import es.upc.fib.prop.usParlament.domain.MP;

import java.util.List;

/**
 * Created by ondrej on 19.5.15.
 */
public interface AttributeManager {

	/**
	 * Creates {@link MP} in the DB
	 * @param attrDefinition Attribute definition to be created
	 */
	public void createAttrDefinition(AttrDefinition attrDefinition);

	/**
	 * Find {@link AttrDefinition} with given ID
	 * @param id ID of the AttrDefinition to be found
	 * @return AttrDefinition if one with this id exists, null otherwise
	 */
	public AttrDefinition findAttrDefinitionByID(Long id);

	/**
	 * Find all saved {@link AttrDefinition}
	 * @return List of all AttrDefinitions
	 */
	public List<AttrDefinition> findAllAttrDefinitions();

	/**
	 * If there is saved {@link AttrDefinition} x such that x.equals(attrDefinition) delete it
	 * @param attrDefinition  AttrDefinition to be deleted
	 */
	public void deleteAttrDefinition(AttrDefinition attrDefinition);

	/**
	 * update information of {@link AttrDefinition} in db
	 * @param attrDefinition  updated attrDefinition
	 */
	public void updateAttrDefinition(AttrDefinition attrDefinition);

}
