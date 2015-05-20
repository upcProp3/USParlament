package es.upc.fib.prop.usParlament.misc;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by miquel on 16/05/15.
 */

/**
 *  A JSON object contains a set of pair string:value where the value can be a string,
 *  another JSON object or an array of values
 */
public class JSONObject extends JSON
{
    Map<JSONString,JSON> json;

    public JSONObject()
    {
        json = new HashMap<>();
    }

    public void addPair(JSONString js,JSON j)
    {
        if(json.containsKey(js)) throw new IllegalArgumentException("REPEATED NAME");
        json.put(js,j);
    }

    public String stringify()
    {
        String retorn = "{";
        boolean heIterat = false;
        for(JSONString s:json.keySet()){
            retorn+= (s.stringify()+":"+json.get(s).stringify());
            retorn+=",";
            heIterat = true;
        }
        if(heIterat) retorn = retorn.substring(0,retorn.length()-1);
        retorn += "}";
        return retorn;
    }
    public String toString()
    {
        String retorn = "{\n";
        boolean heIterat = false;
        for(JSONString s:json.keySet()){
            retorn+= (s.stringify()+":"+json.get(s).stringify());
            retorn+=",\n";
            heIterat = true;
        }
        if(heIterat) retorn = retorn.substring(0,retorn.length()-2);//Remove the coma and the space
        retorn += "\n}";
        return retorn;
    }
}
