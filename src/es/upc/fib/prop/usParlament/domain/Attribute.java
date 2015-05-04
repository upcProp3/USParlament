package es.upc.fib.prop.usParlament.domain;

/**
 * Created by Alex Miro on 8/4/15.
 * Contributions of Ondrej Velisek.
 */

public class Attribute  {
	private Long id;
    private AttrDefinition definition;
    private Object value;

	/**
	 * An Attribute is created with definition 'definition' and value 'value'.
	 * @param definition
	 * @param value
	 */
	public Attribute(AttrDefinition definition, Object value) {
		this.definition = definition;
		this.value = value;
	}

	/**
	 * It returns the implicit Attribute identification number.
	 * @return
	 */
	public Long getId() {
		return id;
	}

	/**
	 * The identification number of the implicit Attribute is set to 'id'.
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * It returns the implicit Attribute value.
	 * @return
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * The value of the implicit Attribute is set to 'value'.
	 * @param value
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * It returns the implicit Attribute definition.
	 * @return
	 */
	public AttrDefinition getDefinition() {
		return definition;
	}

	/**
	 * It returns true iff 'o' is equal to the implicit Attribute (same values for definition and value).
	 * @param o
	 * @return
	 */
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attribute attr = (Attribute) o;
        return (attr.getDefinition().equals(this.definition) && attr.getValue().equals(this.value));
    }

	/**
	 * It returns the hash code of the implicit Attribute.
	 * @return
	 */
	@Override
	public int hashCode() {
		return definition.hashCode()+value.hashCode();
	}

	/**
	 * It returns the string form of an Attribute: name and value.
	 * @return
	 */
	@Override
	public String toString() {
		return definition.getName() + ": " + value;
	}
}
