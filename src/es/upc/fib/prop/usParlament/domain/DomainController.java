package es.upc.fib.prop.usParlament.domain;

import es.upc.fib.prop.usParlament.misc.JSONArray;
import es.upc.fib.prop.usParlament.misc.JSONObject;
import es.upc.fib.prop.usParlament.misc.JSONString;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by miquel on 16/05/15.
 */
public class DomainController
{
    /*
        Needs to contain:
        - 1 Graph (congress)
        - 2 Partition (one current and one for comparing purposes)
        Needs to give the ability to consult the graphs

     */
    private Congress currentCongress;
    //private ArrayList<Set<Node>> mainPartition;  //TODO
    //private ArrayList<Set<Node>> secondaryPartition;
    private ArrayList<Set<MP>> mainPartition;
    private ArrayList<Set<MP>> secondaryPartition;


    /**
     * @return It returns a JSON String with the State and District values for each MP at the current congress.
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
        JSONObject jInfo = new JSONObject();
        JSONString key = new JSONString("State");
        JSONString value = new JSONString(state.toString());
        jInfo.addPair(key, value);
        key.setValue("District");
        value.setValue(String.valueOf(district));
        jInfo.addPair(key, value);
        key.setValue("Name");
        value.setValue(currentCongress.getMP(state, district).getFullname());
        jInfo.addPair(key, value);
        JSONArray atts = new JSONArray();
        for (Attribute a : currentCongress.getMP(state, district).getAttributes()) {
            JSONObject jAtt = new JSONObject();
            key.setValue(a.getDefinition().getName());
            value.setValue(a.getValue().toString());
            jAtt.addPair(key, value);
            atts.addElement(jAtt);
        }
        key.setValue("Attributes");
        value.setValue(atts.toString());
        jInfo.addPair(key, value);
        return jInfo.stringify();
    }

    /**
     * @return Returns the main number of communities.
     */
    public String getMainCommunityNumber() { return String.valueOf(mainPartition.size()); }

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

    public String getMPsMainCommunities(String comnumber) {
        JSONObject mps = new JSONObject();
        JSONString js = new JSONString("Main Parition Community numer "+comnumber);
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

    public String getSecCommunityNumber() {
        return Integer.toString(secondaryPartition.size());
    }

    public String getMPsSecCommunities(String comnumber) {
        JSONObject mps = new JSONObject();
        JSONString js = new JSONString("Main Parition Community numer "+comnumber);
        JSONArray ja = new JSONArray();
        for (MP mp : secondaryPartition.get(Integer.parseInt(comnumber))) {
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
        JSONString jState = new JSONString(jMP.getJSONByKey(key).stringify());
        key.setValue("District");
        JSONString jDistr = new JSONString(jMP.getJSONByKey(key).stringify());
        key.setValue("Name");
        JSONString jName = new JSONString(jMP.getJSONByKey(key).stringify());
        MP m = new MP(jName.stringify(), State.valueOf(jState.stringify()), Integer.valueOf(jDistr.stringify()));
        currentCongress.addNode(m);
    }

    /**
     * @pre jMP belongs to the current congress.
     * @post jMP doesn't belong to the current congress.
     * @param jMP JSON Object defining the MP to delete.
     */
    public void deleteMP(JSONObject jMP) {
        JSONString key = new JSONString("State");
        JSONString jState = new JSONString(jMP.getJSONByKey(key).stringify());
        key.setValue("District");
        JSONString jDistr = new JSONString(jMP.getJSONByKey(key).stringify());
        key.setValue("Name");
        MP m = currentCongress.getMP(State.valueOf(jState.stringify()), Integer.valueOf(jDistr.stringify()));
        currentCongress.removeNode(m);
    }

    /**
     * Adds the attribute to the MP specified. If the MP already has that attribute it is modified instead of added.
     * @param jAttr JSON Object defining the relatives MP and attribute.
     */
    public void addOrModifyAttribute(JSONObject jAttr) {
        JSONString key = new JSONString("State");
        JSONString jState = new JSONString(jAttr.getJSONByKey(key).stringify());
        key.setValue("District");
        JSONString jDistr = new JSONString(jAttr.getJSONByKey(key).stringify());
        MP m = currentCongress.getMP(State.valueOf(jState.stringify()), Integer.valueOf(jDistr.stringify()));
        key.setValue("AttrDef");
        JSONString jAttrD = new JSONString(jAttr.getJSONByKey(key).stringify());
        key.setValue("AttrValue");
        JSONString jAttrV = new JSONString(jAttr.getJSONByKey(key).stringify());
        Attribute a = new Attribute(currentCongress.getAttrDef(jAttrD.stringify()), jAttrV);
        m.addAttribute(a);
    }

    /**
     * @pre The specified MP has a value defined for the specified attribute.
     * @post The specified MP has not any value defined for the (@pre) specified attribute.
     * @param jAttr JSON Object defining the relatives MP and attribute.
     */
    public void deleteAttribute(JSONObject jAttr) {
        JSONString key = new JSONString("State");
        JSONString jState = new JSONString(jAttr.getJSONByKey(key).stringify());
        key.setValue("District");
        JSONString jDistr = new JSONString(jAttr.getJSONByKey(key).stringify());
        MP m = currentCongress.getMP(State.valueOf(jState.stringify()), Integer.valueOf(jDistr.stringify()));
        key.setValue("AttrDef");
        JSONString jAttrD = new JSONString(jAttr.getJSONByKey(key).stringify());
        m.removeAttribute(currentCongress.getAttrDef(jAttrD.stringify()));
    }

    /**
     * Deletes the AttrDefinition jAttrDef from the current congress.
     * @param jAttrDef JSON Object defining the jAttrDef we want to delete.
     */
    public void deleteAttrDef(JSONObject jAttrDef) {
        JSONString key = new JSONString("AttrDefName");
        JSONString jAttrD = new JSONString(jAttrDef.getJSONByKey(key).stringify());
        currentCongress.removeAttrDef(currentCongress.getAttrDef(jAttrD.stringify()));
    }
}
