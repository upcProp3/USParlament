/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upc.fib.prop.usParlament.presentation;

import es.upc.fib.prop.usParlament.misc.JSONArray;
import es.upc.fib.prop.usParlament.misc.JSONObject;
import es.upc.fib.prop.usParlament.misc.JSONString;
import es.upc.fib.prop.usParlament.misc.State;



/**
 * ///IMPORTANT
 *  you can see that for now i don't pass real data, i create some data with the same format
 * as the json it will recieve, to be able to see if it works
 * @author miquel
 */
public class PresentationController {
    static public JSONObject getShortMPList()
    {
        //return domainController.getShortMPList();
        //TEST
        JSONObject j = new JSONObject();
        JSONArray jay = new JSONArray();
        for(int i = 0;i<10;i++){
            JSONObject jmp = new JSONObject();
            jmp.addPair(new JSONString("State"),new JSONString("US"+i));
            jmp.addPair(new JSONString("District"),new JSONString("SA"+i));
            jay.addElement(jmp);
        }
        j.addPair(new JSONString("MPList"),jay);
        return j;
    }
    
    static public void addMP(JSONObject mp,JSONArray attr)
    {
        
    }
    
    static public JSONObject getMPList()
    {
        // return domainController.getMPList();
        JSONObject j = new JSONObject();
        JSONArray jay = new JSONArray();
        for(int i = 0;i<10;i++){
            JSONObject jmp = new JSONObject();
            jmp.addPair(new JSONString("State"),new JSONString("US"+i));
            jmp.addPair(new JSONString("District"),new JSONString("SA"+i));
            for(int z = 0;z<5;z++){
                jmp.addPair(new JSONString("AttrDef"+z),new JSONString("Attr"+z));
            }
            
            jay.addElement(jmp);
        }
        j.addPair(new JSONString("MPList"),jay);
        return j;
    }
    
    static public JSONObject getMPInfo(State state, int district)
    {
        //return domainController.getMPInfo(state,district);
        return null;
    }
    
    public JSONObject getMainCommunityNumber()
    {
        //return domainController.getMainCommuntiyNumber();
        return null;
    }
    
    static public JSONObject getSecCommunityNumber()
    {
        //return domainController.getSecCommuntiyNumber();
        return null;
    }
    
    static public JSONObject getAttrDefs()
    {
        //return domainController.getAttrDefs();

        JSONObject ret = new JSONObject();
        JSONArray ja = new JSONArray();

        for(int z = 0;z<5;z++){
            JSONObject jo = new JSONObject();
            jo.addPair(new JSONString("AttrDefName"),new JSONString("AttrDef"+z));
            jo.addPair(new JSONString("AttrDefImportance"),new JSONString(Integer.toString(z%3)));
            ja.addElement(jo);
        }
        ret.addPair(new JSONString("Attribute Definitions"),ja);

        return ret;
    }
    
}
















