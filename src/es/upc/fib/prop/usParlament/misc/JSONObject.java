package es.upc.fib.prop.usParlament.misc;

import java.util.HashMap;
import java.util.LinkedHashMap;
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
    public void addPair(String s,JSON j)
    {
        JSONString js = new JSONString(s);
        if(json.containsKey(js)) throw new IllegalArgumentException("REPEATED NAME");
        json.put(js,j);
    }
    public JSON getJSONByKey(JSONString key) {
        return json.get(key);
    }
    
    public JSON getJSONByKey(String key) {
        return json.get(new JSONString(key));
    }


    public Map<JSONString, JSON> getJson() {
        return json;
    }

    public Map<String,String> basicJSONObjectGetInfo()
    {
        for(JSON j:json.values())
            if(j.getClass() != JSONString.class) throw new IllegalArgumentException("ALL THE VALUES NEED TO BE STRINGS");
        Map<String,String> retorn = new LinkedHashMap<>();
        for(JSONString js:json.keySet()){
            retorn.put(js.getValue(),((JSONString)json.get(js)).getValue());
        }
        return retorn;
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

    @Override
    public int compareTo(JSON json) {
        JSONObject jsonObject = (JSONObject) json;
        if (this.equals(jsonObject)) {
            return 0;
        }
        return this.stringify().compareTo(jsonObject.stringify());
    }
}
