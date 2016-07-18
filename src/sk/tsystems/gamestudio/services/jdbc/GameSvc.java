package sk.tsystems.gamestudio.services.jdbc;
import java.util.ArrayList;
import java.util.List;



import sk.tsystems.gamestudio.entity.GameEntity;
//import sk.tsystems.gamestudio.services.jdbc.jdbcConnector;
import sk.tsystems.gamestudio.services.GameService;

public class GameSvc /*extends jdbcConnector*/ implements GameService {
	
	List<GameEntity> games;
	
	public GameSvc() {
		super();
		games = new ArrayList<>();
	}

	@Override
	public GameEntity getGame(int id) { 
		if(id < 0 || id > games.size())
			return null;

		return games.get(id-1);
	}

	@Override
	public List<GameEntity> listGames() {
		return games;
	}

	@Override
	public void close() throws Exception {
		
	}

	@Override
	public boolean addGame(GameEntity game) {
		games.add(game);
		return true; // TODO
	}

}