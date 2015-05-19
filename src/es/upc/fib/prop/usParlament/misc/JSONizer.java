package es.upc.fib.prop.usParlament.misc;

import java.util.Stack;

/**
 * Created by miquel on 16/05/15.
 */
public class JSONizer
{

    public static void main(String[] args)
    {
        String s = "{\"gat\":   \"meow\",\"cosa\":{\"ca\":[[\"cosa\"],[{\"dede\":[\"ca\"]}],[]]}}";
        JSONizer j = new JSONizer();
        System.out.println(s);
        JSONObject jo = j.StringToJSON(s);
        System.out.println(j.JSONtoString(j.StringToJSON(j.JSONtoString(jo))));
    }


    private final String FORMAT_ERROR = "JSON FORMATTING ERROR";
    private final String CLOSURE_EX = "BRACKETS AND KEYS DO NOT MATCH";

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

    public JSONObject StringToJSON(String s)
    {
        return JSONObjectParser(sanitizeJSONString(s));
    }

    private int countOccurences(String s, char c)
    {
        int occ = 0;
        for (char k : s.toCharArray()) {
            if (k == c) occ++;
        }
        return occ;
    }

    private boolean correctClosures(String s)
    {
        Stack<Character> closure = new Stack<>();
        int nclaus = 0;
        int nquads = 0;
        boolean inString = false;
        for(char c:s.toCharArray()){
            if(c=='{'){
                nclaus++;
                closure.push('{');
            }
            if(c=='}'){
                nclaus--;
                if(closure.peek() != '{') throw new IllegalArgumentException("BRACKETS AND KEYS MIXED");
                else closure.pop();
            }
            if(c=='['){
                nquads++;
                closure.push('[');
            }
            if(c==']'){
                nquads--;
                if(closure.peek() != '[') throw new IllegalArgumentException("BRACKETS AND KEYS MIXED");
                else closure.pop();
            }

            if(nclaus<0 || nquads<0) throw new IllegalArgumentException("KEYS AND OR BRACKETS NOT CLOSED CORRECTLY");
        }
        return (nclaus==0) && (nquads==0);
    }

    private int correspondingClosure(String s)
    {
        char el = s.charAt(0);
        char alt;

        if(el == '{') alt = '}';
        else if(el=='[') alt = ']';
        else if(el == '"') return s.indexOf('"',1);
        else throw new IllegalArgumentException(CLOSURE_EX+"\nString:"+s+" "+"\nchar:"+s.charAt(0));

        //if(s.charAt(0) != el || s.charAt(s.length()-1) != alt) throw new IllegalArgumentException(CLOSURE_EX);

        int nel = 1;
        int position = 1;
        boolean inString = false;
        for(char c:s.substring(1).toCharArray()){
            if(nel == 0) break;
            if(c==el) nel++;
            if(c==alt) nel--;
            position++;
        }
        return position-1;
    }



    private boolean isIgnorable(char c)
    {
        return c == ' ' || c == '\n' || c == '\t';
    }

    private JSONObject JSONObjectParser(String s)
    {
        if(s.charAt(0)!='{' || s.charAt(s.length()-1) != '}') throw new IllegalArgumentException();
        JSONObject jo = new JSONObject();
        int currentIndex = 1;
        boolean end = false;
        while(!end && s.length()>2){
            //READING NAME
            JSONString name;
            String ns = s.substring(currentIndex);
            int fins = correspondingClosure(ns)+1;
            ns = ns.substring(0,fins);
            //System.out.println("name:"+ns);
            name = JSONStringParser(ns);
            currentIndex+=fins;
            if(s.charAt(currentIndex)!=':') System.out.println(FORMAT_ERROR+" Expected :");
            else currentIndex++;
            JSON value;
            String vs = s.substring(currentIndex);
            int fivs = correspondingClosure(vs)+1;
            vs = vs.substring(0,fivs);
            //System.out.println("value:"+vs);
            value = JSONValueParser(vs);
            currentIndex+=fivs;

            jo.addPair(name,value);
            if(currentIndex == s.length()-1){
                end = true;
            }else{
                if(s.charAt(currentIndex)!=',') System.out.println(FORMAT_ERROR+" Expected ,");
                else currentIndex++;
            }
        }

        return jo;
    }

    /**
     * A JSON String has the format:
     * -"
     * -String
     * -"
     * @param s
     * @return
     */
    private JSONString JSONStringParser(String s)
    {
        if(s.charAt(0)!='"' || s.charAt(s.length()-1)!='"') throw new IllegalArgumentException(FORMAT_ERROR);
        return new JSONString(s.substring(1,s.length()-1));
    }

    private JSON JSONValueParser(String s)
    {
        if(s.charAt(0) == '{'){
            return JSONObjectParser(s);
        }else if(s.charAt(0) == '['){
            return JSONArrayParser(s);
        }else if(s.charAt(0) == '\"'){
            return JSONStringParser(s);
        }else{
            throw new IllegalArgumentException(FORMAT_ERROR);
        }
    }

    /**
     * A JSON array
     * @param s
     * @return
     */
    private JSONArray JSONArrayParser(String s)
    {
        if(s.charAt(0) != '[' || s.charAt(s.length()-1) != ']') throw new IllegalArgumentException(CLOSURE_EX);
        if(!correctClosures(s)) throw new IllegalArgumentException(CLOSURE_EX);

        JSONArray ja = new JSONArray();

        int currentIndex = 1;
        boolean end = false;
        while(!end && s.length()>2){
            JSON j;
            String el = s.substring(currentIndex);
            int fiElement = correspondingClosure(el)+1;
            //System.out.println("Fielement:"+fiElement);
            el = el.substring(0,fiElement);
            //System.out.println("ELEMENT:"+el);
            j = JSONValueParser(el);
            currentIndex += fiElement;
            if(s.charAt(currentIndex)==',') {currentIndex++;}
            ja.addElement(j);
            if(currentIndex == s.length()-1){
                end = true;
            }
        }

        return ja;
    }





    public JSONObject StringtoJSON(String s)
    {
        if(!correctClosures(s)) throw new IllegalArgumentException(FORMAT_ERROR);
        return JSONObjectParser(sanitizeJSONString(s));
    }
}
