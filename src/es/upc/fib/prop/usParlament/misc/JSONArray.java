package es.upc.fib.prop.usParlament.misc;

import java.util.ArrayList;

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
}
