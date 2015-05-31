package es.upc.fib.prop.usParlament.data;

import es.upc.fib.prop.usParlament.misc.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * JUnit tests of CongressManager class.
 * Created by ondrej on 20.5.15.
 */
public class DataControllerTest {

	private final String PATH = "dataControllerTest";
	private final String[] CONGRESS_NAMES = {"congress0", "congress1", "congress2"};
	private final String[][] PARTITIONS_NAMES = {{"p00", "p01"}, {"p10", "p11", "p12"}, {"p20", "p21"}};
	DataController manager;

	@Before
	public void setUp() throws Exception {
		manager = new DataControllerImpl(PATH);
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
	public void testSaveCongress() throws Exception {

		List<String> mps = getMPs();
		List<String> eds = getEdges();

		String c1 = "{\"mps\":[\"" +mps.get(1)+ "\",\"" +mps.get(2)+ "\",\"" +mps.get(3)+ "\",\"" +
				mps.get(4)+ "\",\"" +mps.get(5)+ "\",\"" +mps.get(0)+ "\"]," +
				"\"edges\":[\"" +eds.get(1)+ "\",\"" +eds.get(2)+ "\",\"" +eds.get(3)+ "\",\"" +
				eds.get(4)+ "\",\"" +eds.get(5)+ "\",\"" +eds.get(6)+ "\"," +
				"\"" +eds.get(7)+ "\",\"" +eds.get(8)+ "\",\"" +eds.get(9)+ "\",\"" +eds.get(10)+ "\",\"" +
				eds.get(11)+ "\",\"" +eds.get(0)+ "\"]}";

		final int pos = 1;

		String error = manager.saveCongress(CONGRESS_NAMES[pos], c1);

		assertEquals("{}", error);

		String res = manager.loadCongress(CONGRESS_NAMES[pos]);

		assertEquals(c1, res);
	}

	@Test
	public void testLoadCongress() throws Exception {
		List<String> congresses = prepareCongresses();
		preparePartitions();

		final int pos = 1;

		String congress = manager.loadCongress(CONGRESS_NAMES[pos]);

		assertEquals(congresses.get(pos), congress);
	}

	@Test
	public void testLoadAllCongressesNames() throws Exception {
		prepareCongresses();
		preparePartitions();

		String names = manager.loadAllCongressesNames();

		boolean first = true;
		String expected = "{\"congressesNames\":[";
		for (String name : CONGRESS_NAMES) {
			if (!first) {
				expected += ",";
			}
			expected += "\"" +name+ "\"";
			first = false;
		}
		expected += "]}";

		assertEquals(sortJSONArray(expected, "congressesNames"), sortJSONArray(names, "congressesNames"));
	}


	@Test
	public void testSavePartition() throws Exception {

		List<String> mps = getMPs();

		String p01 = "{\"mps\":[\"" +mps.get(4)+ "\",\"" +mps.get(5)+ "\",\"" +mps.get(0)+ "\"]}";

		final int congPos = 0;
		final int partPos = 1;

		String error = manager.savePartition(CONGRESS_NAMES[congPos], PARTITIONS_NAMES[congPos][partPos], p01);

		assertEquals("{}", error);

		String res = manager.loadPartition(CONGRESS_NAMES[congPos], PARTITIONS_NAMES[congPos][partPos]);

		assertEquals(p01, res);
	}

	@Test
	public void testLoadPartition() throws Exception {
		prepareCongresses();
		List<List<String>> partitions = preparePartitions();

		final int congPos = 0;
		final int partPos = 1;

		String partition = manager.loadPartition(CONGRESS_NAMES[congPos], PARTITIONS_NAMES[congPos][partPos]);

		assertEquals(partitions.get(congPos).get(partPos), partition);
	}

	@Test
	public void testLoadAllPartitionsOfCongress() throws Exception {
		prepareCongresses();
		List<List<String>> partitions = preparePartitions();

		final int pos = 1;

		String res = manager.loadAllPartitionsOfCongress(CONGRESS_NAMES[pos]);

		boolean first = true;
		String expected = "{\"partitions\":[";
		for (String part : partitions.get(pos)) {
			if (!first) {
				expected += ",";
			}
			expected += part;
			first = false;
		}
		expected += "]}";

		assertEquals(sortJSONArray(expected, "partitions"), sortJSONArray(res, "partitions"));
	}

	@Test
	public void testLoadAllPartitionNamesOfCongress() throws Exception {
		prepareCongresses();
		List<List<String>> partitions = preparePartitions();

		final int pos = 1;

		String res = manager.loadAllPartitionNamesOfCongress(CONGRESS_NAMES[pos]);

		boolean first = true;
		String expected = "{\"partitionNames\":[";
		for (String part : PARTITIONS_NAMES[pos]) {
			if (!first) {
				expected += ",";
			}
			expected += "\"" +part+ "\"";
			first = false;
		}
		expected += "]}";
		System.out.println(res);
		assertEquals(sortJSONArray(expected, "partitionNames"), sortJSONArray(res, "partitionNames"));
	}


	private String sortJSONArray(String object, String nameOfArray) {
		JSONizer jsonizer = new JSONizer();
		JSONObject namesObj = jsonizer.StringToJSON(object);
		JSONArray array = (JSONArray) namesObj.getJSONByKey(new JSONString(nameOfArray));
		List<JSON> list = array.getArray();
		Collections.sort(list);
		return jsonizer.JSONtoString(namesObj);
	}


	private List<String> prepareCongresses() {

		List<String> mps = getMPs();
		List<String> eds = getEdges();

		String c1 = "{\"mps\":[\"" +mps.get(1)+ "\",\"" +mps.get(2)+ "\",\"" +mps.get(3)+ "\",\"" +
				mps.get(4)+ "\",\"" +mps.get(5)+ "\",\"" +mps.get(0)+ "\"]," +
				"\"edges\":[\"" +eds.get(1)+ "\",\"" +eds.get(2)+ "\",\"" +eds.get(3)+ "\",\"" +
						eds.get(4)+ "\",\"" +eds.get(5)+ "\",\"" +eds.get(6)+ "\"," +
				"\"" +eds.get(7)+ "\",\"" +eds.get(8)+ "\",\"" +eds.get(9)+ "\",\"" +eds.get(10)+ "\",\"" +
								eds.get(11)+ "\",\"" +eds.get(0)+ "\"]}";
		String c2 = "{\"mps\":[\"" +mps.get(1)+ "\",\"" +mps.get(2)+ "\",\"" +mps.get(3)+ "\",\"" +mps.get(0)+ "\"]," +
				"\"edges\":[\"" +eds.get(1)+ "\",\"" +eds.get(2)+ "\",\"" +eds.get(3)+ "\",\"" +
				eds.get(0)+ "\",\"" +eds.get(7)+ "\",\"" +eds.get(8)+ "\",\"" +eds.get(6)+ "\"]}";
		String c3 = "{\"mps\":[\"" +mps.get(3)+ "\",\"" +mps.get(4)+ "\",\"" +mps.get(5)+ "\",\"" +mps.get(0)+ "\"]," +
				"\"edges\":[\"" +eds.get(5)+ "\",\"" +eds.get(6)+ "\",\"" +eds.get(9)+ "\",\"" +
				eds.get(10)+ "\",\"" +eds.get(11)+ "\",\"" +eds.get(0)+ "\"]}";

		String er;
		er = manager.saveCongress(CONGRESS_NAMES[0], c1);
		assertEquals("{}", er);
		er = manager.saveCongress(CONGRESS_NAMES[1], c2);
		assertEquals("{}", er);
		er = manager.saveCongress(CONGRESS_NAMES[2], c3);
		assertEquals("{}", er);


		List<String> congresses = new ArrayList<>();
		congresses.add(c1);
		congresses.add(c2);
		congresses.add(c3);

		return congresses;
	}

	private List<List<String>> preparePartitions() {

		List<String> mps = getMPs();

		String p00 = "{\"mps\":[" +mps.get(1)+ "," +mps.get(2)+ "," +mps.get(3)+ "]}";
		String p01 = "{\"mps\":[" +mps.get(4)+ "," +mps.get(5)+ "," +mps.get(0)+ "]}";

		String p10 = "{\"mps\":[" +mps.get(1)+ "]}";
		String p11 = "{\"mps\":[" +mps.get(2)+ "," +mps.get(3)+ "]}";
		String p12 = "{\"mps\":[" +mps.get(4)+ "]}";

		String p20 = "{\"mps\":[" +mps.get(3)+ "]}";
		String p21 = "{\"mps\":[" +mps.get(4)+ "," +mps.get(5)+ "," +mps.get(0)+ "]}";

		manager.savePartition(CONGRESS_NAMES[0], PARTITIONS_NAMES[0][0], p00);
		manager.savePartition(CONGRESS_NAMES[0], PARTITIONS_NAMES[0][1], p01);
		manager.savePartition(CONGRESS_NAMES[1], PARTITIONS_NAMES[1][0], p10);
		manager.savePartition(CONGRESS_NAMES[1], PARTITIONS_NAMES[1][1], p11);
		manager.savePartition(CONGRESS_NAMES[1], PARTITIONS_NAMES[1][2], p12);
		manager.savePartition(CONGRESS_NAMES[2], PARTITIONS_NAMES[2][0], p20);
		manager.savePartition(CONGRESS_NAMES[2], PARTITIONS_NAMES[2][1], p21);

		List<String> p0 = new ArrayList<>();
		p0.add(p00);
		p0.add(p01);
		List<String> p1 = new ArrayList<>();
		p1.add(p10);
		p1.add(p11);
		p1.add(p12);
		List<String> p2 = new ArrayList<>();
		p2.add(p20);
		p2.add(p21);

		List<List<String>> partitions = new ArrayList<>();
		partitions.add(p0);
		partitions.add(p1);
		partitions.add(p2);

		return partitions;
	}

	private List<String> getMPs() {
		String m1 = "{\"name\":\"Ondrej\",\"state\":\"AR\",\"district\":\"1\"}";
		String m2 = "{\"name\":\"Alex\",\"state\":\"CA\",\"district\":\"2\"}";
		String m3 = "{\"name\":\"Aleix\",\"state\":\"OH\",\"district\":\"3\"}";
		String m4 = "{\"name\":\"Miquel\",\"state\":\"AR\",\"district\":\"2\"}";
		String m5 = "{\"name\":\"Sebastian\",\"state\":\"CA\",\"district\":\"1\"}";
		String m6 = "{\"name\":\"Daniel\",\"state\":\"OH\",\"district\":\"1\"}";
		List<String> result = new ArrayList<>();
		result.add(m1);
		result.add(m2);
		result.add(m3);
		result.add(m4);
		result.add(m5);
		result.add(m6);
		return result;
	}
	private List<String> getEdges() {
		List<String> mps = getMPs();
		String e1 = "{\"m1\":\"" +mps.get(1)+ "\",\"m2\":\"" +mps.get(1)+ "\",\"weight\":\"12\"}";
		String e2 = "{\"m1\":\"" +mps.get(2)+ "\",\"m2\":\"" +mps.get(1)+ "\",\"weight\":\"11\"}";
		String e3 = "{\"m1\":\"" +mps.get(3)+ "\",\"m2\":\"" +mps.get(2)+ "\",\"weight\":\"10\"}";
		String e4 = "{\"m1\":\"" +mps.get(4)+ "\",\"m2\":\"" +mps.get(2)+ "\",\"weight\":\"9\"}";
		String e5 = "{\"m1\":\"" +mps.get(5)+ "\",\"m2\":\"" +mps.get(3)+ "\",\"weight\":\"8\"}";
		String e6 = "{\"m1\":\"" +mps.get(0)+ "\",\"m2\":\"" +mps.get(3)+ "\",\"weight\":\"7\"}";
		String e7 = "{\"m1\":\"" +mps.get(1)+ "\",\"m2\":\"" +mps.get(4)+ "\",\"weight\":\"6\"}";
		String e8 = "{\"m1\":\"" +mps.get(2)+ "\",\"m2\":\"" +mps.get(4)+ "\",\"weight\":\"5\"}";
		String e9 = "{\"m1\":\"" +mps.get(3)+ "\",\"m2\":\"" +mps.get(5)+ "\",\"weight\":\"4\"}";
		String e10 = "{\"m1\":\"" +mps.get(4)+ "\",\"m2\":\"" +mps.get(5)+ "\",\"weight\":\"3\"}";
		String e11 = "{\"m1\":\"" +mps.get(5)+ "\",\"m2\":\"" +mps.get(0)+ "\",\"weight\":\"2\"}";
		String e12 = "{\"m1\":\"" +mps.get(0)+ "\",\"m2\":\"" +mps.get(0)+ "\",\"weight\":\"1\"}";
		List<String> result = new ArrayList<>();
		result.add(e1);
		result.add(e2);
		result.add(e3);
		result.add(e4);
		result.add(e5);
		result.add(e6);
		result.add(e7);
		result.add(e8);
		result.add(e9);
		result.add(e10);
		result.add(e11);
		result.add(e12);
		return result;
	}
}