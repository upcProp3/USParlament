package es.upc.fib.prop.usParlament.misc;

/**
 * Created by miquel on 16/05/15.
 */
public class JSONizer
{

    public static void main(String[] args)
    {
        String jotason = "jkdsal klk ska l           jklsd alkadak\t\n \"ms\tda i nd  \nais\"laslkdj ajsl ak k akldj ajkl dalj al k";
        JSONizer j = new JSONizer();
        String s = j.sanitizeJSONString(jotason);
        System.out.println(s);
    }


    private final String FORMAT_ERROR = "JSON FORMATTING ERROR";


    /**
     * @param json a jsonObject (at the top of a json hierarchy there is always an object)
     * @return the string corresponding to that jsonObject
     */
    public String JSONtoString(JSONObject json)
    {
        return json.stringify();
    }

    private String sanitizeJSONString(String s)
    {
        String r = "";
        int lastPoint = 0;
        boolean inString = false;
        for(char c:s.toCharArray()){
            if(c == '"') inString = !inString;
            if(inString ||!isIgnorable(c))r+=c;//Sii estic dins un string NO PUC eliminar en cas contrari depen de si es ignorable
        }
        return r;
    }

    public JSON StringToJSON(String s)
    {
        return JSONObjectParser(sanitizeJSONString(s));
    }

    public int nextClosure(String s,int startIndex,char element)
    {//element is { or [
        char otr;
        if(element == '{') otr = '}';
        else if(element == '[') otr = ']';
        else throw new IllegalArgumentException(FORMAT_ERROR);
        if(s.charAt(0)!=element) throw new IllegalArgumentException(FORMAT_ERROR+" WRONG ELEMENT AT START");
        int nel = 1;
        int currentIndex =1;
        boolean inString = false;
        while(nel>0){
            if(s.charAt(currentIndex) =='\"') inString = !inString;

            if(!inString){
                if(s.charAt(currentIndex)==element) nel++;
                if(s.charAt(currentIndex)==otr) nel--;
            }
            currentIndex++;
        }
        return currentIndex-1;
    }

    private int countOccurences(String s, char c)
    {
        int occ = 0;
        for (char k : s.toCharArray()) {
            if (k == c) occ++;
        }
        return occ;
    }



    private boolean isIgnorable(char c)
    {
        return c == ' ' || c == '\n' || c == '\t';
    }


    public JSONObject JSONObjectParser(String s)
    {

        return null;
    }


    public JSONString JSONStringParser(String s)
    {
        return new JSONString(s);
    }

    public JSON JSONValueParser(String s)
    {
        return null;
    }

    public JSONArray JSONArrayParser(String s)
    {
        JSONArray ja = new JSONArray();
        int currIndex = 0;
        int lastIndex = nextClosure(s,currIndex,'[');

        while(currIndex<lastIndex){
            JSON value = JSONValueParser(s.substring(s))
        }
        return null;
    }





    public JSONObject StringtoJSON(String s)
    {
        return JSONObjectParser(s);
    }
}
