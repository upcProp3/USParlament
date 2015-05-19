package es.upc.fib.prop.usParlament.data;


import es.upc.fib.prop.usParlament.domain.MP;
import es.upc.fib.prop.usParlament.domain.State;
import org.apache.commons.dbcp.BasicDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.Date;
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
	private DataSource ds;

	private static DataSource prepareDataSource() throws SQLException
	{
		BasicDataSource ds = new BasicDataSource();
		//we will use in memory database
		ds.setUrl("jdbc:derby://localhost:1527/USParlament;create=true");
		return ds;
	}

	@Before
	public void setUp() throws SQLException, ClassNotFoundException {
		//Class.forName("org.apache.derby.jdbc.ClientDriver");
		ds = prepareDataSource();
		DBUtils.executeSqlScript(ds, MPsManager.class.getResource("createTables.sql"));
		manager = new MPsManagerImpl(ds);
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



	@Test
	public void testFindMPByID() {
		List<MP> mps = prepareMPs();

		MP result = manager.findMPByID(mps.get(1).getId());
		assertEquals("Find MP by ID fail.", mps.get(1), result);
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

		Collections.sort(mps, idComparator);
		Collections.sort(result,idComparator);

		assertEquals("lists are not equals.", mps, result);
	}
	@Test
	public void testFindAllMPsWithEmptyDB() {
		List<MP> mps = new ArrayList<>();

		List<MP> result = manager.findAllMPs();

		assertEquals("lists are not equals. It should be empty.", mps, result);
	}



	@Test
	public void testDeleteMP() {
		System.out.println("deleteStudent");

		List<MP> students = prepareMPs();

		manager.deleteMP(students.get(1));

		assertNotNull("first MP shouldn't be deleted", manager.findMPByID(students.get(0).getId()));
		assertNull("second MP should be delted", manager.findMPByID(students.get(1).getId()));
		assertNotNull("third MP souldn't be deleted", manager.findMPByID(students.get(2).getId()));
	}




	private List<MP> prepareMPs() {

		List<MP> mps = new ArrayList<>();

		mps.add(new MP( "Ondrej Velisek", State.AR, 1));
		mps.add(new MP( "Alex Miro", State.CA, 2));
		mps.add(new MP( "Miquel", State.WA, 3));

		manager.createMP(mps.get(0));
		manager.createMP(mps.get(1));
		manager.createMP(mps.get(2));

		assertNotNull("Create student fail", mps.get(0).getId());
		assertNotNull("Create student fail", mps.get(1).getId());
		assertNotNull("Create student fail", mps.get(2).getId());

		return mps;
	}
	private static final Comparator<MP> idComparator = new Comparator<MP>() {
		@Override
		public int compare(MP m1, MP m2) {
			return Long.valueOf(m1.getId()).compareTo(Long.valueOf(m2.getId()));
		}
	};

}