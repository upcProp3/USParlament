package es.upc.fib.prop.usParlament.domain;

/**
 * Created by Ondrej Velisek on 22.4.15.
 */
public class AttrDefinition {
	private Long id;
	private String name;
	private int importance;

	/**
	 * An AttrDefinition is created with name 'name' and importance 'importance'.
	 * @param name
	 * @param importance
	 */
	public AttrDefinition(String name, int importance) {
		this.name = name;
		this.importance = importance;
	}

	/**
	 * It returns the implicit AttrDefinition identification number.
	 * @return
	 */
	public Long getId() {
		return id;
	}

	/**
	 * The identification number of the implicit AttrDefinition is set to 'id'.
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * The identification number of the implicit AttrDefinition is set to 'id'.
	 * @param id
	 */
	public void setId(int id) {
		setId((long) id);
	}

	/**
	 * It returns the implicit AttrDefinition name.
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * The name of the implicit AttrDefinition is set to 'name'.
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * It returns the implicit AttrDefinition importance.
	 * @return
	 */
	public int getImportance() {
		return importance;
	}

	/**
	 * The importance of the implicit AttrDefinition is set to 'importance'.
	 * @param importance
	 */
	public void setImportance(int importance) {
		this.importance = importance;
	}

	/**
	 * It returns true iff 'o' is equal to the implicit AttrDefinition (same values for name).
	 * @param o
	 * @return
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof AttrDefinition)) return false;

		AttrDefinition that = (AttrDefinition) o;
        return (that.getName().equals(this.getName()));
	}

	/**
	 * It returns the hash code of the implicit AttrDefinition.
	 * @return
	 */
	@Override
	public int hashCode() {
        return name.hashCode();
	}

	/**
	 * It returns the string form of an AttrDefinition: name and importance.
	 * @return
	 */
	@Override
	public String toString() {
		return "\nname: " + name +
				"\nimportance: " + importance;
	}
}
