package es.upc.fib.prop.usParlament.domain;

import es.upc.fib.prop.shared13.Algorithm;
import es.upc.fib.prop.shared13.Node;
import es.upc.fib.prop.shared13.cliques.FCQAlgorithm;
import es.upc.fib.prop.shared13.louvain.LouvainAlgorithm;
import es.upc.fib.prop.shared13.ncliques.NCQAlgorithm;
import es.upc.fib.prop.shared13.newmanngirvan.NGAlgorithm;
import es.upc.fib.prop.usParlament.data.DataController;
import es.upc.fib.prop.usParlament.data.DataControllerImpl;
import es.upc.fib.prop.usParlament.misc.*;

import java.util.*;

/**
 * Created by miquel on 16/05/15.
 *
 * All the TEST INSTRUCTIONS (i.e. printing staff) are commented by //.
 */

//TODO: not finished
public class DomainControllerImpl implements DomainController
{
    /*
        Needs to contain:
        - 1 Graph (congress)
        - 2 Partition (one current and one for comparing purposes)
        Needs to give the ability to consult the graphs

     */
    private Congress currentCongress;
    private String currentCongressName;
    private List<Set<MP>> mainPartition;
    private List<Set<MP>> partition1;
    private List<Set<MP>> partition2;
    private DataController dataController;

    public DomainControllerImpl()
    {
        currentCongress = new Congress();
        mainPartition = new ArrayList<>();
        partition1 = new ArrayList<>();
        partition2 = new ArrayList<>();
        dataController = new DataControllerImpl("congresses");
    }
    // only for tests
    protected Congress getCurrentCongress() {
        return currentCongress;
    }
    protected List<Set<MP>> getMainPartition() {
        return mainPartition;
    }
    protected List<Set<MP>> getPartition1() {
        return partition1;
    }
    protected List<Set<MP>> getPartition2() {
        return partition2;
    }
    protected void setDataController(DataController dataController) {
        this.dataController = dataController;
    }
    protected void setCurrentCongress(Congress congress) {
        this.currentCongress = congress;
    }

    private String exceptionMaker(Exception e) {
        String msg = e.getMessage();
        return "{\"Exception\":{\"Name\":\"" +e.getClass().getSimpleName()+ "\",\"Message\":\"" +msg+ "\"}}";
    }
    private boolean isException(String exception) {
        JSONizer json = new JSONizer();
        try {
            return (json.StringToJSON(exception).getJSONByKey("Exception") != null);
        } catch (Exception e) {
            return false;
        }
    }

    public void setMainToPartition1 () { partition1 = mainPartition; }


    public void setMainToPartition2 () { partition2 = mainPartition; }


    public String getMPsShort() {
        JSONObject jList = new JSONObject();
        JSONArray a = new JSONArray();
        for (MP mp : currentCongress.getMPs()) {
            JSONObject jMP = new JSONObject();

            jMP.addPair(new JSONString("State"),new JSONString(mp.getState().toString()));
            jMP.addPair(new JSONString("District"),new JSONString(Integer.toString(mp.getDistrict())));


            a.addElement(jMP);
        }
        JSONString key = new JSONString("MPList");


        jList.addPair(key, a);
        return jList.stringify();
    }


    public String getMPs() {
        JSONObject ret = new JSONObject();
        JSONString n = new JSONString("MPList");
        JSONArray mps = new JSONArray();
        for (MP mp : currentCongress.getMPs()) {
            JSONObject dip = new JSONObject();
            JSONString attrib = new JSONString(mp.getState().toString());
            dip.addPair(new JSONString("State"), attrib);
            attrib = new JSONString(Integer.toString(mp.getDistrict()));
            dip.addPair(new JSONString("District"), attrib);
            attrib = new JSONString(mp.getFullname());
            dip.addPair(new JSONString("Name"), attrib);
            for (Attribute a : mp.getAttributes()) {
                attrib = new JSONString(a.getValue().toString());
                dip.addPair(new JSONString(a.getDefinition().getName()), attrib);
            }
            mps.addElement(dip);
        }
        ret.addPair(n, mps);

        return ret.stringify();
    }


    public String getMP(State state, int district) {
        MP mp = currentCongress.getMP(state,district);
        JSONObject mi = new JSONObject();
        mi.addPair(new JSONString("State"),new JSONString(mp.getState().toString()));
        mi.addPair(new JSONString("District"),new JSONString(Integer.toString(mp.getDistrict())));

        mi.addPair(new JSONString("Name"), new JSONString(mp.getFullname()));

        JSONArray ja = new JSONArray();
        for(Attribute a:mp.getAttributes()) {
            String attrname = a.getDefinition().getName();
            String attrvalue = a.getValue().toString();
            JSONObject el = new JSONObject();
            el.addPair(new JSONString("AttrDefName"),new JSONString(attrname));
            el.addPair(new JSONString("AttrValue"),new JSONString(attrvalue));
            ja.addElement(el);
        }
        mi.addPair(new JSONString("Attributes"),ja);
        return mi.stringify();
    }


    public String getAttrDefs() {
        JSONObject defs = new JSONObject();
        JSONString js = new JSONString("Attribute Definitions");
        JSONArray ja = new JSONArray();
        for (AttrDefinition def : currentCongress.getAttrDef()) {
            JSONObject jo = new JSONObject();
            jo.addPair(new JSONString("AttrDefName"), new JSONString(def.getName()));
            jo.addPair(new JSONString("AttrDefImportance"), new JSONString(Integer.toString(def.getImportance())));
            ja.addElement(jo);
        }
        defs.addPair(js, ja);
        return defs.stringify();
    }


    public void newCongress() {
        currentCongress = new Congress();
        currentCongressName = null;
        mainPartition = new ArrayList<>();
        partition1 = new ArrayList<>();
        partition2 = new ArrayList<>();
    }


    public boolean existsAttrDef(String name) {
        return currentCongress.hasAttrDef(new AttrDefinition(name, 1));
    }


    public String getMainPartitionSize() {
        return String.valueOf(mainPartition.size()); }


    public String getMPsInMainPartition(String communityID) {
        JSONObject mps = new JSONObject();
        JSONString js = new JSONString("Current partition Community numer " + communityID);
        JSONArray ja = new JSONArray();
        for (MP mp : mainPartition.get(Integer.parseInt(communityID))) {
            JSONObject jo = new JSONObject();
            jo.addPair(new JSONString("State"), new JSONString(mp.getState().toString()));
            jo.addPair(new JSONString("District"), new JSONString(Integer.toString(mp.getDistrict())));
            ja.addElement(jo);
        }
        mps.addPair(js, ja);
        return mps.stringify();
    }


    public String getMPsInPartition1 (String communityID) {
        JSONObject mps = new JSONObject();
        JSONString js = new JSONString("Partition1 Community number " + communityID);
        JSONArray ja = new JSONArray();
        for (MP mp : partition1.get(Integer.parseInt(communityID))) {
            JSONObject jo = new JSONObject();
            jo.addPair(new JSONString("State"), new JSONString(mp.getState().toString()));
            jo.addPair(new JSONString("District"), new JSONString(Integer.toString(mp.getDistrict())));
            ja.addElement(jo);
        }
        mps.addPair(js, ja);
        return mps.stringify();
    }


    public String getMPsInPartition2(String communityID) {
        JSONObject mps = new JSONObject();
        JSONString js = new JSONString("Partition2 Community number "+communityID);
        JSONArray ja = new JSONArray();
        for (MP mp : partition2.get(Integer.parseInt(communityID))) {
            JSONObject jo = new JSONObject();
            jo.addPair(new JSONString("State"), new JSONString(mp.getState().toString()));
            jo.addPair(new JSONString("District"), new JSONString(Integer.toString(mp.getDistrict())));
            ja.addElement(jo);
        }
        mps.addPair(js, ja);
        return mps.stringify();
    }


    public String addMP(String mp) {
        JSONizer json = new JSONizer();
        JSONObject jMP = json.StringToJSON(mp);
        if (!(jMP.hasKey("State") && jMP.hasKey("District") && jMP.hasKey("Name"))) {
            return exceptionMaker(new IllegalArgumentException("mp has to contain State, District and Name"));
        }
        JSONString key = new JSONString("State");
        JSONString jState = new JSONString(((JSONString)jMP.getJSONByKey(key)).getValue());
        key.setValue("District");
        JSONString jDistr = new JSONString(((JSONString)jMP.getJSONByKey(key)).getValue());
        key.setValue("Name");
        JSONString jName = new JSONString(((JSONString)jMP.getJSONByKey(key)).getValue());
        MP m = new MP(jName.getValue(), State.valueOf(jState.getValue()), Integer.valueOf(jDistr.getValue()));
        currentCongress.addNode(m);
        return "{}";
    }


    public void removeMP(State state, int district) {
        currentCongress.removeNode(new MP("INVALID_VALUE", state, district));
    }


    public void removeMP(String mp) {
        JSONizer json = new JSONizer();
        JSONObject jMP = json.StringToJSON(mp);
        JSONString key = new JSONString("State");
        JSONString jState = new JSONString(((JSONString)jMP.getJSONByKey(key)).getValue());
        key.setValue("District");
        JSONString jDistr = new JSONString(((JSONString)jMP.getJSONByKey(key)).getValue());
        removeMP(State.valueOf(jState.getValue()), Integer.valueOf(jDistr.getValue()));
    }


    public void addOrModifyAttribute(String attr, String mp) {
        JSONizer json = new JSONizer();
        JSONObject jAttr = json.StringToJSON(attr);
        JSONObject jMP = json.StringToJSON(mp);

        JSONString key = new JSONString("State");
        JSONString jState = new JSONString(((JSONString)jMP.getJSONByKey(key)).getValue());
        key.setValue("District");
        JSONString jDistr = new JSONString(((JSONString)jMP.getJSONByKey(key)).getValue());
        MP m = currentCongress.getMP(State.valueOf(jState.getValue()), Integer.valueOf(jDistr.getValue()));
        key.setValue("AttrDef");
        JSONString jAttrD = new JSONString(((JSONString)jAttr.getJSONByKey(key)).getValue());
        key.setValue("AttrValue");
        JSONString jAttrV = new JSONString(((JSONString)jAttr.getJSONByKey(key)).getValue());
        Attribute a = new Attribute(currentCongress.getAttrDef(jAttrD.getValue()), jAttrV.getValue());
        m.addAttribute(a);
    }


    public void addOrModifyAttributes(String mp, String attrs) {
        JSONizer json = new JSONizer();
        JSONArray jAttrs = (JSONArray)json.StringToJSON(attrs).getJSONByKey("attributes");
        JSONObject jMP = json.StringToJSON(mp);

        Map<String,String> mpmss = jMP.basicJSONObjectGetInfo();
        MP mpObj = currentCongress.getMP(State.valueOf(mpmss.get("State")),Integer.parseInt(mpmss.get("District")));

        for(JSON j:jAttrs.getArray()){
            Map<String,String> att = ((JSONObject)j).basicJSONObjectGetInfo();

            AttrDefinition atd = currentCongress.getAttrDef(att.get("AttrDefName"));
            if(atd == null) throw new IllegalStateException("NO EXISTEIX LATRIBUT");

            String value = att.get("AttrValue");
            Attribute a = new Attribute(atd,value);
            if(mpObj.hasAttribute(atd)) mpObj.removeAttribute(atd);
            mpObj.addAttribute(new Attribute(atd,value));

        }

    }


    public void cleanCommunityManager() {
        mainPartition = new ArrayList<>();
    }


    public void cleanCompareManager() {
        partition1 = new ArrayList<>();
        partition2 = new ArrayList<>();
    }


    public void removeAttribute(String mp, String attrDefName) {
        JSONizer json = new JSONizer();
        JSONObject jMP = json.StringToJSON(mp);

        Map<String,String> mpmss = jMP.basicJSONObjectGetInfo();
        MP mpObj = currentCongress.getMP(State.valueOf(mpmss.get("State")),Integer.parseInt(mpmss.get("District")));

        AttrDefinition atd = currentCongress.getAttrDef(attrDefName);

        if(atd == null) throw new IllegalStateException("NO EXISTEIX LATRIBUT");
        System.out.println(mp);
        /*if(mp.hasAttribute(atd))*/ mpObj.removeAttribute(atd);
        System.out.println(mp);
    }


    public void addOrModifyAttrDef(String attrDef) {
        System.out.println(attrDef);
        JSONizer json = new JSONizer();
        JSONObject jAttrDef = json.StringToJSON(attrDef);
        JSONString key = new JSONString("AttrDefName");
        JSONString jAttrD = new JSONString(((JSONString)jAttrDef.getJSONByKey(key)).getValue());
        key.setValue("Importance");
        JSONString jImp = new JSONString(((JSONString)jAttrDef.getJSONByKey(key)).getValue());
        String imp = jImp.getValue();

        int importancia;
        switch (imp) {
            case "None":
                importancia = 0;
                break;
            case "Low":
                importancia = 1;
                break;
            case "Medium":
                importancia = 4;
                break;
            case "High":
                importancia = 16;
                break;
            default:
                throw new IllegalStateException("IMPORTANCIA NO RECONEGUDA");
        }

        AttrDefinition ad = new AttrDefinition(jAttrD.getValue(), importancia);
        if (currentCongress.hasAttrDef(ad)) {
            currentCongress.getAttrDef(jAttrD.getValue()).setImportance(importancia);
        } else {
            currentCongress.addAttrDef(ad);
        }
    }


    public void deleteAttrDef(String attrDefName) {
        currentCongress.removeAttrDef(currentCongress.getAttrDef(attrDefName));
    }


    public String saveCurrentCongress(String name) {
        if (name == null) {
            return exceptionMaker(new IllegalArgumentException("name cannot be null"));
        }
        JSONObject congress = new JSONObject();
        JSONArray mps = new JSONArray();
        JSONArray relations = new JSONArray();
        JSONArray definitions = new JSONArray();
        currentCongressName = name;

        congress.addPair(new JSONString("mps"), mps);
        congress.addPair(new JSONString("relations"), relations);
        congress.addPair(new JSONString("attributeDefinitions"), definitions);

        for (MP mp : currentCongress.getMPs()) {
            JSONObject jsonMP = new JSONObject();
            jsonMP.addPair(new JSONString("fullname"), new JSONString(mp.getFullname()));
            jsonMP.addPair(new JSONString("state"), new JSONString(mp.getState().toString()));
            jsonMP.addPair(new JSONString("district"), new JSONString(""+mp.getDistrict()));
            JSONArray attributes = new JSONArray();
            for (Attribute attr : mp.getAttributes()) {
                JSONObject jsonAttr = new JSONObject();
                jsonAttr.addPair(new JSONString("value"), new JSONString(attr.getValue().toString()));
                jsonAttr.addPair(new JSONString("definitionName"), new JSONString(attr.getDefinition().getName()));
                attributes.addElement(jsonAttr);
            }
            jsonMP.addPair(new JSONString("attributes"), attributes);
            mps.addElement(jsonMP);
        }
        for (Relationship rel : currentCongress.getRelationships()) {
            JSONObject jsonRelation = new JSONObject();
            MP m1 = (MP) rel.getNode();
            MP m2 = (MP) rel.getNeighbor(m1);
            JSONObject jsonMP1 = new JSONObject();
            jsonMP1.addPair(new JSONString("state"), new JSONString(m1.getState().toString()));
            jsonMP1.addPair(new JSONString("district"), new JSONString(""+m1.getDistrict()));
            JSONObject jsonMP2 = new JSONObject();
            jsonMP2.addPair(new JSONString("state"), new JSONString(m2.getState().toString()));
            jsonMP2.addPair(new JSONString("district"), new JSONString(""+m2.getDistrict()));

            jsonRelation.addPair(new JSONString("m1"), jsonMP1);
            jsonRelation.addPair(new JSONString("m2"), jsonMP2);
            jsonRelation.addPair(new JSONString("weight"), new JSONString(""+rel.getWeight()));
            jsonRelation.addPair(new JSONString("valid"), new JSONString(""+rel.isValid()));
            relations.addElement(jsonRelation);
        }
        for (AttrDefinition def : currentCongress.getAttrDef()) {
            JSONObject jo = new JSONObject();
            jo.addPair(new JSONString("name"), new JSONString(def.getName()));
            jo.addPair(new JSONString("importance"), new JSONString(Integer.toString(def.getImportance())));
            definitions.addElement(jo);
        }
        return dataController.saveCongress(name, congress.stringify());
    }


    public String loadCongressAsCurrent(String name) {
        if (name == null) {
            return exceptionMaker(new IllegalArgumentException("name of file cannto be null"));
        }
        String congress = dataController.loadCongress(name);
        if (isException(congress)) {
            return congress;
        }
        JSONizer json = new JSONizer();
        currentCongressName = name;
        JSONObject jsonCongress = json.StringToJSON(congress);
        Congress newCongress = new Congress();

        for (JSON jsonDef : ((JSONArray)jsonCongress.getJSONByKey("attributeDefinitions")).getArray()) {
            JSONObject jo = (JSONObject)jsonDef;
            String defName = ((JSONString)jo.getJSONByKey("name")).getValue();
            int importance = Integer.valueOf(((JSONString) jo.getJSONByKey("importance")).getValue());
            AttrDefinition attrDef = new AttrDefinition(defName, importance);
            newCongress.addAttrDef(attrDef);
        }
        for (JSON jsonMP : ((JSONArray)jsonCongress.getJSONByKey("mps")).getArray()) {
            JSONObject jo = (JSONObject)jsonMP;
            String fullname = ((JSONString)jo.getJSONByKey("fullname")).getValue();
            State state = State.valueOf(((JSONString) jo.getJSONByKey("state")).getValue());
            int district = Integer.valueOf(((JSONString) jo.getJSONByKey("district")).getValue());
            MP mp = new MP(fullname, state, district);
            JSONArray attributes = ((JSONArray) jo.getJSONByKey("attributes"));
            for (JSON attr : attributes.getArray()) {
                JSONObject objAttr = (JSONObject) attr;
                AttrDefinition def = newCongress.getAttrDef(((JSONString)objAttr.getJSONByKey("definitionName")).getValue());
                Attribute mpAttr = new Attribute(def, ((JSONString)objAttr.getJSONByKey("value")).getValue());
                mp.addAttribute(mpAttr);
            }
            newCongress.addNode(mp);
        }
        for (JSON jsonRel : ((JSONArray)jsonCongress.getJSONByKey("relations")).getArray()) {
            JSONObject jo = (JSONObject)jsonRel;
            JSONObject jsonMP1 = (JSONObject)jo.getJSONByKey("m1");
            JSONObject jsonMP2 = (JSONObject)jo.getJSONByKey("m2");
            State state1 = State.valueOf(((JSONString)jsonMP1.getJSONByKey("state")).getValue());
            int dist1 = Integer.valueOf(((JSONString)jsonMP1.getJSONByKey("district")).getValue());
            State state2 = State.valueOf(((JSONString)jsonMP2.getJSONByKey("state")).getValue());
            int dist2 = Integer.valueOf(((JSONString)jsonMP2.getJSONByKey("district")).getValue());
            MP m1 = newCongress.getMP(state1, dist1);
            MP m2 = newCongress.getMP(state2, dist2);
            int weight = Double.valueOf(((JSONString) jo.getJSONByKey("weight")).getValue()).intValue();
            boolean valid = Boolean.valueOf(((JSONString) jo.getJSONByKey("valid")).getValue());
            Relationship rel = new Relationship(m1, m2, weight);
            rel.setValidity(valid);
            newCongress.addEdge(rel);
        }
        currentCongress = newCongress;
        return congress;
    }


    public String loadAllCongressesNames() {
        return dataController.loadAllCongressesNames();
    }


    public String saveMainPartition(String partitionName) {
        if (currentCongressName == null) {
            return "{\"Exception\":{\"Name\":\"IllegalArgumentException\",\"Message\":\"Current congress is not loaded\"}}";
        }
        JSONObject jsonPartition = new JSONObject();
        JSONArray communities = new JSONArray();
        for (Set<MP> community : mainPartition) {
            JSONArray jsonCommunity = new JSONArray();
            for (MP mp : community) {
                JSONObject jsonMP = new JSONObject();
                jsonMP.addPair(new JSONString("state"), new JSONString(mp.getState().toString()));
                jsonMP.addPair(new JSONString("district"), new JSONString(""+mp.getDistrict()));
                jsonCommunity.addElement(jsonMP);
            }
            communities.addElement(jsonCommunity);
        }
        jsonPartition.addPair("communities", communities);
        return dataController.savePartition(currentCongressName, partitionName, jsonPartition.stringify());
    }


    public String loadPartitionInto(String partitionName, String into) {
        if (currentCongressName == null) {
            return "{\"Exception\":{\"Name\":\"IllegalArgumentException\",\"Message\":\"Current congress is not saved\"}}";
        }
        JSONizer json = new JSONizer();
        String respond = dataController.loadPartition(currentCongressName, partitionName);
        List<Set<MP>> newPartition = new ArrayList<>();
        JSONObject jsonPartition = json.StringToJSON(respond);
        for (JSON jsonCom : ((JSONArray)jsonPartition.getJSONByKey("communities")).getArray()) {
            Set<MP> community = new HashSet<>();
            for (JSON j : ((JSONArray)jsonCom).getArray()) {
                JSONObject jsonMP = (JSONObject) j;
                State state = State.valueOf(((JSONString)jsonMP.getJSONByKey("state")).getValue());
                int dist = Integer.valueOf(((JSONString)jsonMP.getJSONByKey("district")).getValue());
                MP mp = currentCongress.getMP(state, dist);
                community.add(mp);
            }
            newPartition.add(community);
        }

        switch (into) {
            case "mainPartition" :
                mainPartition = newPartition;
                break;
            case "partition1" :
                partition1 = newPartition;
                break;
            case "partition2" :
                partition2 = newPartition;
                break;
            default:
                throw new IllegalArgumentException("unknown partition");
        }
        return respond;
    }


    public String loadAllPartitionsInCurrentCongress() {
        if (currentCongressName == null) {
            return "{\"Exception\":{\"Name\":\"IllegalArgumentException\",\"Message\":\"Current congress is not saved\"}}";
        }
        return dataController.loadAllPartitionsOfCongress(currentCongressName);
    }


    public String loadAllPartitionNamesInCurrentCongress() {
        if (currentCongressName == null) {
            return "{\"Exception\":{\"Name\":\"IllegalArgumentException\",\"Message\":\"Current congress is not saved\"}}";
        }
        return dataController.loadAllPartitionNamesOfCongress(currentCongressName);
    }


    public void computePartition(String algorithm, String argument) {
        computeRelationships();
        Algorithm alg;
        switch (algorithm) {
            case "N Clique Percolation":
                alg = new NCQAlgorithm(currentCongress);
                break;
            case "Four Clique Percolation":
                alg = new FCQAlgorithm(currentCongress, Double.valueOf(argument));
                break;
            case "Louvian":
                alg = new LouvainAlgorithm(currentCongress);
                break;
            case "Newmann Girvan":
                alg = new NGAlgorithm(currentCongress, Integer.valueOf(argument));
                break;
            default:
                throw new IllegalArgumentException("Incorrect name of algorithm");
        }
        List<Set<MP>> partition = new ArrayList<>();
        for (Set<Node> set : alg.calculate()) {
            Set<MP> mpSet = new HashSet<>();
            for (Node n : set) {
                mpSet.add((MP) n);
            }
            partition.add(mpSet);
        }
        mainPartition = partition;
    }


    public String getCommunityIDs(String partition) {
        List<Set<MP>> part;
        switch (partition) {
            case "mainPartition" :
                part = mainPartition;
                break;
            case "partition1" :
                part = partition1;
                break;
            case "partition2" :
                part = partition2;
                break;
            default:
                throw new IllegalArgumentException("unknown partition");
        }
        JSONArray ids = new JSONArray();
        for (Set comm : part) {
            int id = part.indexOf(comm);
            ids.addElement(new JSONString("" + id));
        }
        JSONObject jo = new JSONObject();
        jo.addPair("ids", ids);
        return (new JSONizer()).JSONtoString(jo);
    }


    public void addMPToCommunity(String communityID, State state, int district) {
        MP m = currentCongress.getMP(state, district);
        for (int i = 0; i < mainPartition.size(); i++) {
            Set<MP> comm = mainPartition.get(i);
            if (i == Integer.valueOf(communityID)) {
                if (comm.contains(m)) return;
                comm.add(m);
            }
            //else if (comm.contains(m)) comm.remove(m);
        }
    }


    public void removeMPFromCommunity (String communityID, State state, int district) {
        MP m = currentCongress.getMP(state, district);
        for (int i = 0; i < mainPartition.size(); i++) {
            if (i == Integer.valueOf(communityID)) {
                mainPartition.get(i).remove(m);
                return;
            }
        }
    }


    public void addNewCommunity () {
        Set<MP> newComm = new HashSet<>();
        mainPartition.add(mainPartition.size(), newComm);
    }


    public void removeCommunity (String communityID) {
        for (int i = 0; i < mainPartition.size(); i++) {
            if (i == Integer.valueOf(communityID)) {
                mainPartition.remove(mainPartition.get(i));
                return;
            }
        }
    }


    public String compare2partitions() {
        (new WeightAlgorithm(currentCongress)).computeAllWeights();
        Map<Node, Integer> part1 = new HashMap<>();
        int comm = 0;
        for (Set<MP> c : partition1) {
            for (MP m : c) {
                part1.put(m, comm);
            }
            ++comm;
        }
        Map<Node, Integer> part2 = new HashMap<>();
        comm = 0;
        for (Set<MP> c : partition2) {
            for (MP m : c) {
                part2.put(m, comm);
            }
            ++comm;
        }
        //System.out.println("part1:"+part1);
        //System.out.println("part2:"+part2);
        ComparingAlgorithm ca = new ComparingAlgorithm(currentCongress, part1, part2);
        JSONObject jInfo = new JSONObject();
        JSONString jBest = new JSONString();
        if (ca.best == part1) jBest.setValue("Partition1");
        else jBest.setValue("Partition2");
        jInfo.addPair("Best partition", jBest);
        JSONString jModB = new JSONString(String.valueOf(ca.bestModularity()));
        JSONString jModO = new JSONString(String.valueOf(ca.otherModularity()));
        jInfo.addPair("Best modularity", jModB);
        jInfo.addPair("Other modularity", jModO);
        JSONString jPA = new JSONString(String.valueOf(ca.percentBetter()));
        jInfo.addPair("Percentage of accuracy", jPA);
        return jInfo.stringify();
    }


    public String getCurrentCongressName() {
        if (currentCongressName == null) {
            return "";
        }
        return currentCongressName;
    }


    public void computeRelationships() {
        Congress newCongress = new Congress();
        for (MP m : currentCongress.getMPs()) {
            newCongress.addNode(m);
        }
        for (AttrDefinition ad : currentCongress.getAttrDef()) {
            newCongress.addAttrDef(ad);
        }
        WeightAlgorithm wa = new WeightAlgorithm(newCongress);
        wa.computeAllWeights();
        currentCongress = newCongress;
    }


}
