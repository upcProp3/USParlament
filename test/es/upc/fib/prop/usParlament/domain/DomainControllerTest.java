package es.upc.fib.prop.usParlament.domain;

import es.upc.fib.prop.usParlament.data.DataControllerImpl;
import es.upc.fib.prop.usParlament.misc.JSONArray;
import es.upc.fib.prop.usParlament.misc.JSONObject;
import es.upc.fib.prop.usParlament.misc.JSONString;
import es.upc.fib.prop.usParlament.misc.State;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Created by ondrej on 28.5.15.
 */
public class DomainControllerTest {

	private final String PATH = "domainControllerTest";
	private final String[] CONGRESS_NAMES = {"congress0", "congress1", "congress2"};
	DomainControllerImpl manager;

	@Before
	public void setUp() throws Exception {
		manager = new DomainControllerImpl();
		manager.setDataController(new DataControllerImpl(PATH));
	}

	@After
	public void tearDown() throws Exception {
		//delete(new File(PATH));
	}
	private void delete(File f) throws IOException {
		if (!f.exists()) {
			return;
		}
		if (f.isDirectory()) {
			for (File c : f.listFiles())
				delete(c);
		}
		if (!f.delete()) {
			throw new IOException("Failed to delete file: " + f);
		}

	}

    @Test
    public void testGetMPInfo() throws Exception {
        Congress expected = prepareCurrentCongress();
        manager.saveCurrentCongress(CONGRESS_NAMES[0]);
        manager.loadCongressAsCurrent(CONGRESS_NAMES[0]);
        JSONObject jo = new JSONObject();
        jo.addPair(new JSONString("State"),new JSONString("CA"));
        jo.addPair(new JSONString("District"),new JSONString("2"));
        jo.addPair(new JSONString("Name"),new JSONString("Alex"));
        JSONArray ja = new JSONArray();
        JSONObject j = new JSONObject();
        j.addPair(new JSONString("AttrDefName"), new JSONString("religion"));
        j.addPair(new JSONString("AttrValue"),new JSONString("catholicism"));

        ja.addElement(j);
        j = new JSONObject();
        j.addPair(new JSONString("AttrDefName"), new JSONString("sex"));
        j.addPair(new JSONString("AttrValue"),new JSONString("male"));

        ja.addElement(j);
        j = new JSONObject();
        j.addPair(new JSONString("AttrDefName"), new JSONString("party"));
        j.addPair(new JSONString("AttrValue"),new JSONString("democrat"));
        ja.addElement(j);
        jo.addPair(new JSONString("Attributes"),ja);
        String alex = jo.stringify();
        /*String alex = new String("{\"State\":\"CA\",\"District\":\"2\",\"Name\":\"Alex\",\"Attributes\":[" +
                "{\"AttrDefName\":\"sex\",\"AttrValue\":\"male\"},{\"AttrDefName\":\"party\",\"AttrValue\":\"democrat\"}," +
                "{\"AttrDefName\":\"religion\",\"AttrValue\":\"catholicism\"}]}");//*/
        String current = new String(manager.getMPInfo(State.CA, 2));
        System.out.println(current);
        assertEquals(alex, current);
    }
//*/

	@Test
	public void testSaveCurrentCongress() throws Exception {
		Congress expected = prepareCurrentCongress();
		manager.saveCurrentCongress(CONGRESS_NAMES[0]);
		manager.loadCongressAsCurrent(CONGRESS_NAMES[0]);
		Congress current = manager.getCurrentCongress();
		Collections.sort(current.getRelationships());
		assertEquals(expected, current);
	}

	@Test
	public void testLoadCongress() throws Exception {
		Congress expected = prepareCurrentCongress();
		manager.saveCurrentCongress(CONGRESS_NAMES[1]);
		manager.loadCongressAsCurrent(CONGRESS_NAMES[1]);
		Congress current = manager.getCurrentCongress();
		Collections.sort(current.getRelationships());
		assertEquals(expected, current);
	}

	@Test
	public void testLoadAllCongressesNames() throws Exception {
		//throw new UnsupportedOperationException("Not implemented yet");
	}


	private Congress prepareCurrentCongress() {
		Congress congress = manager.getCurrentCongress();
		AttrDefinition sex = new AttrDefinition("sex", 1);
		AttrDefinition party = new AttrDefinition("party", 16);
		AttrDefinition religion = new AttrDefinition("religion", 4);
		congress.addAttrDef(sex);
		congress.addAttrDef(party);
		congress.addAttrDef(religion);
		MP mp1 = new MP("Ondrej", State.AK, 1);
		MP mp2 = new MP("Alex", State.CA, 2);
		MP mp3 = new MP("Aleix", State.CO, 3);
		MP mp4 = new MP("Miquel", State.OH, 1);
		Attribute attr1 = new Attribute(congress.getAttrDef("sex"), "male");
		Attribute attr2 = new Attribute(congress.getAttrDef("party"), "democrat");
		Attribute attr3 = new Attribute(congress.getAttrDef("party"), "republican");
		Attribute attr4 = new Attribute(congress.getAttrDef("religion"), "catholicism");
		Attribute attr5 = new Attribute(congress.getAttrDef("religion"), "islamism");
		Attribute attr6 = new Attribute(congress.getAttrDef("religion"), "atheist");
		mp1.addAttribute(attr1);
		mp2.addAttribute(attr1);
		mp3.addAttribute(attr1);
		mp1.addAttribute(attr2);
		mp2.addAttribute(attr2);
		mp3.addAttribute(attr3);
		mp4.addAttribute(attr3);
		mp2.addAttribute(attr4);
		mp3.addAttribute(attr5);
		mp4.addAttribute(attr6);
		congress.addNode(mp1);
		congress.addNode(mp2);
		congress.addNode(mp3);
		congress.addNode(mp4);
		(new WeightAlgorithm(congress)).computeAllWeights();
		return congress;
	}

	private List<Set<MP>> prepareCurrentPartition() {
		return null;
	}
}