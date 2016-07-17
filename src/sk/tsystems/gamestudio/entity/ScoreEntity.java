package sk.tsystems.gamestudio.entity;

import java.util.Date;
import sk.tsystems.gamestudio.entity.UserEntity;
import sk.tsystems.gamestudio.entity.GameEntity;

public class ScoreEntity  {
	private Date date;
	private int score;
	private GameEntity game;
	private UserEntity user;
	
	public ScoreEntity(GameEntity game, UserEntity user, int score, Date datum) {
		super();
		this.game = game;
		this.user = user;
		this.score = score;
		this.date = datum;
	}
	
	public ScoreEntity(GameEntity game, UserEntity user, int score) {
		this(game, user, score, new Date());
	}

	public Date getDate() {
		return date;
	}

	public int getScore() {
		return score;
	}

	public GameEntity getGame() {
		return game;
	}

	public UserEntity getUser() {
		return user;
	}
	



}
