package es.upc.fib.prop.usParlament.domain;

/**
 * Created by ondrej on 22.4.15.
 */
public class AttrDefinition {
	private Long id;
	private String name;
	private int importance;

	public AttrDefinition(String name, int importance) {
		this.name = name;
		this.importance = importance;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public void setId(int id) {
		setId((long) id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getImportance() {
		return importance;
	}

	public void setImportance(int importance) {
		this.importance = importance;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof AttrDefinition)) return false;

		AttrDefinition that = (AttrDefinition) o;

		if (id != null ? !id.equals(that.id) : that.id != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}

	@Override
	public String toString() {
		return "id: " + id +
				"\nname: " + name +
				"\nimportance: " + importance;
	}
}
