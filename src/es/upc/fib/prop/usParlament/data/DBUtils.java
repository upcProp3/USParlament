package es.upc.fib.prop.usParlament.data;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;
import org.apache.commons.dbcp.BasicDataSource;

/**
 * Some DB tools.
 * 
 * @author Petr Adamek 
 */
public class DBUtils {

    private static final Logger logger = Logger.getLogger(
            DBUtils.class.getName());
    
    public static DataSource getDataSource() {
    BasicDataSource ds = new BasicDataSource();
    //we will use in memory database
    ds.setUrl("jdbc:derby://localhost:1527/USParlament");
    
    return ds;
    }
    
    public static void checkDataSource(DataSource ds)
    {
        if (ds == null)
        {
            throw new IllegalStateException("DataSource is not set");
        }
    }
    
    public static Long getKey(ResultSet keyRS, Object o) throws ServiceFailureException, SQLException
    {
        if (keyRS.next())
        {
            if (keyRS.getMetaData().getColumnCount() != 1)
            {
                throw new ServiceFailureException("Internal Error: Generated key"
                        + "retriving failed when trying to insert " + o.toString()
                        + " - wrong key fields count: " + keyRS.getMetaData().getColumnCount());
            }
            Long result = keyRS.getLong(1);
            if (keyRS.next())
            {
                throw new ServiceFailureException("Internal Error: Generated key"
                        + "retriving failed when trying to insert " + o.toString()
                        + " - more keys found");
            }
            return result;
        }
        else
        {
            throw new ServiceFailureException("Internal Error: Generated key"
                    + "retriving failed when trying to insert " + o.toString()
                    + " - no key found");
        }
    }
    
    private static String[] readSqlStatements(URL url)
    {
        try
        {
            char buffer[] = new char[256];
            StringBuilder result = new StringBuilder();
            InputStreamReader reader = new InputStreamReader(url.openStream(), "UTF-8");
            while (true)
            {
                int count = reader.read(buffer);
                if (count < 0) {
                    break;
                }
                result.append(buffer, 0, count);
            }
            return result.toString().split(";");
        }
        catch (IOException ex)
        {
            throw new RuntimeException("Cannot read " + url, ex);
        }
    }
    
    public static void executeSqlScript(DataSource ds, URL scriptUrl) throws SQLException
    {
        try (Connection conn = ds.getConnection();)
        {
            for (String sqlStatement : readSqlStatements(scriptUrl))
            {
                if (!sqlStatement.trim().isEmpty())
                {
                    conn.prepareStatement(sqlStatement).executeUpdate();
                }
            }
        }
    } 
    
}
