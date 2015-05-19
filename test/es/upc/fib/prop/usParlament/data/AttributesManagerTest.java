package es.upc.fib.prop.usParlament.data;

import es.upc.fib.prop.usParlament.domain.AttrDefinition;
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

import static org.junit.Assert.*;

/**
 * Created by ondrej on 19.5.15.
 */
public class AttributesManagerTest {


	private AttributesManager manager;
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
		manager = new AttributesManagerImpl(ds);
	}

	@After
	public void tearDown() throws SQLException {
		DBUtils.executeSqlScript(ds, MPsManager.class.getResource("dropTables.sql"));
	}



	@Test
	public void testCreateAttrDefinition() {
		AttrDefinition attrDefinition = new AttrDefinition("age", 1);
		manager.createAttrDefinition(attrDefinition);

		Long attrId = attrDefinition.getId();
		assertNotNull("Create AttrDefinition fail. ID is null.", attrId);
		AttrDefinition result = manager.findAttrDefinitionByID(attrId);
		assertEquals("Create AttrDefinition fail. AttrDefinition was not found in DB.", attrDefinition, result);
	}
	@Test(expected=IllegalArgumentException.class)
	public void testCreateAttrDefinitionWithId() {
		AttrDefinition attrDefinition = new AttrDefinition("age", 1);
		attrDefinition.setId(15L);
		manager.createAttrDefinition(attrDefinition);
	}
	@Test(expected=IllegalArgumentException.class)
	public void testCreateAttrDefinitionWithoutName() {
		AttrDefinition attrDefinition = new AttrDefinition(null, 1);
		manager.createAttrDefinition(attrDefinition);
	}
	@Test(expected=IllegalArgumentException.class)
	public void testCreateAttrDefinitionWithNegativeImportance() {
		AttrDefinition attrDefinition = new AttrDefinition("age", -1);
		manager.createAttrDefinition(attrDefinition);
	}



	@Test
	public void testFindAttrDefinitionByID() {
		List<AttrDefinition> attrDefinitions = prepareAttrDefinitions();

		AttrDefinition result = manager.findAttrDefinitionByID(attrDefinitions.get(1).getId());
		assertEquals("Find AttrDefinition by ID fail.", attrDefinitions.get(1), result);
	}
	@Test(expected=IllegalArgumentException.class)
	public void testFindAttrDefinitionByIDWithNegativeId() {
		manager.findAttrDefinitionByID(-1L);
	}
	@Test(expected=IllegalArgumentException.class)
	public void testFindAttrDefinitionByIDWithoutId() {
		manager.findAttrDefinitionByID(null);
	}



	@Test
	public void testFindAllAttrDefinitions() {
		List<AttrDefinition> attrDefinitions = prepareAttrDefinitions();

		List<AttrDefinition> result = manager.findAllAttrDefinitions();

		Collections.sort(attrDefinitions, idComparator);
		Collections.sort(result, idComparator);

		assertEquals("lists are not equals.", attrDefinitions, result);
	}
	@Test
	public void testFindAllAttrDefinitionsWithEmptyDB() {
		List<AttrDefinition> attrDefinitions = new ArrayList<>();

		List<AttrDefinition> result = manager.findAllAttrDefinitions();

		assertEquals("lists are not equals. It should be empty.", attrDefinitions, result);
	}



	@Test
	public void testDeleteAttrDefinition() {
		List<AttrDefinition> attrDefinitions = prepareAttrDefinitions();

		manager.deleteAttrDefinition(attrDefinitions.get(1));

		assertNotNull("first AttrDefinition shouldn't be deleted",
				manager.findAttrDefinitionByID(attrDefinitions.get(0).getId()));
		assertNull("second AttrDefinition should be delted",
				manager.findAttrDefinitionByID(attrDefinitions.get(1).getId()));
		assertNotNull("third AttrDefinition souldn't be deleted",
				manager.findAttrDefinitionByID(attrDefinitions.get(2).getId()));
	}



	@Test
	public void testUpdateAttrDefinition() {
		List<AttrDefinition> attrDefinitions = prepareAttrDefinitions();
		AttrDefinition attrDefinition = attrDefinitions.get(1);

		attrDefinition.setName("gender");
		attrDefinition.setImportance(4);
		manager.updateAttrDefinition(attrDefinition);

		AttrDefinition result = manager.findAttrDefinitionByID(attrDefinition.getId());

		assertEquals("attrDefinitions are not equals.", attrDefinition, result);
		assertEquals("names are not equals.", attrDefinition.getName(), result.getName());
		assertEquals("importances are not equals.", attrDefinition.getImportance(), result.getImportance());
	}
	@Test(expected=IllegalArgumentException.class)
	public void testUpdateAttrDefinitionWithoutName() {
		List<AttrDefinition> attrDefinitions = prepareAttrDefinitions();
		AttrDefinition attrDefinition = attrDefinitions.get(0);

		attrDefinition.setName(null);
		manager.updateAttrDefinition(attrDefinition);
	}
	@Test(expected=IllegalArgumentException.class)
	public void testUpdateAttrDefinitionWithNegativeImportance() {
		List<AttrDefinition> attrDefinitions = prepareAttrDefinitions();
		AttrDefinition attrDefinition = attrDefinitions.get(0);

		attrDefinition.setImportance(-1);
		manager.updateAttrDefinition(attrDefinition);
	}



	private List<AttrDefinition> prepareAttrDefinitions() {

		List<AttrDefinition> attrDefinitions = new ArrayList<>();

		attrDefinitions.add(new AttrDefinition( "age", 1));
		attrDefinitions.add(new AttrDefinition( "sex", 2));
		attrDefinitions.add(new AttrDefinition( "party", 3));

		manager.createAttrDefinition(attrDefinitions.get(0));
		manager.createAttrDefinition(attrDefinitions.get(1));
		manager.createAttrDefinition(attrDefinitions.get(2));

		assertNotNull("Create student fail", attrDefinitions.get(0).getId());
		assertNotNull("Create student fail", attrDefinitions.get(1).getId());
		assertNotNull("Create student fail", attrDefinitions.get(2).getId());

		return attrDefinitions;
	}
	private static final Comparator<AttrDefinition> idComparator = new Comparator<AttrDefinition>() {
		@Override
		public int compare(AttrDefinition a1, AttrDefinition a2) {
			return Long.valueOf(a1.getId()).compareTo(Long.valueOf(a2.getId()));
		}
	};
}