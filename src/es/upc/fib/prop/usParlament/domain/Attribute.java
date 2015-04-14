package es.upc.fib.prop.usParlament.domain;

import es.upc.fib.prop.shared13.Edge;
import java.util.*;


/**
 * Created by alexmiro9 on 8/4/15.
 */

public class Attribute  {

    private String name;
    private int importance; //3: high; 2: medium; 1: low; 0: null
    private Boolean intType; //true = integer/boolean type; false = string type
    private int value1; //irrelevant iff intType = false
    private String value2; //irrelevant iff intType = true;

    /**
     * @pre True
     * @post An empty attribute is created
     * Creates an empty attribute
     */
    public Attribute() {}

    /**
     * @pre importance is an integer between 0 and 3, both included.
     * @post An empty graph is created
     * @param name
     * @param importance
     * Creates an attribute of name 'name' and importance 'importance'.
     */
    public Attribute(String name, int importance)
    {
        this.name = name;
        this.importance = importance;
    }

    /**
     * @pre True
     * @post Returns the name of the attribute.
     * @return
     * Returns the name of the attribute.
     */
    public String getName() { return this.name; }

    /**
     * @pre True
     * @post Returns true if the node is in the graph, false otherwise
     * @return
     * @param n
     */
    public int getImportance() { return this.importance; }

    /**
     * @pre The attribute type is either integer or boolean.
     * @post Returns the value of the attribute.
     * @return
     * Returns de value of the attribute.
     */
    public int getValue1() { return this.value1; }

    /**
     * @pre The attribute type is String.
     * @post Returns the value of the attribute.
     * @return
     * Returns de value of the attribute.
     */
    public String getValue2() { return this.value2; }

    /**
     * @pre True.
     * @post Returns true if the attribute type is integer or boolean, false otherwise.
     * @return
     * Returns true if the attribute type is integer or boolean, false otherwise.
     */
    public Boolean getType() { return this.intType; }

    /**
     * @pre True.
     * @post The value of the attribute's name is 'name'.
     * @param name
     * Sets the value of the attribute's name.
     */
    public void setName(String name) { this.name = name; }

    /**
     * @pre True.
     * @post The value of the attribute's importance is 'importance'.
     * @param importance
     * Sets the value of attribute's importance.
     */
    public void setImportance(int importance) { this.importance = importance; }

    /**
     * @pre The attribute type is either integer or boolean.
     * @post The value of the attribute is 'v'.
     * @param v
     * Sets the value of attribute.
     */
    public void setValue1(int v) { this.value1 = v; }

    /**
     * @pre The attribute type is string.
     * @post The value of the attribute is 'v'.
     * @param v
     * Sets the value of attribute.
     */
    public void setValue2(String v) { this.value2 = v; }

    /**
     * @pre a is a non-empty attribute
     * @post true iff a's value is equal to the attribute's value
     * @param a
     * @return
     * Compares two attributes.
     */
    public Boolean equals(Attribute a) {
        if (a.getType()) {
            return a.getValue1() == this.value1;
        } else {
            return a.getValue2() == this.value2;
        }
    }
}
