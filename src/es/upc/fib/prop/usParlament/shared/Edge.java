package es.upc.fib.prop.usParlament.shared;

/**
 *
 */
public interface Edge {

	/**
	 * Documentation
	 * @param origin
	 */
	public void setOrigin(Node origin);

	/**
	 *
	 * @param destiny
	 */
	public void setDestiny(Node destiny);

	/**
	 *
	 * @return
	 */
    public Node getOrigin();

	/**
	 *
	 * @return
	 */
	public Node getDestiny();

	/**
	 *
	 * @return
	 */
    public double getWeight();

}
