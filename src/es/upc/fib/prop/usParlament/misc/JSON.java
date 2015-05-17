package es.upc.fib.prop.usParlament.misc;

import java.util.Map;

/**
 * Created by miquel on 16/05/15.
 */
public abstract class JSON
{
    public abstract String stringify();
}
    /*
        a JSON contains pairs name:value
        name is a string
        value can be a string , a JSON or an array of strings and JSONs
    */
