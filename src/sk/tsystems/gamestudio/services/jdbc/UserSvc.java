package sk.tsystems.gamestudio.services.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import sk.tsystems.gamestudio.entity.UserEntity;
import sk.tsystems.gamestudio.services.UserService;

public class UserSvc extends jdbcConnector implements UserService {
	private final String SELECT_QI = "SELECT USRID, UNAME FROM USRS WHERE USRID = ?";
	private final String SELECT_QN = "SELECT USRID, UNAME FROM USRS WHERE UNAME = ?";
	private final String INSERT_Q = "INSERT INTO USRS (UNAME) VALUES (?)";
	
	private UserEntity myAcc = null;
	
	public UserSvc() {
		super();
	}
	
	private UserEntity getUserSql(String Stat, Object o)
	{
		try(PreparedStatement stmt = this.conn().prepareStatement(Stat))
        {
			if (o instanceof String)
				stmt.setString(1, (String) o);
			else
			if (o instanceof Integer)
				stmt.setInt(1, (Integer) o);
			else
				throw new RuntimeException("Invalid parameter class: "+o.getClass().getName());
			
        	try(ResultSet rs = stmt.executeQuery())
        	{
	        	if(rs.next())
	        		return new UserEntity(rs.getInt(1), rs.getString(2)); 
        	}
        } catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public UserEntity getUser(int id) {
		return getUserSql(SELECT_QI, id);
	}

	@Override
	public UserEntity getUser(String name) {
		return getUserSql(SELECT_QN, name);
	}

	@Override
	public UserEntity me() {
		return myAcc;
	}

	@Override
	public boolean auth(String name) { //TODO as simple as can be
		UserEntity usr = getUser(name);
		
		if(usr==null) 
			usr = addUser(name);
		
		myAcc = usr;
		return true;
	}

	@Override
	public UserEntity addUser(String name) {
        try(PreparedStatement stmt = this.conn().prepareStatement(INSERT_Q))
        {
	        stmt.setString(1, name);

	        if(stmt.executeUpdate()>0)
	        	return getUser(name);
        } catch (SQLException e) {
        	if(e instanceof java.sql.SQLIntegrityConstraintViolationException)
	        	return getUser(name);
	        else
			e.printStackTrace();
		}
        
        return null;
	}


}
