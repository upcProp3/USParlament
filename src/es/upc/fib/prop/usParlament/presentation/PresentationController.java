/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upc.fib.prop.usParlament.presentation;

import es.upc.fib.prop.usParlament.domain.DomainController;
import es.upc.fib.prop.usParlament.misc.*;

import java.util.*;


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

//    public void test (String s) { dc.loadCongress(s); }


    public JSONObject getShortMPList()
    {
        //TEST
        JSONObject j = new JSONObject();
        JSONArray jay = new JSONArray();
        for(int i = 0;i<10;i++){
            JSONObject jmp = new JSONObject();
            jmp.addPair(new JSONString("State"),new JSONString("AL"));
            jmp.addPair(new JSONString("District"),new JSONString(Integer.toString(i)));
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

    public void deleteAttribute(JSONObject jo,JSONObject ja)
    {
        dc.deleteAttribute(jo, ja);
    }

    public void addAttributes(JSONObject mp,JSONArray attr)
    {
        dc.addOrModifyAttribute(mp, attr);
    }

    public void deleteMP(State state,int district)
    {
        dc.deleteMP(state, district);
    }

    public JSONObject getMPList()
    {
        return j.StringToJSON(dc.getMPList());
    }
    
    public JSONObject getMPInfo(State state, int district)
    {
         return j.StringToJSON(dc.getMPInfo(state, district));
    }

    public void newCongress()
    {
        dc.newCongress();
    }
    
    public JSONObject getMainPartitionSize()
    {
        JSONString j = new JSONString(dc.getMainPartitionSize());
        JSONString key = new JSONString("Number");
        JSONObject jRet = new JSONObject();
        jRet.addPair(key, j);
        return jRet;
    }

    public JSONObject getMainPartitionCommunities() {
        return null;
    }
    
    public JSONObject getSecCommunityNumber()
    {
        //return domainController.getSecCommuntiyNumber();
        return null;
    }

    //TODO
    public void addMPToCommunity(String cName, State st, Integer dt) {
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


    public String saveCurrentCongress(String name) {
        return dc.saveCurrentCongress(name);
    }

    public List<String> loadAllCongressNames() {
        List<String> list = new ArrayList<>();
        JSONizer json = new JSONizer();
        JSONObject jo = json.StringToJSON(dc.loadAllCongressesNames());
        for (JSON j : ((JSONArray)jo.getJSONByKey("congressesNames")).getArray()) {
            list.add(((JSONString) j).getValue());
        }
        return list;
    }

    public String loadCongressAsCurrent(String name) {
        return dc.loadCongressAsCurrent(name);
    }

    public void cleanCommunityManager() {
        dc.cleanCommunityManager();
    }

    public void computeCommunities(String algorithm, String argument) {
        dc.computeCommunities(algorithm, argument);
    }

    public List<Integer> getCommunityIDs() {
        JSONizer json = new JSONizer();
        JSONArray jsonIds = (JSONArray)json.StringToJSON(dc.getCommunityIDs()).getJSONByKey("ids");
        List<Integer> ids = new ArrayList<>();
        for (JSON jo : jsonIds.getArray()) {
            ids.add(Integer.valueOf(((JSONString)jo).getValue()));
        }
        return ids;
    }

    public Set<JSONObject> getMPsCurrentPartition(int selectedCommunity) {
        JSONizer json = new JSONizer();
        JSONArray jsonMPs = (JSONArray)json.StringToJSON(
                dc.getMPsMainPartition(String.valueOf(selectedCommunity)))
                .getJSONByKey("Current partition Community numer " + selectedCommunity);
        Set<JSONObject> mps = new HashSet<>();
        for (JSON jo : jsonMPs.getArray()) {
            mps.add((JSONObject)jo);
        }
        return mps;
    }
}
















