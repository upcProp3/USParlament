package es.upc.fib.prop.usParlament.data;


import org.apache.commons.dbcp.BasicDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.SQLException;


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
	public void testCreateMP() throws Exception {

	}

	@Test
	public void testFindMPByID() throws Exception {

	}

	@Test
	public void testFindAllMPs() throws Exception {

	}

	@Test
	public void testDeleteMP() throws Exception {

	}
}