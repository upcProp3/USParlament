package es.upc.fib.prop.usParlament.data;

import es.upc.fib.prop.usParlament.domain.Attribute;

import java.util.List;

/**
 * Created by ondrej on 17.4.15.
 */
public interface AttributeManager {

	public void createAttribute(Attribute attr);

	public List<Attribute> getAllAttributes();

	public Attribute getAttributeById(Integer id);

	public void deleteAttribute(Attribute attr);
}
