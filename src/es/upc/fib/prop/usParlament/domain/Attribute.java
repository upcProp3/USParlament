package es.upc.fib.prop.usParlament.domain;

/**
 * Created by Alex Miro on 8/4/15.
 * Contributions of Ondrej Velisek.
 */

public class Attribute  {
    private AttrDefinition definition;
    private Object value;

	/**
	 * An Attribute is created with definition 'definition' and value 'value'.
	 * @param definition definition of attribute.
	 * @param value value of attribute. Usually string.
	 */
	public Attribute(AttrDefinition definition, Object value) {
		this.definition = definition;
		this.value = value;
	}

	/**
	 * It returns the implicit Attribute value.
	 * @return value of attribute
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * The value of the implicit Attribute is set to 'value'.
	 * @param value value to set.
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * It returns the implicit Attribute definition.
	 * @return definition of attribute.
	 */
	public AttrDefinition getDefinition() {
		return definition;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attribute attr = (Attribute) o;
        return (attr.getDefinition().equals(this.definition) && attr.getValue().equals(this.value));
    }

	@Override
	public int hashCode() {
		return definition.hashCode()+value.hashCode();
	}

	@Override
	public String toString() {
		return definition.getName() + ": " + value;
	}
}
