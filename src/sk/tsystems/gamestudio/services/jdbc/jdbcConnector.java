package sk.tsystems.gamestudio.services.jdbc;
import java.sql.Connection;
import java.sql.DriverManager;

public abstract class jdbcConnector implements AutoCloseable{
	// configuration for database connection  
	private final String driverCls = "oracle.jdbc.OracleDriver";
	private final String dbURL_primary = "jdbc:oracle:thin:@localhost:1521:xe";
	private final String dbURL_secondary = "jdbc:oracle:thin:@oracledb.dalibor.sk:1521:xe";
	private final String user = "gamecenter";
	private final String passw = "gamecenter";
	private static Connection dbCon = null; // an connection to database  
	
	public jdbcConnector() {
		super();
		if(!verifyConn())
			establishConn();
	}
	
	
	private void establishConn()
	{
        if(dbCon!= null)
        	tryCloseDBConn();

        // TODO propertyfile
        try
        {
            Class.forName(driverCls);
            try
            {
            	dbCon = DriverManager.getConnection(dbURL_primary, user, passw);
            }
            catch(Exception e)
            {
            	dbCon = DriverManager.getConnection(dbURL_secondary, user, passw);
            }
        }
        catch(Exception e)
        {
        	dbCon = null; 
        	throw new RuntimeException("Can't connect to database.", e);
        }
	}
	
	private void tryCloseDBConn()
	{
    	try // try to close 
    	{
    		dbCon.close();
    	}
    	catch (Exception e)
    	{
    		// we don't popup any exception, its always ok
    	}
    	dbCon = null;
	}
	
	private boolean verifyConn()
	{
		try {
			return dbCon.isValid(5);
		} 
		catch (Exception e) {
		}
		return false;
	}
	
	public Connection conn()
	{
		if( !verifyConn() )
			establishConn();
 
		return dbCon;
	}

	@Override
	public void close() throws Exception {
		tryCloseDBConn();
	}
}	
	

