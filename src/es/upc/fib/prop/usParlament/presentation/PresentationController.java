/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upc.fib.prop.usParlament.presentation;

import es.upc.fib.prop.usParlament.domain.DomainController;
import es.upc.fib.prop.usParlament.misc.*;

import java.util.Map;


/**
 * ///IMPORTANT
 *  you can see that for now i don't pass real data, i create some data with the same format
 * as the json it will recieve, to be able to see if it works
 * @author miquel
 */
public class PresentationController {

    private DomainController dc;
    private JSONizer j;
    PresentationController()
    {
        dc = new DomainController();
        j = new JSONizer();
    }

    public JSONObject getShortMPList()
    {
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
        ///END OF TEST
        //GOOD CODE
        //return j.StringToJSON(dc.getShortMPList());
        //END OF GOOD CODE
    }
    
    public void addMP(JSONObject mp,JSONArray attr)
    {
        dc.addMP(mp);
        Map<String,String> dmp = mp.basicJSONObjectGetInfo();
        String estat = dmp.get("State");
        String distr = dmp.get("District");
        //System.out.println(estat+distr);
        dc.addOrModifyAttribute(mp,attr);

    }

    public void deleteMP(State state,int district)
    {
        dc.deleteMP(state,district);
    }

    public JSONObject getMPList()
    {
        return j.StringToJSON(dc.getMPList());
    }
    
    public JSONObject getMPInfo(State state, int district)
    {
         return j.StringToJSON(dc.getMPInfo(state,district));
    }

    public void newCongress()
    {
        dc.newCongress();
    }
    
    public JSONObject getMainCommunityNumber()
    {
        //return domainController.getMainCommuntiyNumber();
        return null;
    }
    
    public JSONObject getSecCommunityNumber()
    {
        //return domainController.getSecCommuntiyNumber();
        return null;
    }

    public boolean existsAttrDef(String name)
    {
        return dc.existsAttrDef(name);
    }

    public void addOrModifyAttrDef(JSONObject obj)
    {
        dc.addOrModifyAttrDef(obj);
    }

    public JSONObject getAttrDefs()
    {
        return j.StringToJSON(dc.getAttrDefs());
    }
    
}
















