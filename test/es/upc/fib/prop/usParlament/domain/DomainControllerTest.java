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
import java.util.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by ondrej on 1.6.15.
 */
public class DomainControllerTest {

	private final String PATH = "domainControllerTest";
	private final String[] CONGRESS_NAMES = {"congress0", "congress1"};
	DomainControllerImpl controller;

	@Before
	public void setUp() throws Exception {
		controller = new DomainControllerImpl();
		controller.setDataController(new DataControllerImpl(PATH));
	}

	@After
	public void tearDown() throws Exception {
		delete(new File(PATH));
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
	public void testCleanCommunityManager() throws Exception {

	}

	@Test
	public void testCleanCompareManager() throws Exception {

	}

	@Test
	public void testNewCongress() throws Exception {

	}

	@Test
	public void testSaveCurrentCongress() throws Exception {
		Congress expected = prepareCurrentCongress();
		controller.saveCurrentCongress(CONGRESS_NAMES[0]);
		controller.loadCongressAsCurrent(CONGRESS_NAMES[0]);
		Congress current = controller.getCurrentCongress();
		Collections.sort(current.getRelationships());
		assertEquals(expected, current);
	}

	@Test
	public void testLoadCongressAsCurrent() throws Exception {
		Congress expected = prepareCurrentCongress();
		controller.saveCurrentCongress(CONGRESS_NAMES[1]);
		controller.loadCongressAsCurrent(CONGRESS_NAMES[1]);
		Congress current = controller.getCurrentCongress();
		Collections.sort(current.getRelationships());
		assertEquals(expected, current);
	}

	@Test
	public void testLoadAllCongressesNames() throws Exception {

	}

	@Test
	public void testGetCurrentCongressName() throws Exception {

	}

	@Test
	public void testComputeRelationships() throws Exception {

	}

	@Test
	public void testGetMPsShort() throws Exception {

	}

	@Test
	public void testGetMPs() throws Exception {

	}

	@Test
	public void testGetMP() throws Exception {
		prepareCurrentCongress();

		JSONObject jo = new JSONObject();
		jo.addPair(new JSONString("State"),new JSONString("NY"));
		jo.addPair(new JSONString("District"),new JSONString("1"));
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
		j.addPair(new JSONString("AttrDefName"), new JSONString("sport"));
		j.addPair(new JSONString("AttrValue"),new JSONString("football"));
		ja.addElement(j);

		j = new JSONObject();
		j.addPair(new JSONString("AttrDefName"), new JSONString("party"));
		j.addPair(new JSONString("AttrValue"),new JSONString("republican"));
		ja.addElement(j);

		jo.addPair(new JSONString("Attributes"),ja);
		String alex = jo.stringify();

        /*String alex = new String("{\"State\":\"CA\",\"District\":\"2\",\"Name\":\"Alex\",\"Attributes\":[" +
                "{\"AttrDefName\":\"sex\",\"AttrValue\":\"male\"},{\"AttrDefName\":\"party\",\"AttrValue\":\"democrat\"}," +
                "{\"AttrDefName\":\"religion\",\"AttrValue\":\"catholicism\"}]}");//*/
		String current = controller.getMP(State.NY, 1);
		assertEquals(alex, current);
	}

	@Test
	public void testAddMP() throws Exception {

	}

	@Test
	public void testRemoveMP() throws Exception {

	}

	@Test
	public void testRemoveMP1() throws Exception {

	}

	@Test
	public void testGetAttrDefs() throws Exception {

	}

	@Test
	public void testAddOrModifyAttrDef() throws Exception {

	}

	@Test
	public void testDeleteAttrDef() throws Exception {

	}

	@Test
	public void testAddOrModifyAttribute() throws Exception {

	}

	@Test
	public void testAddOrModifyAttributes() throws Exception {

	}

	@Test
	public void testRemoveAttribute() throws Exception {

	}

	@Test
	public void testExistsAttrDef() throws Exception {

	}

	@Test
	public void testSaveMainPartition() throws Exception {

	}

	@Test
	public void testLoadPartitionInto() throws Exception {

	}

	@Test
	public void testLoadAllPartitionsInCurrentCongress() throws Exception {

	}

	@Test
	public void testGetCommunityIDs() throws Exception {

	}

	@Test
	public void testGetMainPartitionSize() throws Exception {

	}

	@Test
	public void testGetMPsInMainPartition() throws Exception {

	}

	@Test
	public void testGetMPsInPartition1() throws Exception {

	}

	@Test
	public void testGetMPsInPartition2() throws Exception {

	}

	@Test
	public void testSetMainToPartition1() throws Exception {

	}

	@Test
	public void testSetMainToPartition2() throws Exception {

	}

	@Test
	public void testCompare2partitions() throws Exception {

	}

	@Test
	public void testLoadAllPartitionNamesInCurrentCongress() throws Exception {

	}

	@Test
	public void testComputePartition() throws Exception {

	}

	@Test
	public void testAddNewCommunity() throws Exception {

	}

	@Test
	public void testRemoveCommunity() throws Exception {

	}

	@Test
	public void testAddMPToCommunity() throws Exception {

	}

	@Test
	public void testRemoveMPFromCommunity() throws Exception {

	}

	private Congress prepareCurrentCongress() {
		Congress congress = controller.getCurrentCongress();

		AttrDefinition sex = new AttrDefinition("sex", 1);
		AttrDefinition sport = new AttrDefinition("sport", 1);
		AttrDefinition religion = new AttrDefinition("religion", 4);
		AttrDefinition party = new AttrDefinition("party", 16);
		congress.addAttrDef(sex);
		congress.addAttrDef(sport);
		congress.addAttrDef(religion);
		congress.addAttrDef(party);

		MP ondrej = new MP("Ondrej", State.CA, 1);
		MP alex = new MP("Alex", State.NY, 1);
		MP aleix = new MP("Aleix", State.WA, 1);
		MP miquel = new MP("Miquel", State.CO, 1);
		MP homer = new MP("Homer", State.CO, 2);
		MP kate = new MP("Kate", State.OH, 2);

		Attribute male = new Attribute(congress.getAttrDef("sex"), "male");
		Attribute female = new Attribute(congress.getAttrDef("sex"), "female");

		Attribute football = new Attribute(congress.getAttrDef("sport"), "football");
		Attribute hockey = new Attribute(congress.getAttrDef("sport"), "hockey");
		Attribute basketball = new Attribute(congress.getAttrDef("sport"), "basketball");

		Attribute catholicism = new Attribute(congress.getAttrDef("religion"), "catholicism");
		Attribute islamism = new Attribute(congress.getAttrDef("religion"), "islamism");
		Attribute judaism = new Attribute(congress.getAttrDef("religion"), "judaism");

		Attribute democrat = new Attribute(congress.getAttrDef("party"), "democrat");
		Attribute republican = new Attribute(congress.getAttrDef("party"), "republican");

		ondrej.addAttribute(male);
		ondrej.addAttribute(hockey);
		ondrej.addAttribute(republican);

		miquel.addAttribute(male);
		miquel.addAttribute(football);
		miquel.addAttribute(catholicism);
		miquel.addAttribute(democrat);

		alex.addAttribute(male);
		alex.addAttribute(football);
		alex.addAttribute(catholicism);
		alex.addAttribute(republican);

		aleix.addAttribute(male);
		aleix.addAttribute(football);
		aleix.addAttribute(islamism);
		aleix.addAttribute(democrat);

		homer.addAttribute(basketball);
		homer.addAttribute(islamism);

		kate.addAttribute(female);
		kate.addAttribute(judaism);
		kate.addAttribute(republican);

		congress.addNode(ondrej);
		congress.addNode(miquel);
		congress.addNode(alex);
		congress.addNode(aleix);
		congress.addNode(kate);
		congress.addNode(homer);

		return congress;
	}
	private List<Set<MP>>  prepareMainPartition() {
		List<Set<MP>> partition = controller.getMainPartition();
		Congress congress = controller.getCurrentCongress();

		Set<MP> comm1 = new HashSet<>();
		Set<MP> comm2 = new HashSet<>();
		Set<MP> comm3 = new HashSet<>();

		List<MP> mps = new ArrayList<MP>(congress.getMPs());
		comm1.add(mps.get(0));
		comm1.add(mps.get(1));
		comm1.add(mps.get(2));
		comm2.add(mps.get(2));
		comm2.add(mps.get(3));
		comm3.add(mps.get(4));
		comm3.add(mps.get(5));

		partition.add(comm1);
		partition.add(comm2);
		partition.add(comm3);

		return partition;
	}
}