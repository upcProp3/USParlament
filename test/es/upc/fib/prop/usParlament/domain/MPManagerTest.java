package es.upc.fib.prop.usParlament.domain;


import es.upc.fib.prop.usParlament.data.MPManager;
import es.upc.fib.prop.usParlament.data.MPManagerImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;

import static es.upc.fib.prop.usParlament.data.DBUtils.executeSqlScript;
import static es.upc.fib.prop.usParlament.data.DBUtils.prepareDataSource;
import static org.junit.Assert.*;

public class MPManagerTest {

	private MPManager manager;
	private DataSource ds;

	@Before
	public void setUp() throws Exception {
		ds = prepareDataSource();
		executeSqlScript(ds, MPManager.class.getResource("createTables.sql"));
		manager = new MPManagerImpl(ds);
	}

	@After
	public void tearDown() throws Exception {
		executeSqlScript(ds, MPManager.class.getResource("dropTables.sql"));
	}

	@Test
	public void testCreateMP() throws Exception {

		MP mp = new MP("Ondrej Velisek", State.NY, 1);

		Long mpId = mp.getId();
		assertNull("Create student fail. Student has id before DB save", mpId);

		manager.createMP(mp);

		mpId = mp.getId();
		assertNotNull("Create student fail. Student doesn't have id after DB save", mpId);

		MP result = manager.getMPbyId(mpId);
		assertEquals(mp, result);
	}
/*
	@Test
	public void testGetAllMPs() throws Exception {

	}

	@Test
	public void testDeleteMP() throws Exception {

	}
	*/
}