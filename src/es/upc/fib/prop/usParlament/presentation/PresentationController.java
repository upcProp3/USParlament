/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upc.fib.prop.usParlament.presentation;

import es.upc.fib.prop.usParlament.domain.DomainController;
import es.upc.fib.prop.usParlament.domain.DomainControllerImpl;
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
        dc = new DomainControllerImpl();
        j = new JSONizer();
    }

    public void setCurrentToPartition1() { dc.setMainToPartition1(); }
    public void setCurrentToPartition2() { dc.setMainToPartition2(); }


    public JSONObject getShortMPList()
    {
        /*
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
        */
        ///END OF TEST
        //GOOD CODE
        return j.StringToJSON(dc.getMPsShort());
        //END OF GOOD CODE
    }
    
    public void addMP(JSONObject mp,JSONArray attrs)
    {
        JSONizer json = new JSONizer();
        dc.addMP(json.JSONtoString(mp));
        Map<String,String> dmp = mp.basicJSONObjectGetInfo();
        String estat = dmp.get("State");
        String distr = dmp.get("District");
        JSONObject jAttrs = new JSONObject();
        jAttrs.addPair("attributes", attrs);
        dc.addOrModifyAttributes(json.JSONtoString(mp), json.JSONtoString(jAttrs));

    }

    public void removeAttribute(JSONObject jo, String attrDefName)
    {
        JSONizer json = new JSONizer();
        dc.removeAttribute(json.JSONtoString(jo), attrDefName);
    }

    public void addAttributes(JSONObject mp,JSONArray attrs)
    {
        JSONizer json = new JSONizer();
        JSONObject jAttrs = new JSONObject();
        jAttrs.addPair("attributes", attrs);
        dc.addOrModifyAttributes(json.JSONtoString(mp), json.JSONtoString(jAttrs));
    }

    public void deleteMP(State state,int district)
    {
        dc.removeMP(state, district);
    }

    public JSONObject getMPList()
    {
        return j.StringToJSON(dc.getMPs());
    }
    
    public JSONObject getMPInfo(State state, int district)
    {
         return j.StringToJSON(dc.getMP(state, district));
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

    public void addMPToCommunity(String name, State st, Integer dt) {
        dc.addMPToCommunity(name, st, dt);
    }

    public void deleteMPFromCommunity (String name, State st, Integer dt) {
        dc.removeMPFromCommunity(name, st, dt);
    }

    public boolean existsAttrDef(String name)
    {
        return dc.existsAttrDef(name);
    }

    public void addOrModifyAttrDef(JSONObject obj)
    {
        JSONizer json = new JSONizer();
        dc.addOrModifyAttrDef(json.JSONtoString(obj));
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

    public String saveCurrentPartition(String pName) throws InternalException {
        if (pName.trim().length() < 3) {
            throw new InternalException("Partition name has to have at least 3 characters.");
        }
        return dc.saveMainPartition(pName.trim());
    }
    
    public void loadPartitionAs(String pName, String as) {
        dc.loadPartitionInto(pName, as);
    }

    public void cleanCommunityManager() {
        dc.cleanCommunityManager();
    }
    public void cleanCompareManager() {
        dc.cleanCompareManager();
    }
    public void computeCommunities(String algorithm, String argument) {
        dc.computePartition(algorithm, argument);
    }

    public boolean hasMainPartitionCommunityName(String name)
    {
        return dc.hasMainPartitionCommunityName(name);
    }

    public List<String> getCommunityIDs(String partition) {
        JSONizer json = new JSONizer();
        JSONArray jsonIds = (JSONArray)json.StringToJSON(dc.getCommunityIDs(partition)).getJSONByKey("ids");
        List<String> ids = new ArrayList<>();
        for (JSON jo : jsonIds.getArray()) {
            ids.add(((JSONString)jo).getValue());
        }
        return ids;
    }



    public void changeMainPartitionCommunityName(String oldName,String newName)
    {
        dc.changeMainPartitionCommunityName(oldName,newName);
    }



    public Set<JSONObject> getMPsCurrentPartition(String community) {
        JSONizer json = new JSONizer();
        JSONArray jsonMPs = (JSONArray)json.StringToJSON(
                dc.getMPsInMainPartition(community))
                .getJSONByKey("mps");
        Set<JSONObject> mps = new HashSet<>();
        for (JSON jo : jsonMPs.getArray()) {
            mps.add((JSONObject)jo);
        }
        return mps;
    }

    public Set<JSONObject> getMPsPartition1(String selectedCommunity) {
        JSONizer json = new JSONizer();
        JSONArray jsonMPs = (JSONArray)json.StringToJSON(
                dc.getMPsInPartition1(selectedCommunity))
                .getJSONByKey("mps");
        Set<JSONObject> mps = new HashSet<>();
        for (JSON jo : jsonMPs.getArray()) {
            mps.add((JSONObject)jo);
        }
        return mps;
    }

    public Set<JSONObject> getMPsPartition2(String selectedCommunity) {
        JSONizer json = new JSONizer();
        JSONArray jsonMPs = (JSONArray)json.StringToJSON(
                dc.getMPsInPartition2(selectedCommunity))
                .getJSONByKey("mps");
        Set<JSONObject> mps = new HashSet<>();
        for (JSON jo : jsonMPs.getArray()) {
            mps.add((JSONObject)jo);
        }
        return mps;
    }



    public void addNewCommunity() { dc.addNewCommunity(); }

    public void deleteSelectedCommunity(String name) {
        dc.removeCommunity(String.valueOf(name));
    }

    public JSONObject compareFunction() {
        JSONizer json = new JSONizer();
        JSONObject jInfo = (JSONObject)json.StringToJSON(dc.compare2partitions());
        return jInfo;
    }

    public List<String> loadAllPartitionNamesInCurrentCongress() throws InternalException {
        JSONObject allNames = (new JSONizer()).StringToJSON(dc.loadAllPartitionNamesInCurrentCongress());
        if (allNames.getJSONByKey("Exception") != null) {
            throw new InternalException(((JSONString)((JSONObject)allNames.getJSONByKey("Exception"))
                    .getJSONByKey("Message")).getValue());
        }
        List<String> names = new ArrayList<>();
        for (JSON jo : ((JSONArray)allNames.getJSONByKey("partitionNames")).getArray()) {
            names.add(((JSONString)jo).getValue());
        }
        return names;
    }

    public void removeAllSavedPartitions(String congresssName) {
        dc.removeAllSavedPartitions(congresssName);
    }

    public boolean areSavedPartitionsCompatibleWithCurrentCongress(String congresssName) {
        return dc.areSavedPartitionsCompatibleWithCurrentCongress(congresssName);
    }

    public String getCurrentCongressName() {
        return dc.getCurrentCongressName();
    }

    public void computeRelationships() { dc.computeRelationships(); }

    class InternalException extends Exception {
        public InternalException(String message) {
            super(message);
        }
    }


}
















