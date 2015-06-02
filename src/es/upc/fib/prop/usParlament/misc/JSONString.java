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

    public String toString()
    {
        return "\""+value+"\"";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JSONString)) return false;

        JSONString that = (JSONString) o;

        return !(value != null ? !value.equals(that.value) : that.value != null);

    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}
