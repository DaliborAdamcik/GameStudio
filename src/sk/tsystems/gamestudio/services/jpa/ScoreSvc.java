package sk.tsystems.gamestudio.services.jpa;

import java.util.ArrayList;
import java.util.List;

import sk.tsystems.gamestudio.entity.GameEntity;
import sk.tsystems.gamestudio.entity.ScoreEntity;
import sk.tsystems.gamestudio.services.ScoreService;

public class ScoreSvc extends JpaConnector implements ScoreService {

	public ScoreSvc() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean addScore(ScoreEntity score) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<ScoreEntity> topScores(GameEntity game) {
		// TODO Auto-generated method stub
		return new ArrayList<ScoreEntity>();
	}

}
