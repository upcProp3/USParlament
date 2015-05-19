package es.upc.fib.prop.usParlament.data;

import es.upc.fib.prop.usParlament.domain.AttrDefinition;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by ondrej on 19.5.15.
 */
public class AttributeManagerImpl implements AttributeManager {
	private final DataSource ds;

	public AttributeManagerImpl(DataSource ds) {
		this.ds = ds;
	}

	@Override
	public void createAttrDefinition(AttrDefinition attrDefinition) {

	}

	@Override
	public AttrDefinition findAttrDefinitionByID(Long id) {
		return null;
	}

	@Override
	public List<AttrDefinition> findAllAttrDefinitions() {
		return null;
	}

	@Override
	public void deleteAttrDefinition(AttrDefinition attrDefinition) {

	}

	@Override
	public void updateAttrDefinition(AttrDefinition attrDefinition) {

	}
}
