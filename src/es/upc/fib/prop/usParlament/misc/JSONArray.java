package es.upc.fib.prop.usParlament.misc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by miquel on 16/05/15.
 */
public class JSONArray extends JSON
{
    private ArrayList<JSON> array;

    public JSONArray()
    {
        array = new ArrayList<>();
    }

    public void addElement(JSON j)
    {
        array.add(j);
    }

    public String stringify()
    {
        String retorn = "[";
        boolean heIterat = false;
        for(JSON j:array){
            retorn+=j.stringify();
            retorn+=",";
            heIterat = true;
        }
        if(heIterat) retorn = retorn.substring(0,retorn.length()-1); //TODO: revisar

        retorn+="]";
        return retorn;
    }

    public ArrayList<JSON> getArray() {
        return array;
    }

    public String toString()
    {
        String retorn = "[\n";
        boolean heIterat = false;
        for(JSON j:array){
            retorn+=j.stringify();
            retorn+=",\n";
            heIterat = true;
        }
        if(heIterat) retorn = retorn.substring(0,retorn.length()-2);

        retorn+="\n]";
        return retorn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JSONArray)) return false;

        JSONArray jsonArray = (JSONArray) o;

        Set<JSON> arraySet = new HashSet<>(array);
        Set<JSON> jsonArraySet = new HashSet<>(jsonArray.array);

        return !(array != null ? !arraySet.equals(jsonArraySet) : jsonArray.array != null);
    }

    @Override
    public int hashCode() {
        return array != null ? array.hashCode() : 0;
    }
}
