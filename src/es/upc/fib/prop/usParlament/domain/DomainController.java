package es.upc.fib.prop.usParlament.domain;

import es.upc.fib.prop.shared13.Node;
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
    private ArrayList<Set<Node>> mainPartition;
    private ArrayList<Set<Node>> secondaryPartition;


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
        //JSONString ret = new JSONString(jList.toString());
        //return ret;
        return jList.stringify();
    }
    

    public String getMPList() {
        JSONObject ret = new JSONObject();
        JSONString n = new JSONString("MPList");
        JSONArray mps = new JSONArray();
        for (MP mp : currentCongress.getMPs()) {
            JSONObject dip = new JSONObject();
            JSONString js = new JSONString("MP");
            JSONObject camps = new JSONObject();
            JSONArray ja = new JSONArray();
            JSONString attrib = new JSONString(mp.getState().toString());
            camps.addPair(new JSONString("State"), attrib);
            ja.addElement(camps);
            attrib = new JSONString(Integer.toString(mp.getDistrict()));
            camps.addPair(new JSONString("District"), attrib);
            ja.addElement(camps);
            attrib = new JSONString(mp.getFullname());
            camps.addPair(new JSONString("Name"), attrib);
            ja.addElement(camps);
            for (Attribute a : mp.getAttributes()) {
                attrib = new JSONString(a.getValue().toString());
                camps.addPair(new JSONString(a.getDefinition().getName()), attrib);
                ja.addElement(camps);
            }
            dip.addPair(js, ja);
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
        //JSONString ret = new JSONString(jInfo.toString());
        //return ret;
        return jInfo.stringify();
    }

    public JSONString getMainCommunityNumber() {

        return null;
    }

    public String getAttrDefs() {
        JSONObject defs = new JSONObject();
        JSONString js = new JSONString("Attribute Definitions");
        JSONArray ja = new JSONArray();
        for (AttrDefinition def : currentCongress.getAttrDef()) {
            JSONObject jo = new JSONObject();
            JSONString jsa = new JSONString("attrdef");
            JSONArray jaa = new JSONArray();
            jo.addPair(new JSONString("AttrDefName"), new JSONString(def.getName()));
            jaa.addElement(jo);
            jo.addPair(new JSONString("AttrDefImportance"), new JSONString(Integer.toString(def.getImportance())));
            jaa.addElement(jo);
            jo.addPair(jsa, jaa);
            ja.addElement(jo);
        }
        defs.addPair(js, ja);
        return defs.stringify();
    }
}
