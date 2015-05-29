package es.upc.fib.prop.usParlament.domain;

import es.upc.fib.prop.usParlament.data.DataController;
import es.upc.fib.prop.usParlament.data.DataControllerImpl;
import es.upc.fib.prop.usParlament.misc.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * Created by miquel on 16/05/15.
 *
 * All the TEST INSTRUCTIONS (i.e. printing staff) are commented by //.
 */

//TODO: not finished
public class DomainController
{
    /*
        Needs to contain:
        - 1 Graph (congress)
        - 2 Partition (one current and one for comparing purposes)
        Needs to give the ability to consult the graphs

     */
    private Congress currentCongress;
    private ArrayList<Set<MP>> mainPartition;
    private ArrayList<Set<MP>> partition1;
    private ArrayList<Set<MP>> partition2;
    private DataController dataController;

    
    public DomainController()
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

    public Congress getCurrentCongress() {
        return currentCongress;
    }

    /**
     * @return It returns an String with the State and District values for each MP at the current congress.
     */
    public String getShortMPList() {
        JSONObject jList = new JSONObject();
        JSONArray a = new JSONArray();
        for (MP mp : currentCongress.getMPs()) {
            JSONObject jMP = new JSONObject();
            JSONString key = new JSONString("State");
            JSONString value = new JSONString(mp.getState().toString());
            jMP.addPair(key, value);
            key.setValue("District");
            value.setValue(String.valueOf(mp.getDistrict()));
            jMP.addPair(key, value);
            a.addElement(jMP);
        }
        JSONString key = new JSONString("MPList");
        JSONString value = new JSONString(a.toString());
        jList.addPair(key, value);
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
        mainPartition = new ArrayList<>();
        partition1 = new ArrayList<>();
        partition2 = new ArrayList<>();
    }

    public boolean existsAttrDef(String name)
    {
        return currentCongress.hasAttrDef(new AttrDefinition(name,1));
    }

    /**
     * @return Returns the current partition number of communities.
     */
    public String getMainPartitionNumber() { return String.valueOf(mainPartition.size()); }

    public String getMainPartitionCommunities() {
        JSONObject jPart = new JSONObject();
        for (Set<MP> c : mainPartition) {

        }
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
    public String getP1CommunityNumber() { return String.valueOf(partition1.size()); }

    public String getMPsPartition1 (String comnumber) {
        JSONObject mps = new JSONObject();
        JSONString js = new JSONString("Parition1 Community numer " + comnumber);
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

    public String getMPsPartiton2(String comnumber) {
        JSONObject mps = new JSONObject();
        JSONString js = new JSONString("Parition 2 Community numer "+comnumber);
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

    public void deleteMP(State state, int district)
    {
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
            currentCongress.getAttrDef(jAttrD.getValue()).setImportance(Integer.valueOf(jImp.getValue()));
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
    public String loadCongress(String name) {
        JSONizer json = new JSONizer();
        String congress = dataController.loadCongress(name);
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
     * @param congressName  unique identificator of congress. It has to already exists in persistent memory.
     * @param partitionName  unique identificator in congressName scope.
     * @return  Exception string If there is exception "{}" string otherwise.
     */
    public String saveCurrentPartition(String congressName, String partitionName) {
        JSONObject jsonPartition = new JSONObject();
        JSONArray communities = new JSONArray();
        for (Set<MP> community : currentPartition) {
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
        return dataController.savePartition(congressName, partitionName, jsonPartition.stringify());
    }

    /**
     * load saved partition from persistent memory as current partition.
     * @param congressName  unique identificator of congress.
     * @param partitionName  unique identificator in congressName scope.
     * @return JSON representation of partition.
     */
    public String loadPartitionAsCurrent(String congressName, String partitionName) {
        JSONizer json = new JSONizer();
        String respond = dataController.loadPartition(congressName, partitionName);
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

        currentPartition = newPartition;
        return respond;
    }

    /**
     * load all saved partitions of congress.
     * @param congressName  unique identificator of congress.
     * @return  JSON representation of array of partitions.
     */
    public String loadAllPartitionsOfCongress(String congressName) {
        return dataController.loadAllPartitionsOfCongress(congressName);
    }

    public List<Set<MP>> getCurrentPartition() {
        return currentPartition;
    }
}
