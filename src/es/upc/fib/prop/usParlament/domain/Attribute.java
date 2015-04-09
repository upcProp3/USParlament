package es.upc.fib.prop.usParlament.domain;

import es.upc.fib.prop.shared13.Edge;
import java.util.*;


/**
 * Created by alexmirocat on 8/4/15.
 */

public class Attribute  {

    private String name;
    private Integer importance; //1: high; 2: medium; 3: low; 0: null

    public Attribute() {}

    public Attribute(String name, Integer importance)
    {
        this.name = name;
        this.importance = importance;
    }

    public String getName() { return name; }

    public Integer getImportance() { return importance; }

    public void setName(String name) { this.name = name; }

    public void setImportance(Integer importance) { this.importance = importance; }
}
