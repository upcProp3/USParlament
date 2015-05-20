package es.upc.fib.prop.usParlament.domain;


import java.util.Comparator;





/**
 * Created by Ondrej Velisek on 22.4.15.
 */
public class AttrDefinition {
	private String name;
	private int importance;

	/**
	 * An AttrDefinition is created with name 'name' and importance 'importance'.
	 * @param name name of attribute definition. It should be unique.
	 * @param importance positive number. Higher importance for computing relationship.
	 */
	public AttrDefinition(String name, int importance) {
		this.name = name;
		this.importance = importance;
	}

	/**
	 * It returns the implicit AttrDefinition name.
	 * @return name of attribute. Should be unique.
	 */
	public String getName() {
		return name;
	}

	/**
	 * The name of the implicit AttrDefinition is set to 'name'.
	 * @param name new name of attribute. Should be unique.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * It returns the implicit AttrDefinition importance.
	 * @return positive integer. Higher means importance for compute weights.
	 */
	public int getImportance() {
		return importance;
	}

	/**
	 * The importance of the implicit AttrDefinition is set to 'importance'.
	 * @param importance Positive integer. Higher means importance for compute weights.
	 */
	public void setImportance(int importance) {
		this.importance = importance;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof AttrDefinition)) return false;

		AttrDefinition that = (AttrDefinition) o;
        return (that.getName().equals(this.getName()));
	}

	@Override
	public int hashCode() {
        return name.hashCode();
	}

	@Override
	public String toString() {
		return "\nname: " + name +
				"\nimportance: " + importance;
	}


    public int compare(AttrDefinition ad)
    {
        return this.name.compareTo(ad.getName());
    }


}
