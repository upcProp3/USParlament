package es.upc.fib.prop.usParlament.domain;

import es.upc.fib.prop.usParlament.data.DataControllerImpl;
import es.upc.fib.prop.usParlament.misc.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by ondrej on 1.6.15.
 */
public class DomainControllerTest {

	private final String PATH = "domainControllerTest";
	private final String[] CONGRESS_NAMES = {"congress0", "congress1", "congress2"};
	private final String[] PARTITION_NAMES = {"partition0", "partition1", "partition2"};
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
		Congress congress = prepareCurrentCongress();
		Map<String, Set<MP>>mainPartition = prepareMainPartition();
		Map<String, Set<MP>> partition1 = preparePartition1();
		Map<String, Set<MP>> partition2 = preparePartition2();
		controller.saveCurrentCongress(CONGRESS_NAMES[0]);
		String congressName = CONGRESS_NAMES[0];

		controller.cleanCommunityManager();

		assertEquals(congress, controller.getCurrentCongress());
		assertEquals(new TreeMap<String, Set<MP>>(), controller.getMainPartition());
		assertEquals(partition1, controller.getPartition1());
		assertEquals(partition2, controller.getPartition2());
		assertEquals(CONGRESS_NAMES[0], controller.getCurrentCongressName());
	}

	@Test
	public void testCleanCompareManager() throws Exception {
		Congress congress = prepareCurrentCongress();
		Map<String, Set<MP>>mainPartition = prepareMainPartition();
		Map<String, Set<MP>> partition1 = preparePartition1();
		Map<String, Set<MP>> partition2 = preparePartition2();
		controller.saveCurrentCongress(CONGRESS_NAMES[0]);
		String congressName = CONGRESS_NAMES[0];

		controller.cleanCompareManager();

		assertEquals(congress, controller.getCurrentCongress());
		assertEquals(mainPartition, controller.getMainPartition());
		assertEquals(new TreeMap<String, Set<MP>>(), controller.getPartition1());
		assertEquals(new TreeMap<String, Set<MP>>(), controller.getPartition2());
		assertEquals(CONGRESS_NAMES[0], controller.getCurrentCongressName());
	}

	@Test
	public void testNewCongress() throws Exception {
		Congress congress = prepareCurrentCongress();
		Map<String, Set<MP>>mainPartition = prepareMainPartition();
		Map<String, Set<MP>> partition1 = preparePartition1();
		Map<String, Set<MP>> partition2 = preparePartition2();
		controller.saveCurrentCongress(CONGRESS_NAMES[0]);
		String congressName = CONGRESS_NAMES[0];

		controller.newCongress();

		assertEquals(new Congress(), controller.getCurrentCongress());
		assertEquals(new TreeMap<String, Set<MP>>(), controller.getMainPartition());
		assertEquals(new TreeMap<String, Set<MP>>(), controller.getPartition1());
		assertEquals(new TreeMap<String, Set<MP>>(), controller.getPartition2());
		assertEquals("", controller.getCurrentCongressName());
	}

	@Test
	public void testSaveCurrentCongress() throws Exception {
		Congress congress = prepareCurrentCongress();
		String exception = controller.saveCurrentCongress(CONGRESS_NAMES[0]);
		assertEquals("{}", exception);
		controller.loadCongressAsCurrent(CONGRESS_NAMES[0]);
		Congress current = controller.getCurrentCongress();
		assertEquals(congress, current);
	}
	@Test
	public void testSaveCurrentCongressWithoutName() throws Exception {
		Congress congress = prepareCurrentCongress();
		String exception = controller.saveCurrentCongress("");
		expectedException(IllegalArgumentException.class, exception);
	}
	@Test
	public void testSaveCurrentCongressWithNullName() throws Exception {
		Congress congress = prepareCurrentCongress();
		String exception = controller.saveCurrentCongress(null);
		expectedException(IllegalArgumentException.class, exception);
	}

	@Test
	public void testLoadCongressAsCurrent() throws Exception {
		Congress congress = prepareCurrentCongress();
		controller.saveCurrentCongress(CONGRESS_NAMES[1]);
		controller.loadCongressAsCurrent(CONGRESS_NAMES[1]);
		Congress current = controller.getCurrentCongress();
		assertEquals(congress, current);
	}
	@Test
	public void testLoadCongressAsCurrentNotExist() throws Exception {
		Congress congress = prepareCurrentCongress();
		controller.saveCurrentCongress(CONGRESS_NAMES[0]);
		String exception = controller.loadCongressAsCurrent(CONGRESS_NAMES[1]);
		expectedException(FileNotFoundException.class, exception);
	}
	@Test
	public void testLoadCongressAsCurrentWithNullName() throws Exception {
		Congress congress = prepareCurrentCongress();
		String exception = controller.loadCongressAsCurrent(null);
		expectedException(IllegalArgumentException.class, exception);
	}

	@Test
	public void testLoadAllCongressesNames() throws Exception {
		Congress congress = prepareCurrentCongress();
		controller.saveCurrentCongress(CONGRESS_NAMES[0]);
		controller.saveCurrentCongress(CONGRESS_NAMES[1]);
		controller.saveCurrentCongress(CONGRESS_NAMES[2]);
		String jsonStringNames = controller.loadAllCongressesNames();

		JSONizer json = new JSONizer();
		JSONArray jsonNames = (JSONArray)json.StringToJSON(jsonStringNames).getJSONByKey("congressesNames");
		List<JSON> names = new ArrayList<>(jsonNames.getArray());

		List<String> sringNames = new ArrayList<>();
		for (JSON name : names) {
			sringNames.add(((JSONString)name).getValue());
		}

		List<String> expected = new ArrayList<>();
		for (String name : CONGRESS_NAMES) {
			expected.add(name);
		}
		Collections.sort(expected);
		Collections.sort(sringNames);
		assertEquals(expected, sringNames);
	}

	@Test
	public void testGetCurrentCongressName() throws Exception {
		Congress congress = prepareCurrentCongress();
		controller.saveCurrentCongress(CONGRESS_NAMES[0]);
		String congressName = controller.getCurrentCongressName();
		assertEquals(CONGRESS_NAMES[0], congressName);
	}
	@Test
	public void testGetCurrentCongressNameUnsaved() throws Exception {
		Congress congress = prepareCurrentCongress();
		String congressName = controller.getCurrentCongressName();
		assertEquals("", congressName);
	}

	@Test
	public void testComputeRelationships() throws Exception {
		Congress congress = prepareCurrentCongress();

		controller.computeRelationships();

		Congress expected = prepareCongressWithWeights();

		assertEquals(expected, controller.getCurrentCongress());
	}

	@Test
	public void testGetMPsShort() throws Exception {
		Congress congress = prepareCurrentCongress();
		String current = controller.getMPsShort();

		String expected = "{\"MPList\":[{\"State\":\"CO\",\"District\":\"2\"}," +
				"{\"State\":\"NY\",\"District\":\"1\"},{\"State\":\"CO\",\"District\":\"1\"}," +
				"{\"State\":\"OH\",\"District\":\"2\"},{\"State\":\"CA\",\"District\":\"1\"}," +
				"{\"State\":\"WA\",\"District\":\"1\"}]}";

		JSONizer json = new JSONizer();

		assertEquals(json.StringToJSON(expected), json.StringToJSON(current));
	}

	@Test
	public void testGetMPs() throws Exception {
		Congress congress = prepareCurrentCongress();
		String current = controller.getMPs();

		String expected = "{\"MPList\":[{\"Name\":\"Aleix\",\"religion\":\"islamism\",\"sex\":\"male\"," +
				"\"State\":\"WA\",\"District\":\"1\",\"sport\":\"football\",\"party\":\"democrat\"}," +
				"{\"Name\":\"Alex\",\"religion\":\"catholicism\",\"sex\":\"male\"," +
				"\"State\":\"NY\",\"District\":\"1\",\"sport\":\"football\",\"party\":\"republican\"}," +
				"{\"Name\":\"Homer\",\"religion\":\"islamism\"," +
				"\"State\":\"CO\",\"District\":\"2\",\"sport\":\"basketball\"}," +
				"{\"Name\":\"Kate\",\"religion\":\"judaism\",\"sex\":\"female\"," +
				"\"State\":\"OH\",\"District\":\"2\",\"party\":\"republican\"}," +
				"{\"Name\":\"Miquel\",\"religion\":\"catholicism\",\"sex\":\"male\"," +
				"\"State\":\"CO\",\"District\":\"1\",\"sport\":\"football\",\"party\":\"democrat\"}," +
				"{\"Name\":\"Ondrej\",\"sex\":\"male\"," +
				"\"State\":\"CA\",\"District\":\"1\",\"sport\":\"hockey\",\"party\":\"republican\"}]}";

		JSONizer json = new JSONizer();

		assertEquals(json.StringToJSON(expected), json.StringToJSON(current));
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
		Congress congress = prepareCurrentCongress();
		assertNull(congress.getMP(State.WA, 2));
		String exception = controller.addMP("{\"Name\":\"Aleix\",\"State\":\"WA\",\"District\":\"2\"}");
		assertEquals("{}", exception);
		assertNotNull(congress.getMP(State.WA, 2));
	}
	@Test
	public void testAddMPWithoutName() throws Exception {
		Congress congress = prepareCurrentCongress();
		String exception = controller.addMP("{\"State\":\"WA\",\"District\":\"2\"}");
		expectedException(IllegalArgumentException.class, exception);
	}
	@Test
	public void testAddMPWithoutState() throws Exception {
		Congress congress = prepareCurrentCongress();
		String exception = controller.addMP("{\"Name\":\"Aleix\",\"District\":\"2\"}");
		expectedException(IllegalArgumentException.class, exception);
	}
	@Test
	public void testAddMPWithoutDistrict() throws Exception {
		Congress congress = prepareCurrentCongress();
		String exception = controller.addMP("{\"Name\":\"Aleix\",\"State\":\"WA\"}");
		expectedException(IllegalArgumentException.class, exception);
	}

	@Test
	public void testRemoveMP() throws Exception {
		Congress congress = prepareCurrentCongress();
		assertNotNull(congress.getMP(State.WA, 1));
		String exception = controller.removeMP("{\"State\":\"WA\",\"District\":\"1\"}");
		assertEquals("{}", exception);
		assertNull(congress.getMP(State.WA, 1));
	}
	@Test
	public void testRemoveMPWithoutState() throws Exception {
		Congress congress = prepareCurrentCongress();
		String exception = controller.removeMP("{\"District\":\"1\"}");
		expectedException(IllegalArgumentException.class, exception);
	}
	@Test
	public void testRemoveMPWithoutDistrict() throws Exception {
		Congress congress = prepareCurrentCongress();
		String exception = controller.removeMP("{\"State\":\"WA\"}");
		expectedException(IllegalArgumentException.class, exception);
	}
	@Test
	public void testRemoveMP1() throws Exception {
		Congress congress = prepareCurrentCongress();
		assertNotNull(congress.getMP(State.WA, 1));
		String exception = controller.removeMP(State.WA, 1);
		assertEquals("{}", exception);
		assertNull(congress.getMP(State.WA, 1));
	}
	@Test
	public void testRemoveMPWithNullState() throws Exception {
		Congress congress = prepareCurrentCongress();
		String exception = controller.removeMP(null, 1);
		expectedException(IllegalArgumentException.class, exception);
	}
	@Test
	public void testRemoveMPWithZeroDistrict() throws Exception {
		Congress congress = prepareCurrentCongress();
		String exception = controller.removeMP(State.WA, 0);
		expectedException(IllegalArgumentException.class, exception);
	}

	@Test
	public void testGetAttrDefs() throws Exception {
		throw new UnsupportedOperationException();
	}

	@Test
	public void testAddOrModifyAttrDef() throws Exception {
		throw new UnsupportedOperationException();
	}

	@Test
	public void testDeleteAttrDef() throws Exception {
		throw new UnsupportedOperationException();
	}

	@Test
	public void testAddOrModifyAttribute() throws Exception {
		throw new UnsupportedOperationException();
	}

	@Test
	public void testAddOrModifyAttributes() throws Exception {
		throw new UnsupportedOperationException();
	}

	@Test
	public void testRemoveAttribute() throws Exception {
		throw new UnsupportedOperationException();
	}

	@Test
	public void testExistsAttrDef() throws Exception {
		throw new UnsupportedOperationException();
	}

	@Test
	public void testSaveMainPartition() throws Exception {
		Congress congress = prepareCurrentCongress();
		Map<String, Set<MP>>expected = prepareMainPartition();
		controller.saveCurrentCongress(CONGRESS_NAMES[0]);
		String exception = controller.saveMainPartition(PARTITION_NAMES[0]);
		assertEquals("{}", exception);
		controller.loadPartitionInto(PARTITION_NAMES[0], "mainPartition");
		Map<String, Set<MP>> current = controller.getMainPartition();
		assertEquals(expected, current);
	}
	@Test
	public void testSaveMainPartitionWithoutSavedCongress() throws Exception {
		Congress congress = prepareCurrentCongress();
		Map<String, Set<MP>>expected = prepareMainPartition();
		String exception = controller.saveMainPartition(PARTITION_NAMES[0]);
		expectedException(IllegalArgumentException.class, exception);
	}
	@Test
	public void testSaveMainPartitionWithNullName() throws Exception {
		Congress congress = prepareCurrentCongress();
		Map<String, Set<MP>>expected = prepareMainPartition();
		controller.saveCurrentCongress(CONGRESS_NAMES[0]);
		String exception = controller.saveMainPartition(null);
		expectedException(IllegalArgumentException.class, exception);
	}
	@Test
	public void testSaveMainPartitionWithoutName() throws Exception {
		Congress congress = prepareCurrentCongress();
		Map<String, Set<MP>>expected = prepareMainPartition();
		controller.saveCurrentCongress(CONGRESS_NAMES[0]);
		String exception = controller.saveMainPartition("");
		expectedException(IllegalArgumentException.class, exception);
	}

	@Test
	public void testLoadPartitionIntoMain() throws Exception {
		Congress congress = prepareCurrentCongress();
		Map<String, Set<MP>>expected = prepareMainPartition();
		controller.saveCurrentCongress(CONGRESS_NAMES[0]);
		controller.saveMainPartition(PARTITION_NAMES[0]);
		String exception = controller.loadPartitionInto(PARTITION_NAMES[0], "mainPartition");
		Map<String, Set<MP>> current = controller.getMainPartition();
		assertEquals(expected, current);
	}
	@Test
	public void testLoadPartitionInto1() throws Exception {
		Congress congress = prepareCurrentCongress();
		Map<String, Set<MP>>expected = prepareMainPartition();
		controller.saveCurrentCongress(CONGRESS_NAMES[0]);
		controller.saveMainPartition(PARTITION_NAMES[0]);
		String exception = controller.loadPartitionInto(PARTITION_NAMES[0], "partition1");
		Map<String, Set<MP>> current = controller.getPartition1();
		assertEquals(expected, current);
	}
	@Test
	public void testLoadPartitionInto2() throws Exception {
		Congress congress = prepareCurrentCongress();
		Map<String, Set<MP>>expected = prepareMainPartition();
		controller.saveCurrentCongress(CONGRESS_NAMES[0]);
		controller.saveMainPartition(PARTITION_NAMES[0]);
		String exception = controller.loadPartitionInto(PARTITION_NAMES[0], "partition2");
		Map<String, Set<MP>> current = controller.getPartition2();
		assertEquals(expected, current);
	}
	@Test
	public void testLoadPartitionIntoUnsavedPartition() throws Exception {
		Congress congress = prepareCurrentCongress();
		Map<String, Set<MP>>expected = prepareMainPartition();
		controller.saveCurrentCongress(CONGRESS_NAMES[0]);
		String exception = controller.loadPartitionInto(PARTITION_NAMES[0], "mainPartition");
		expectedException(IllegalArgumentException.class, exception);
	}
	@Test
	public void testLoadPartitionIntoUnsavedCongress() throws Exception {
		Congress congress = prepareCurrentCongress();
		Map<String, Set<MP>>expected = prepareMainPartition();
		String exception = controller.loadPartitionInto(PARTITION_NAMES[0], "mainPartition");
		expectedException(IllegalArgumentException.class, exception);
	}
	@Test
	public void testLoadPartitionIntoWithNullName() throws Exception {
		Congress congress = prepareCurrentCongress();
		Map<String, Set<MP>>expected = prepareMainPartition();
		controller.saveCurrentCongress(CONGRESS_NAMES[0]);
		controller.saveMainPartition(PARTITION_NAMES[0]);
		String exception = controller.loadPartitionInto(null, "mainPartition");
		expectedException(IllegalArgumentException.class, exception);
	}
	@Test
	public void testLoadPartitionIntoNullField() throws Exception {
		Congress congress = prepareCurrentCongress();
		Map<String, Set<MP>>expected = prepareMainPartition();
		controller.saveCurrentCongress(CONGRESS_NAMES[0]);
		controller.saveMainPartition(PARTITION_NAMES[0]);
		String exception = controller.loadPartitionInto(PARTITION_NAMES[0], null);
		expectedException(IllegalArgumentException.class, exception);
	}
	@Test
	public void testLoadPartitionIntoUnknownField() throws Exception {
		Congress congress = prepareCurrentCongress();
		Map<String, Set<MP>>expected = prepareMainPartition();
		controller.saveCurrentCongress(CONGRESS_NAMES[0]);
		controller.saveMainPartition(PARTITION_NAMES[0]);
		String exception = controller.loadPartitionInto(PARTITION_NAMES[0], "partition");
		expectedException(IllegalArgumentException.class, exception);
	}

	@Test
	public void testLoadAllPartitionsInCurrentCongress() throws Exception {
		Congress congress = prepareCurrentCongress();
		Map<String, Set<MP>> partition = prepareMainPartition();
		controller.saveCurrentCongress(CONGRESS_NAMES[0]);
		controller.saveMainPartition(PARTITION_NAMES[0]);
		controller.saveMainPartition(PARTITION_NAMES[1]);
		controller.saveMainPartition(PARTITION_NAMES[2]);
		String currentString = controller.loadAllPartitionsInCurrentCongress();

		JSONizer json = new JSONizer();
		JSONObject current = json.StringToJSON(currentString);

		JSONObject expected = new JSONObject();
		JSONArray partitions = new JSONArray();
		for (int i = 0; i < 3; i++ ) {
			JSONObject part = new JSONObject();
			JSONArray communities = new JSONArray();
			for (Map.Entry<String, Set<MP>> comm : partition.entrySet()) {
				JSONObject community = new JSONObject();
				JSONArray mps = new JSONArray();
				for (MP mp : comm.getValue()) {
					JSONObject jMP = new JSONObject();
					jMP.addPair("state", new JSONString(mp.getState().toString()));
					jMP.addPair("district", new JSONString(""+mp.getDistrict()));
					mps.addElement(jMP);
				}
				community.addPair("name", new JSONString(comm.getKey()));
				community.addPair("mps", mps);
				communities.addElement(community);
			}
			part.addPair("communities", communities);
			partitions.addElement(part);
		}
		expected.addPair("partitions", partitions);

		assertEquals(expected, current);
	}
	@Test
	public void testLoadAllPartitionsInCurrentCongressEmpty() throws Exception {
		Congress congress = prepareCurrentCongress();
		Map<String, Set<MP>>partition = prepareMainPartition();
		controller.saveCurrentCongress(CONGRESS_NAMES[0]);
		String currentString = controller.loadAllPartitionsInCurrentCongress();

		JSONizer json = new JSONizer();
		JSONObject current = json.StringToJSON(currentString);

		JSONObject expected = new JSONObject();
		JSONArray partitions = new JSONArray();
		expected.addPair("partitions", partitions);

		assertEquals(expected, current);
	}
	@Test
	public void testLoadAllPartitionsInCurrentCongressUnsavedCongress() throws Exception {
		Congress congress = prepareCurrentCongress();
		Map<String, Set<MP>>partition = prepareMainPartition();
		String exception = controller.loadAllPartitionsInCurrentCongress();
		expectedException(IllegalArgumentException.class, exception);
	}

	@Test
	public void testGetCommunityIDsMain() throws Exception {
		Congress congress = prepareCurrentCongress();
		Map<String, Set<MP>>partition = prepareMainPartition();
		String ids = controller.getCommunityIDs("mainPartition");
		JSONizer json = new JSONizer();
		JSONArray jIDs = (JSONArray)json.StringToJSON(ids).getJSONByKey("ids");
		Set<String> current = new HashSet<>();
		for (JSON j : jIDs.getArray()) {
			current.add(((JSONString)j).getValue());
		}
		assertEquals(partition.keySet(), current);
	}
	@Test
	public void testGetCommunityIDsUnknownField() throws Exception {
		Congress congress = prepareCurrentCongress();
		Map<String, Set<MP>>partition = prepareMainPartition();
		String exception = controller.getCommunityIDs("part1");
		expectedException(IllegalArgumentException.class, exception);
	}
	@Test
	public void testGetCommunityIDsNullField() throws Exception {
		Congress congress = prepareCurrentCongress();
		Map<String, Set<MP>>partition = prepareMainPartition();
		String exception = controller.getCommunityIDs(null);
		expectedException(IllegalArgumentException.class, exception);
	}

	@Test
	public void testGetMainPartitionSize() throws Exception {
		Congress congress = prepareCurrentCongress();
		Map<String, Set<MP>>partition = prepareMainPartition();
		String size = controller.getMainPartitionSize();
		assertEquals(String.valueOf(partition.size()), size);
	}

	@Test
	public void testGetMPsInMainPartition() throws Exception {
		Congress congress = prepareCurrentCongress();
		Map<String, Set<MP>> partition = prepareMainPartition();
		String mps = controller.getMPsInMainPartition("Community11");
		JSONizer json = new JSONizer();
		JSONObject current = json.StringToJSON(mps);
		JSONObject expected = new JSONObject();
		JSONArray expectedMPs = new JSONArray();
		for (MP mp : partition.get("Community11")) {
			JSONObject jMP = new JSONObject();
			jMP.addPair("State", new JSONString(mp.getState().toString()));
			jMP.addPair("District", new JSONString(""+mp.getDistrict()));
			expectedMPs.addElement(jMP);
		}
		expected.addPair("mps", expectedMPs);
		assertEquals(expected, current);
	}
	@Test
	public void testGetMPsInMainPartitionWithUnknownID() throws Exception {
		Congress congress = prepareCurrentCongress();
		Map<String, Set<MP>>partition = prepareMainPartition();
		String exception = controller.getMPsInMainPartition("A");
		expectedException(IllegalArgumentException.class, exception);
	}
	@Test
	public void testGetMPsInMainPartitionWithNullID() throws Exception {
		Congress congress = prepareCurrentCongress();
		Map<String, Set<MP>>partition = prepareMainPartition();
		String exception = controller.getMPsInMainPartition(null);
		expectedException(IllegalArgumentException.class, exception);
	}

	@Test
	public void testGetMPsInPartition1() throws Exception {
		Congress congress = prepareCurrentCongress();
		Map<String, Set<MP>> partition = preparePartition1();
		String mps = controller.getMPsInPartition1("Community11");
		JSONizer json = new JSONizer();
		JSONObject current = json.StringToJSON(mps);
		JSONObject expected = new JSONObject();
		JSONArray expectedMPs = new JSONArray();
		for (MP mp : partition.get("Community11")) {
			JSONObject jMP = new JSONObject();
			jMP.addPair("State", new JSONString(mp.getState().toString()));
			jMP.addPair("District", new JSONString(""+mp.getDistrict()));
			expectedMPs.addElement(jMP);
		}
		expected.addPair("mps", expectedMPs);
		assertEquals(expected, current);
	}
	@Test
	public void testGetMPsInPartition1WithUnknownID() throws Exception {
		Congress congress = prepareCurrentCongress();
		Map<String, Set<MP>>partition = preparePartition1();
		String exception = controller.getMPsInPartition1("A");
		expectedException(IllegalArgumentException.class, exception);
	}
	@Test
	public void testGetMPsInPartition1WithNullID() throws Exception {
		Congress congress = prepareCurrentCongress();
		Map<String, Set<MP>>partition = preparePartition1();
		String exception = controller.getMPsInPartition1(null);
		expectedException(IllegalArgumentException.class, exception);
	}

	@Test
	public void testGetMPsInPartition2() throws Exception {
		Congress congress = prepareCurrentCongress();
		Map<String, Set<MP>>partition = preparePartition2();
		String mps = controller.getMPsInPartition2("Community11");
		JSONizer json = new JSONizer();
		JSONObject current = json.StringToJSON(mps);
		JSONObject expected = new JSONObject();
		JSONArray expectedMPs = new JSONArray();
		for (MP mp : partition.get("Community11")) {
			JSONObject jMP = new JSONObject();
			jMP.addPair("State", new JSONString(mp.getState().toString()));
			jMP.addPair("District", new JSONString(""+mp.getDistrict()));
			expectedMPs.addElement(jMP);
		}
		expected.addPair("mps", expectedMPs);
		assertEquals(expected, current);
	}
	@Test
	public void testGetMPsInPartition2WithUnknownID() throws Exception {
		Congress congress = prepareCurrentCongress();
		Map<String, Set<MP>>partition = preparePartition2();
		String exception = controller.getMPsInPartition2("A");
		expectedException(IllegalArgumentException.class, exception);
	}
	@Test
	public void testGetMPsInPartition2WithNullID() throws Exception {
		Congress congress = prepareCurrentCongress();
		Map<String, Set<MP>>partition = preparePartition2();
		String exception = controller.getMPsInPartition2(null);
		expectedException(IllegalArgumentException.class, exception);
	}

	@Test
	public void testSetMainToPartition1() throws Exception {
		Congress congress = prepareCurrentCongress();
		Map<String, Set<MP>>part1 = preparePartition1();
		Map<String, Set<MP>>main = prepareMainPartition();
		controller.setMainToPartition1();
		assertEquals(main, controller.getPartition1());
		assertEquals(main, controller.getMainPartition());
	}

	@Test
	public void testSetMainToPartition2() throws Exception {
		Congress congress = prepareCurrentCongress();
		Map<String, Set<MP>>part2 = preparePartition2();
		Map<String, Set<MP>>main = prepareMainPartition();
		controller.setMainToPartition2();
		assertEquals(main, controller.getPartition2());
		assertEquals(main, controller.getMainPartition());
	}

	@Test
	public void testCompare2partitions() throws Exception {
		Congress congress = prepareCurrentCongress();
		//Map<String, Set<MP>> part = prepareMainPartition();
		Map<String, Set<MP>> part1 = preparePartition1();
		Map<String, Set<MP>> part2 = preparePartition2();
		String results = controller.compare2partitions();
		System.out.println(results);
	}

	@Test
	public void testLoadAllPartitionNamesInCurrentCongress() throws Exception {
		Congress congress = prepareCurrentCongress();
		Map<String, Set<MP>>partition = prepareMainPartition();
		controller.saveCurrentCongress(CONGRESS_NAMES[0]);
		controller.saveMainPartition(PARTITION_NAMES[0]);
		controller.saveMainPartition(PARTITION_NAMES[1]);
		controller.saveMainPartition(PARTITION_NAMES[2]);
		String currentString = controller.loadAllPartitionNamesInCurrentCongress();

		JSONizer json = new JSONizer();
		JSONObject current = json.StringToJSON(currentString);

		JSONObject expected = new JSONObject();
		JSONArray names = new JSONArray();
		for (String name : PARTITION_NAMES) {
			names.addElement(new JSONString(name));
		}
		expected.addPair("partitionNames", names);

		assertEquals(expected, current);
	}

	@Test
	public void testComputePartition() throws Exception {
		throw new UnsupportedOperationException();
	}

	@Test
	public void testAddNewCommunity() throws Exception {
		Congress congress = prepareCurrentCongress();
		Map<String, Set<MP>> partition = prepareMainPartition();
		int size = partition.size();
		controller.addNewCommunity();
		System.out.print(partition);
		assertEquals(String.valueOf(size + 1), controller.getMainPartitionSize());
		assertNotNull(controller.getMainPartition().get("Community0"));
	}

	@Test
	public void testRemoveCommunity() throws Exception {
		Congress congress = prepareCurrentCongress();
		Map<String, Set<MP>>partition = prepareMainPartition();
		int size = partition.size();
		Set<MP> toRemove = partition.get("Community11");
		controller.removeCommunity("Community11");
		assertEquals(size - 1, controller.getMainPartition().size());
		assertFalse(controller.getMainPartition().containsKey("Community11"));
	}

	@Test
	public void testAddMPToCommunity() throws Exception {
		Congress congress = prepareCurrentCongress();
		Map<String, Set<MP>> partition = prepareMainPartition();
		int size = partition.get("Community11").size();
		assertFalse(controller.getMainPartition().get("Community11").contains(congress.getMP(State.WA, 1)));
		controller.addMPToCommunity("Community11", State.WA, 1);
		assertEquals(size + 1, controller.getMainPartition().get("Community11").size());
		assertTrue(controller.getMainPartition().get("Community11").contains(congress.getMP(State.WA, 1)));
	}

	@Test
	public void testRemoveMPFromCommunity() throws Exception {
		Congress congress = prepareCurrentCongress();
		Map<String, Set<MP>> partition = prepareMainPartition();
		int size = partition.get("Community11").size();
		assertTrue(controller.getMainPartition().get("Community11").contains(congress.getMP(State.CO, 1)));
		controller.removeMPFromCommunity("Community11", State.CO, 1);
		assertEquals(size - 1, controller.getMainPartition().get("Community11").size());
		assertFalse(controller.getMainPartition().get("Community11").contains(congress.getMP(State.CO, 1)));
	}




	private Congress prepareCurrentCongress() {
		Congress congress = prepareCongress();
		controller.setCurrentCongress(congress);
		return congress;
	}
	private Congress prepareCongress() {
		Congress congress = new Congress();

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
	private Map<String, Set<MP>>  prepareMainPartition() {
		Map<String, Set<MP>> partition = controller.getMainPartition();
		Congress congress = controller.getCurrentCongress();

		Set<MP> comm1 = new HashSet<>();
		Set<MP> comm2 = new HashSet<>();
		Set<MP> comm3 = new HashSet<>();


		comm3.add(congress.getMP(State.CA, 1));
		comm3.add(congress.getMP(State.NY, 1));
		comm3.add(congress.getMP(State.WA, 1));
		comm2.add(congress.getMP(State.CO, 1));
		comm2.add(congress.getMP(State.CO, 2));
		comm1.add(congress.getMP(State.CO, 2));
		comm1.add(congress.getMP(State.OH, 2));

		partition.put("Community10", comm1);
		partition.put("Community11", comm2);
		partition.put("Community12", comm3);

		return partition;
	}
	private Map<String, Set<MP>>  preparePartition1() {
		Map<String, Set<MP>> partition = controller.getPartition1();
		Congress congress = controller.getCurrentCongress();

		Set<MP> comm1 = new HashSet<>();
		Set<MP> comm2 = new HashSet<>();
		Set<MP> comm3 = new HashSet<>();

		MP ondrej = new MP("Ondrej", State.CA, 1);
		MP alex = new MP("Alex", State.NY, 1);
		MP aleix = new MP("Aleix", State.WA, 1);
		MP miquel = new MP("Miquel", State.CO, 1);
		MP homer = new MP("Homer", State.CO, 2);
		MP kate = new MP("Kate", State.OH, 2);

		comm1.add(congress.getMP(State.CA, 1));
		comm1.add(congress.getMP(State.NY, 1));
		comm1.add(congress.getMP(State.WA, 1));
		comm2.add(congress.getMP(State.CO, 1));
		comm2.add(congress.getMP(State.CO, 2));
		comm3.add(congress.getMP(State.CO, 2));
		comm3.add(congress.getMP(State.OH, 2));

		partition.put("Community10", comm1);
		partition.put("Community11", comm2);
		partition.put("Community12", comm3);

		return partition;
	}
	private Map<String, Set<MP>>  preparePartition2() {
		Map<String, Set<MP>> partition = controller.getPartition2();
		Congress congress = controller.getCurrentCongress();

		Set<MP> comm1 = new HashSet<>();
		Set<MP> comm2 = new HashSet<>();

		comm1.add(congress.getMP(State.CA, 1));
		comm1.add(congress.getMP(State.NY, 1));
		comm1.add(congress.getMP(State.WA, 1));
		comm1.add(congress.getMP(State.CO, 1));
		comm2.add(congress.getMP(State.CO, 1));
		comm2.add(congress.getMP(State.OH, 2));

		partition.put("Community10", comm1);
		partition.put("Community11", comm2);

		return partition;
	}
	private Congress prepareCongressWithWeights() {
		Congress c = prepareCongress();

		c.addEdge(new Relationship(c.getMP(State.WA, 1), c.getMP(State.CO, 2), 4));
		c.addEdge(new Relationship(c.getMP(State.WA, 1), c.getMP(State.CO, 1), 18));
		c.addEdge(new Relationship(c.getMP(State.WA, 1), c.getMP(State.NY, 1), 2));
		c.addEdge(new Relationship(c.getMP(State.CO, 1), c.getMP(State.CA, 1), 1));
		c.addEdge(new Relationship(c.getMP(State.OH, 2), c.getMP(State.CA, 1), 16));
		c.addEdge(new Relationship(c.getMP(State.NY, 1), c.getMP(State.CA, 1), 17));
		c.addEdge(new Relationship(c.getMP(State.NY, 1), c.getMP(State.CO, 1), 6));
		c.addEdge(new Relationship(c.getMP(State.WA, 1), c.getMP(State.CA, 1), 1));
		c.addEdge(new Relationship(c.getMP(State.NY, 1), c.getMP(State.OH, 2), 16));

		controller.setCurrentCongress(c);
		return c;
	}

	private void expectedException(Class exception, String jsonStringException) {
		JSONizer json = new JSONizer();
		JSONObject jsonException = (JSONObject)json.StringToJSON(jsonStringException).getJSONByKey("Exception");
		assertNotNull(jsonException);
		String currentException = ((JSONString)jsonException.getJSONByKey("Name")).getValue();
		assertEquals(exception.getSimpleName(), currentException);
	}
}