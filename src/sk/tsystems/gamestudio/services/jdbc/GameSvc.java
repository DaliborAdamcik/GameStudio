package sk.tsystems.gamestudio.services.jdbc;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import sk.tsystems.gamestudio.entity.GameEntity;
import sk.tsystems.gamestudio.services.GameService;

public class GameSvc extends jdbcConnector implements GameService {
	private final String SELECT_G = "SELECT GAMEID, GNAME, SURL FROM GAME WHERE GAMEID = ?";
	private final String SELECT_L = "SELECT GAMEID FROM GAME";
	private final String INSERT_Q = "INSERT INTO GAME (GNAME, GAMEID, SURL) VALUES (?, GAMEID_SEQ.nextval, ?)";
	private static GameService instance = null; 
	
	List<GameEntity> games;
	
	public GameSvc() {
		super();
		games = new ArrayList<>();
		instance = this;
	}
	
	static GameService getInstance()
	{
		if(instance == null)
			throw new RuntimeException("Game service not started.");
		return instance;
	}

	@Override
	public GameEntity getGame(int id) { 
		try(PreparedStatement stmt = this.conn().prepareStatement(SELECT_G))
        {
			stmt.setInt(1, id);
			
        	try(ResultSet rs = stmt.executeQuery())
        	{
	        	if(rs.next())
	        	{
	        		GameEntity game  = new GameEntity(rs.getInt(1), rs.getString(2));

	        		if(rs.getString(3).compareTo("<unset>")!=0) // we have class for this file try to get class
	        		try
	        		{
	        			/*java.nio.file.Path currentRelativePath = java.nio.file.Paths.get("");
	        			String s = currentRelativePath.toAbsolutePath().toString();
	        			System.out.println("Current relative path is: " + s);*/	        			
	        			
	        			Class<?> clazz = Class.forName(rs.getString(3));
	        			game.setRunnable(clazz);
	        			
	        			
	        		}
	        		catch(Exception e)
	        		{
	        			e.printStackTrace();
	        		}
	        		return game;
	        	}
        	}
        } catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<GameEntity> listGames() {
		List<GameEntity> results = new ArrayList<>();
		
		try(PreparedStatement stmt = this.conn().prepareStatement(SELECT_L))
        {
        	try(ResultSet rs = stmt.executeQuery())
        	{
	        	while(rs.next())
	        		results.add(getGame(rs.getInt(1)));
        	}
        } catch (SQLException e) {
			e.printStackTrace();
		}
		
		return results;
	}

	@Override
	public boolean addGame(GameEntity game) {
        try(PreparedStatement stmt = this.conn().prepareStatement(INSERT_Q, new String[] {"GAMEID"}))
        {
	        stmt.setString(1, game.getName());
	        stmt.setString(2, game.className());
	        
	        if(stmt.executeUpdate()>0)
	        {
		        try(ResultSet rs = stmt.getGeneratedKeys())
		        {
			        if (rs.next() ) {
			            game.setID(rs.getInt(1));
			            return true;
			        }
		        }

	        }
	        
        } catch (SQLException e) {
        	e.printStackTrace();
		}
        return false;
	}

}