package es.upc.fib.prop.usParlament.domain;

/**
 * Created by alex on 8/4/15.
 * contributions of ondrej
 */

public class Attribute  {
	private Long id;
    private AttrDefinition definition;
    private Object value;

	public Attribute(AttrDefinition definition, Object value) {
		this.definition = definition;
		this.value = value;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

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
