package es.upc.fib.prop.usParlament.data;

import org.apache.commons.dbcp.BasicDataSource;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 * Some DB tools.
 * 
 * @author Petr Adamek 
 */
public class DBUtils {

    private static final Logger logger = Logger.getLogger(
            DBUtils.class.getName());
    
    public static DataSource prepareDataSource() {
	    BasicDataSource ds = new BasicDataSource();
        //we will use in memory database
        ds.setUrl("jdbc:derby://localhost:1527/USParlamentDB;create=true");
    return ds;
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
