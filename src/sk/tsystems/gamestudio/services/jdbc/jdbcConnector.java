package sk.tsystems.gamestudio.services.jdbc;
import java.sql.Connection;
import java.sql.DriverManager;

abstract class jdbcConnector implements AutoCloseable{
	// configuration for database connection  
	private final String driverCls = "oracle.jdbc.OracleDriver";
//	private final String dbURL = "jdbc:oracle:thin:@localhost:1521:xe";
	private final String dbURL = "jdbc:oracle:thin:@oracledb.dalibor.sk:1521:xe";
	private final String user = "gamecenter";
	private final String passw = "gamecenter";
	private static Connection dbCon = null; // an connection to database  
	
	jdbcConnector() {
		super();
		if(!verifyConn())
			establishConn();
	}
	
	
	private void establishConn()
	{
        if(dbCon!= null)
        	tryCloseDBConn();

        try
        {
            Class.forName(driverCls);
        	dbCon = DriverManager.getConnection(dbURL, user, passw);
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
	
	Connection conn()
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
	

