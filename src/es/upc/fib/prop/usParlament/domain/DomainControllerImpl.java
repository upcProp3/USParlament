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
    public void setDataController(DataController dataController) {
        this.dataController = dataController;
    }

    protected Congress getCurrentCongress() {
        return currentCongress;
    }

    public void setCurrentToPartition1 () { partition1 = mainPartition; }
    public void setCurrentToPartition2 () { partition2 = mainPartition; }


    /**
     * @return It returns an String with the State and District values for each MP at the current congress.
     */
    public String getShortMPList() {
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

    /**
     * @return It returns an String with the State, District and attributes values for each MP at the current congress.
     */
    public String getMPList() {
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

    /**
     *
     * @param state State of the MP
     * @param district District of the MP
     * @return Returns all the saved information about the MP (state,district).
     */
    public String getMPInfo(State state, int district) {
        MP mp = currentCongress.getMP(state,district);
        JSONObject mi = new JSONObject();
        mi.addPair(new JSONString("State"),new JSONString(mp.getState().toString()));
        mi.addPair(new JSONString("District"),new JSONString(Integer.toString(mp.getDistrict())));

        mi.addPair(new JSONString("Name"), new JSONString(mp.getFullname()));

        JSONArray ja = new JSONArray();
        for(Attribute a:mp.getAttributes()){
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

    /**
     * @return Returns an String with all the information about the AttrDefinitions defined in the current congress.
     */
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

    public void newCongress()
    {
        currentCongress = new Congress();
        currentCongressName = null;
        mainPartition = new ArrayList<>();
        partition1 = new ArrayList<>();
        partition2 = new ArrayList<>();
    }

    public boolean existsAttrDef(String name)
    {
        return currentCongress.hasAttrDef(new AttrDefinition(name, 1));
    }

    /**
     * @return Returns the current partition number of communities.
     */
    public String getMainPartitionSize() {
        return String.valueOf(mainPartition.size()); }

    public String getMainPartitionCommunities() {
        JSONObject jPart = new JSONObject();
        for (Set<MP> c : mainPartition) {

        }
        return null;
    }

    /**
     * @param comnumber
     * @return
     */
    public String getMPsMainPartition(String comnumber) {
        JSONObject mps = new JSONObject();
        JSONString js = new JSONString("Current partition Community numer " + comnumber);
        JSONArray ja = new JSONArray();
        for (MP mp : mainPartition.get(Integer.parseInt(comnumber))) {
            JSONObject jo = new JSONObject();
            jo.addPair(new JSONString("State"), new JSONString(mp.getState().toString()));
            jo.addPair(new JSONString("District"), new JSONString(Integer.toString(mp.getDistrict())));
            ja.addElement(jo);
        }
        mps.addPair(js, ja);
        return mps.stringify();
    }

    /**
     * @return Returns the partition 1 number of communities.
     */
    public String getP1CommunityNumber() {
        return String.valueOf(partition1.size()); }

    public String getMPsPartition1 (String comnumber) {
        JSONObject mps = new JSONObject();
        JSONString js = new JSONString("Partition1 Community number " + comnumber);
        JSONArray ja = new JSONArray();
        for (MP mp : partition1.get(Integer.parseInt(comnumber))) {
            JSONObject jo = new JSONObject();
            jo.addPair(new JSONString("State"), new JSONString(mp.getState().toString()));
            jo.addPair(new JSONString("District"), new JSONString(Integer.toString(mp.getDistrict())));
            ja.addElement(jo);
        }
        mps.addPair(js, ja);
        return mps.stringify();
    }

    /**
     * @return Returns the partition 2 number of communities.
     */
    public String getP2CommunityNumber() {
        return Integer.toString(partition2.size());
    }

    public String getMPsPartition2(String comnumber) {
        JSONObject mps = new JSONObject();
        JSONString js = new JSONString("Partition2 Community number "+comnumber);
        JSONArray ja = new JSONArray();
        for (MP mp : partition2.get(Integer.parseInt(comnumber))) {
            JSONObject jo = new JSONObject();
            jo.addPair(new JSONString("State"), new JSONString(mp.getState().toString()));
            jo.addPair(new JSONString("District"), new JSONString(Integer.toString(mp.getDistrict())));
            ja.addElement(jo);
        }
        mps.addPair(js, ja);
        return mps.stringify();
    }

    /**
     * @pre jMP doesn't belong to the current congress.
     * @post jMP belongs to the current congress.
     * @param jMP JSON Object defining the new MP.
     */
    public void addMP(JSONObject jMP) {
        JSONString key = new JSONString("State");
        JSONString jState = new JSONString(((JSONString)jMP.getJSONByKey(key)).getValue());
        key.setValue("District");
        JSONString jDistr = new JSONString(((JSONString)jMP.getJSONByKey(key)).getValue());
        key.setValue("Name");
        JSONString jName = new JSONString(((JSONString)jMP.getJSONByKey(key)).getValue());
        MP m = new MP(jName.getValue(), State.valueOf(jState.getValue()), Integer.valueOf(jDistr.getValue()));
        currentCongress.addNode(m);
    }

    public void deleteMP(State state, int district) {
        currentCongress.removeNode(new MP("INVALID_VALUE", state, district));
    }

    /**
     * @pre jMP belongs to the current congress.
     * @post jMP doesn't belong to the current congress.
     * @param jMP JSON Object defining the MP to delete.
     */
    public void deleteMP(JSONObject jMP) {
        JSONString key = new JSONString("State");
        JSONString jState = new JSONString(((JSONString)jMP.getJSONByKey(key)).getValue());
        key.setValue("District");
        JSONString jDistr = new JSONString(((JSONString)jMP.getJSONByKey(key)).getValue());
        key.setValue("Name");
        JSONString jName = new JSONString(((JSONString)jMP.getJSONByKey(key)).getValue());
        MP m = currentCongress.getMP(State.valueOf(jState.getValue()), Integer.valueOf(jDistr.getValue()));
        currentCongress.removeNode(m);
    }

    /**
     * Adds the attribute to the MP specified. If the MP already has that attribute it is modified instead of added.
     * @param jAttr JSON Object defining the relatives MP and attribute.
     */
    public void addOrModifyAttribute(JSONObject jAttr) {
        JSONString key = new JSONString("State");
        JSONString jState = new JSONString(((JSONString)jAttr.getJSONByKey(key)).getValue());
        key.setValue("District");
        JSONString jDistr = new JSONString(((JSONString)jAttr.getJSONByKey(key)).getValue());
        MP m = currentCongress.getMP(State.valueOf(jState.getValue()), Integer.valueOf(jDistr.getValue()));
        key.setValue("AttrDef");
        JSONString jAttrD = new JSONString(((JSONString)jAttr.getJSONByKey(key)).getValue());
        key.setValue("AttrValue");
        JSONString jAttrV = new JSONString(((JSONString)jAttr.getJSONByKey(key)).getValue());
        Attribute a = new Attribute(currentCongress.getAttrDef(jAttrD.getValue()), jAttrV.getValue());
        m.addAttribute(a);
    }




    public void addOrModifyAttribute(JSONObject jmp,JSONArray jattrs)
    {
        Map<String,String> mpmss = jmp.basicJSONObjectGetInfo();
        MP mp = currentCongress.getMP(State.valueOf(mpmss.get("State")),Integer.parseInt(mpmss.get("District")));

        for(JSON j:jattrs.getArray()){
            Map<String,String> att = ((JSONObject)j).basicJSONObjectGetInfo();

            AttrDefinition atd = currentCongress.getAttrDef(att.get("AttrDefName"));
            if(atd == null) throw new IllegalStateException("NO EXISTEIX LATRIBUT");

            String value = att.get("AttrValue");
            Attribute a = new Attribute(atd,value);
            if(mp.hasAttribute(atd)) mp.removeAttribute(atd);
            mp.addAttribute(new Attribute(atd,value));

        }

    }

    public void cleanCommunityManager() {
        mainPartition = new ArrayList<>();
    }
    public void cleanCompareManager() {
        partition1 = new ArrayList<>();
        partition2 = new ArrayList<>();
    }

    public void deleteAttribute(JSONObject jmp,JSONObject jattr)
    {
        Map<String,String> mpmss = jmp.basicJSONObjectGetInfo();
        MP mp = currentCongress.getMP(State.valueOf(mpmss.get("State")),Integer.parseInt(mpmss.get("District")));

        Map<String,String> att = jattr.basicJSONObjectGetInfo();

        AttrDefinition atd = currentCongress.getAttrDef(att.get("AttrDefName"));

        if(atd == null) throw new IllegalStateException("NO EXISTEIX LATRIBUT");
        System.out.println(mp);
        /*if(mp.hasAttribute(atd))*/ mp.removeAttribute(atd);
        System.out.println(mp);
    }


    /**
     * @pre The specified MP has a value defined for the specified attribute.
     * @post The specified MP has not any value defined for the (@pre) specified attribute.
     * @param jAttr JSON Object defining the relatives MP and attribute.
     */
    public void deleteAttribute(JSONObject jAttr) {
        JSONString key = new JSONString("State");
        JSONString jState = new JSONString(((JSONString)jAttr.getJSONByKey(key)).getValue());
        key.setValue("District");
        JSONString jDistr = new JSONString(((JSONString)jAttr.getJSONByKey(key)).getValue());
        MP m = currentCongress.getMP(State.valueOf(jState.getValue()), Integer.valueOf(jDistr.getValue()));
        key.setValue("AttrDef");
        JSONString jAttrD = new JSONString(((JSONString)jAttr.getJSONByKey(key)).getValue());
        m.removeAttribute(currentCongress.getAttrDef(jAttrD.getValue()));
    }

    /**
     * @pre true.
     * @post The AttrDefinition defined by jAttrDef is added/modified to/from the current congress.
     * @param jAttrDef JSON Object defining the relative AttrDefinition.
     */
    public void addOrModifyAttrDef(JSONObject jAttrDef) {
        JSONString key = new JSONString("AttrDefName");
        JSONString jAttrD = new JSONString(((JSONString)jAttrDef.getJSONByKey(key)).getValue());
        key.setValue("Importance");
        JSONString jImp = new JSONString(((JSONString)jAttrDef.getJSONByKey(key)).getValue());
        String imp = jImp.getValue();
        int importancia = -1;
        if(imp == "None") importancia = 0;
        if(imp == "Low") importancia = 1;
        else if(imp == "Medium") importancia = 4;
        else if(imp == "High") importancia = 16;

        if(importancia == -1) throw new IllegalStateException("IMPORTANCIA NO RECONEGUDA");

        AttrDefinition ad = new AttrDefinition(jAttrD.getValue(), importancia);
        if (currentCongress.hasAttrDef(ad)) {
            currentCongress.getAttrDef(jAttrD.getValue()).setImportance(importancia);
        } else {
            currentCongress.addAttrDef(ad);
        }
    }

    /**
     * Deletes the AttrDefinition jAttrDef from the current congress.
     * @param jAttrDef JSON Object defining the jAttrDef we want to delete.
     */
    public void deleteAttrDef(JSONObject jAttrDef) {
        JSONString key = new JSONString("AttrDefName");
        JSONString jAttrD = new JSONString(((JSONString)jAttrDef.getJSONByKey(key)).getValue());
        currentCongress.removeAttrDef(currentCongress.getAttrDef(jAttrD.getValue()));
    }




    /**
     * save congress into persistent memory. If cogress with the same identificator already exists it will be rewritten.
     * @param name  unique identificator of congress
     * @return  Exception string If there is exception "{}" string otherwise.
     */
    public String saveCurrentCongress(String name) {
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

    /**
     * load congress from persistent memory
     * @param name  identificator of congress
     * @return  JSON representation of congress
     */
    public String loadCongressAsCurrent(String name) {
        JSONizer json = new JSONizer();
        String congress = dataController.loadCongress(name);
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

    /**
     * load names of all saved congresses
     * @return JSON list of all congresses
     */
    public String loadAllCongressesNames() {
        return dataController.loadAllCongressesNames();
    }



    /**
     * save current partition into persistent memory. If partition with same identificators already exists it will be rewritten.
     * @param partitionName  unique identificator in congressName scope.
     * @return  Exception string If there is exception "{}" string otherwise.
     */
    public String saveCurrentPartition(String partitionName) {
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

    /**
     * load saved partition from persistent memory as current partition.
     * @param partitionName  unique identificator in congressName scope.
     * @param as  name of field where to save loaded partition (mainPartion, partition1, partition2)
     * @return JSON representation of partition or exception.
     */
    public String loadPartitionAs(String partitionName, String as) {
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

        switch (as) {
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

    /**
     * load all saved partitions of current congress.
     * @return  JSON representation of array of partitions.
     */
    public String loadAllPartitionsInCurrentCongress() {
        if (currentCongressName == null) {
            return "{\"Exception\":{\"Name\":\"IllegalArgumentException\",\"Message\":\"Current congress is not saved\"}}";
        }
        return dataController.loadAllPartitionsOfCongress(currentCongressName);
    }

    /**
     * load all saved partition names of current congress.
     * @return  JSON representation of array of names.
     */
    public String loadAllPartitionNamesInCurrentCongress() {
        if (currentCongressName == null) {
            return "{\"Exception\":{\"Name\":\"IllegalArgumentException\",\"Message\":\"Current congress is not saved\"}}";
        }
        return dataController.loadAllPartitionNamesOfCongress(currentCongressName);
    }

    protected List<Set<MP>> getCurrentPartition() {
        return mainPartition;
    }

    /**
     * Compute partitions with given algorithm name (clicques, louvian, newmanngirvan) and save it to the current partition
     * @param algorithm  unique identificator of congress.
     */
    public void computeCommunities(String algorithm, String argument) {
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

    /**
     * Adds the MP (st, distr) to the community (cNumb) of main partition.
     * If the MP(st, distr) is in another community different from (cNumb), the MP is moved.
     * @param cNumb Community identifier number.
     * @param st State of the MP we want to add.
     * @param distr District of the MP we want to add.
     */
    public void addMPToCommunity(Integer cNumb, State st, Integer distr) {
        MP m = currentCongress.getMP(st, distr);
        for (int i = 0; i < mainPartition.size(); i++) {
            Set<MP> comm = mainPartition.get(i);
            if (i == cNumb) {
                if (comm.contains(m)) return;
                comm.add(m);
            }
            //else if (comm.contains(m)) comm.remove(m);
        }
    }

    public void deleteMPFromCommunity (Integer cNumb, State st, Integer distr) {
        MP m = currentCongress.getMP(st, distr);
        for (int i = 0; i < mainPartition.size(); i++) {
            if (i == cNumb) {
                mainPartition.get(i).remove(m);
                return;
            }
        }
    }

    public void addNewCommunity () {
        Set<MP> newComm = new HashSet<>();
        mainPartition.add(mainPartition.size(), newComm);
    }

    public void deleteSelectedCommunity (Integer cNumb) {
        for (int i = 0; i < mainPartition.size(); i++) {
            if (i == cNumb) {
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
