package es.upc.fib.prop.usParlament.data;


import es.upc.fib.prop.usParlament.domain.AttrDefinition;
import es.upc.fib.prop.usParlament.domain.Attribute;
import es.upc.fib.prop.usParlament.domain.MP;
import es.upc.fib.prop.usParlament.domain.State;
import org.apache.commons.dbcp.BasicDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


/**
 * Created by ondrej on 15.5.15.
 */
public class MPsManagerTest {


	private MPsManager manager;
	private AttributesManager attrManager;
	private DataSource ds;

	private static DataSource prepareDataSource() throws SQLException
	{
		BasicDataSource ds = new BasicDataSource();
		ds.setUrl("jdbc:derby://localhost:1527/USParlament;create=true");
		return ds;
	}

	@Before
	public void setUp() throws SQLException, ClassNotFoundException {
		//Class.forName("org.apache.derby.jdbc.ClientDriver");
		ds = prepareDataSource();
		DBUtils.executeSqlScript(ds, MPsManager.class.getResource("createTables.sql"));
		manager = new MPsManagerImpl(ds);
		attrManager = new AttributesManagerImpl(ds);
	}

	@After
	public void tearDown() throws SQLException {
		DBUtils.executeSqlScript(ds, MPsManager.class.getResource("dropTables.sql"));
	}



	@Test
	public void testCreateMP() {
		MP mp = new MP( "Ondrej Velisek", State.AR, 1);
		manager.createMP(mp);

		Long mpId = mp.getId();
		assertNotNull("Create MP fail. ID is null.", mpId);
		MP dbMP = manager.findMPByID(mpId);
		assertEquals("Create MP fail. MP was not found in DB.", mp, dbMP);
	}
	@Test
	public void testCreateMPWithAttributes() {
		MP mp = new MP( "Ondrej Velisek", State.AR, 1);
		List<AttrDefinition> attrDefinitions = prepareAttrDefinitions();
		Attribute attr1 = new Attribute(attrDefinitions.get(0), "young");
		Attribute attr2 = new Attribute(attrDefinitions.get(1), "male");
		mp.addAttribute(attr1);
		mp.addAttribute(attr2);
		manager.createMP(mp);

		Long mpId = mp.getId();
		assertNotNull("Create MP fail. ID is null.", mpId);
		MP dbMP = manager.findMPByID(mpId);
		assertEquals("Create MP fail. MP was not found in DB.", mp, dbMP);

		assertEquals("Create MP fail. MP doesn't have attribute.", attr1, dbMP.getAttribute(attrDefinitions.get(0)));
		assertEquals("Create MP fail. MP doesn't have attribute.", attr2, dbMP.getAttribute(attrDefinitions.get(1)));
	}
	@Test(expected=IllegalArgumentException.class)
	public void testCreateMPWithID() {
		MP mp = new MP( "Ondrej Velisek", State.AR, 1);
		mp.setId(15L);
		manager.createMP(mp);
	}
	@Test(expected=IllegalArgumentException.class)
	public void testCreateMPWithoutName() {
		MP mp = new MP( null, State.AR, 1);
		manager.createMP(mp);
	}
	@Test(expected=IllegalArgumentException.class)
	public void testCreateMPWithoutState() {
		MP mp = new MP( null, null, 1);
		manager.createMP(mp);
	}
	@Test(expected=IllegalArgumentException.class)
	public void testCreateMPWithNegativeDistrict() {
		MP mp = new MP( null, State.AR, -1);
		manager.createMP(mp);
	}
	@Test(expected=IllegalArgumentException.class)
	public void testCreateMPWithNullAttribute() {
		MP mp = new MP( "Ondrej Velisek", State.AR, 1);
		List<AttrDefinition> attrDefinitions = prepareAttrDefinitions();
		Attribute attr1 = new Attribute(attrDefinitions.get(0), "young");
		mp.addAttribute(attr1);
		mp.addAttribute(null);
		manager.createMP(mp);
	}



	@Test
	public void testFindMPByID() {
		List<MP> mps = prepareMPs();

		MP result = manager.findMPByID(mps.get(1).getId());
		assertEquals("Find MP by ID fail.", mps.get(1), result);
	}
	@Test
	public void testFindMPByIDWithAttributes() {
		List<MP> mps = prepareMPs();

		MP mp = mps.get(1);

		MP result = manager.findMPByID(mp.getId());
		assertEquals("Find MP by ID fail.", mp, result);

		Collections.sort(mp.getAttributes(), attrComparatorByDefId);
		Collections.sort(result.getAttributes(), attrComparatorByDefId);

		assertEquals("Find MP by ID fail. Attributes are nto equals.", mp.getAttributes(), result.getAttributes());
	}
	@Test(expected=IllegalArgumentException.class)
	public void testFindMPByIDWithNegativeID() {
		manager.findMPByID(-1L);
	}
	@Test(expected=IllegalArgumentException.class)
	public void testFindMPByIDWithoutID() {
		manager.findMPByID(null);
	}



	@Test
	public void testFindAllMPs() {
		List<MP> mps = prepareMPs();

		List<MP> result = manager.findAllMPs();

		Collections.sort(mps, idMPComparator);
		Collections.sort(result, idMPComparator);

		assertEquals("lists are not equals.", mps, result);
	}
	@Test
	public void testFindAllMPsWithAttributes() {
		List<MP> mps = prepareMPs();

		List<MP> result = manager.findAllMPs();

		Collections.sort(mps, idMPComparator);
		Collections.sort(result, idMPComparator);

		assertEquals("lists are not equals.", mps, result);

		for (int i = 0; i < mps.size(); i++) {
			List<Attribute> mpAttrs = mps.get(i).getAttributes();
			List<Attribute> resMPAttrs = result.get(i).getAttributes();
			Collections.sort(mpAttrs, attrComparatorByDefId);
			Collections.sort(resMPAttrs, attrComparatorByDefId);
			assertEquals("attributes are not equals", mpAttrs, resMPAttrs);
		}
	}
	@Test
	public void testFindAllMPsWithEmptyDB() {
		List<MP> mps = new ArrayList<>();

		List<MP> result = manager.findAllMPs();

		assertEquals("lists are not equals. It should be empty.", mps, result);
	}



	@Test
	public void testDeleteMP() {
		List<MP> students = prepareMPs();

		manager.deleteMP(students.get(1));

		assertNotNull("first MP shouldn't be deleted", manager.findMPByID(students.get(0).getId()));
		assertNull("second MP should be delted", manager.findMPByID(students.get(1).getId()));
		assertNotNull("third MP souldn't be deleted", manager.findMPByID(students.get(2).getId()));
	}



	@Test
	public void testUpdateMP() {
		List<MP> mps = prepareMPs();
		MP mp = mps.get(1);

		mp.setFullname("Darth Vader");
		mp.setState(State.LA);
		mp.setDistrict(4);
		manager.updateMP(mp);

		MP result = manager.findMPByID(mp.getId());

		assertEquals("mps are not equals.", mp, result);
		assertEquals("fullnames are not equals.", mp.getFullname(), result.getFullname());
		assertEquals("states are not equals.", mp.getState(), result.getState());
		assertEquals("districts are not equals.", mp.getDistrict(), result.getDistrict());
	}
	@Test
	public void testUpdateMPsAttributes() {
		List<MP> mps = prepareMPs();
		MP mp = mps.get(0);

		// first way: by set new value directly to attribute
		AttrDefinition ageDef = attrManager.findAttrDefinitionByName("age");
		mp.getAttribute(ageDef).setValue("old");

		// second way: by set new whole new attribute which should rewrite old-one
		AttrDefinition sexDef = attrManager.findAttrDefinitionByName("sex");
		mp.addAttribute(new Attribute(sexDef, "female"));

		manager.updateMP(mp);

		MP result = manager.findMPByID(mp.getId());

		assertEquals("mps are not equals.", mp, result);

		Collections.sort(mp.getAttributes(), attrComparatorByDefId);
		Collections.sort(result.getAttributes(), attrComparatorByDefId);

		assertEquals("Find MP by ID fail. Attributes are not equals.", mp.getAttributes(), result.getAttributes());
	}
	@Test(expected=IllegalArgumentException.class)
	public void testUpdateMPWithoutName() {
		List<MP> mps = prepareMPs();
		MP mp = mps.get(0);

		mp.setFullname(null);
		manager.updateMP(mp);
	}
	@Test(expected=IllegalArgumentException.class)
	public void testUpdateMPWithoutState() {
		List<MP> mps = prepareMPs();
		MP mp = mps.get(0);

		mp.setState(null);
		manager.updateMP(mp);
	}
	@Test(expected=IllegalArgumentException.class)
	public void testUpdateMPWithNegativeDistrict() {
		List<MP> mps = prepareMPs();
		MP mp = mps.get(0);

		mp.setDistrict(-1);
		manager.updateMP(mp);
	}




	private List<MP> prepareMPs() {

		List<MP> mps = new ArrayList<>();

		mps.add(new MP( "Ondrej Velisek", State.AR, 1));
		mps.add(new MP( "Alex Miro", State.CA, 2));
		mps.add(new MP( "Miquel", State.WA, 3));

		List<AttrDefinition> attrDef = prepareAttrDefinitions();

		mps.get(0).addAttribute(new Attribute(attrDef.get(0), "young"));
		mps.get(0).addAttribute(new Attribute(attrDef.get(1), "male"));
		mps.get(0).addAttribute(new Attribute(attrDef.get(2), "democratic"));

		mps.get(1).addAttribute(new Attribute(attrDef.get(1), "male"));
		mps.get(1).addAttribute(new Attribute(attrDef.get(2), "republican"));

		manager.createMP(mps.get(0));
		manager.createMP(mps.get(1));
		manager.createMP(mps.get(2));

		assertNotNull("Create student fail", mps.get(0).getId());
		assertNotNull("Create student fail", mps.get(1).getId());
		assertNotNull("Create student fail", mps.get(2).getId());

		return mps;
	}
	private List<AttrDefinition> prepareAttrDefinitions() {

		List<AttrDefinition> attrDefinitions = new ArrayList<>();

		attrDefinitions.add(new AttrDefinition( "age", 1));
		attrDefinitions.add(new AttrDefinition( "sex", 2));
		attrDefinitions.add(new AttrDefinition("party", 3));

		attrManager.createAttrDefinition(attrDefinitions.get(0));
		attrManager.createAttrDefinition(attrDefinitions.get(1));
		attrManager.createAttrDefinition(attrDefinitions.get(2));

		assertNotNull("Create student fail", attrDefinitions.get(0).getId());
		assertNotNull("Create student fail", attrDefinitions.get(1).getId());
		assertNotNull("Create student fail", attrDefinitions.get(2).getId());

		return attrDefinitions;
	}
	private static final Comparator<MP> idMPComparator = new Comparator<MP>() {
		@Override
		public int compare(MP m1, MP m2) {
			return Long.valueOf(m1.getId()).compareTo(Long.valueOf(m2.getId()));
		}
	};
	private static final Comparator<Attribute> attrComparatorByDefId = new Comparator<Attribute>() {
		@Override
		public int compare(Attribute a1, Attribute a2) {
			return Long.valueOf(a1.getDefinition().getId()).compareTo(Long.valueOf(a1.getDefinition().getId()));
		}
	};


}