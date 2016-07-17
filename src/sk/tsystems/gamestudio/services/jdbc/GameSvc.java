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

		GameEntity ga = new GameEntity(1, "Minesweeper");
		ga.setRunnable(sk.tsystems.gamestudio.game.minesweeper.Minesweeper.class);
		
		games.add(ga);
		games.add(new GameEntity(2, "Kamene"));
		games.add(new GameEntity(3, "Uhadni cislo"));
	}

	@Override
	public GameEntity getGame(int id) { 
		if(id < 0 || id >= games.size())
			return new GameEntity(id, "UNK_GAME_"+id);

		return games.get(id);
	}

	@Override
	public List<GameEntity> listGames() {
		return games;
	}

	@Override
	public void close() throws Exception {
		
	}

}