package es.upc.fib.prop.usParlament.misc;

/**
 * Created by miquel on 16/05/15.
 */
public class JSONString extends JSON
{
    private String value;

    public JSONString()
    {
        value = "";
    }

    public JSONString(String s)
    {
        value = s;
    }

    public void setValue(String s)
    {
        value = s;
    }

    public String getValue()
    {
        return value;
    }

    public String stringify()
    {
        return this.toString();
    }

    public boolean equals(Object o)
    {
        if(this.getClass() != o.getClass()) return false;
        return this.value.equals(((JSONString)o).getValue());
    }

    public int hashCode()
    {
        return value.hashCode();
    }

    public String toString()
    {
        return "\""+value+"\"";
    }

    @Override
    public int compareTo(JSON json) {
        JSONString jsonString = (JSONString) json;
        return this.getValue().compareTo(jsonString.getValue());
    }
}
